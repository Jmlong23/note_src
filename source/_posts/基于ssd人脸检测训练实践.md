---
title: 基于ssd人脸检测训练实践
toc: true
date: 2020-05-10 17:35:27
tags:
categories: 深度学习
---

## 实验环境

<!--more-->

win10

tensorflow-gpu1.14.0

anaconda3.6

cuda10.0

cudnn7.6

## 数据预处理

### 下载widerface数据集

<http://shuoyang1213.me/WIDERFACE/>

### 将数据打包成voc格式

在我是在数据集下的相同目录下创建5个文件夹：
JPEGImages：用来保存你的数据图片
Annotations：这里是存放你对所有数据图片做的标注，每张照片的标注信息必须是xml格式
ImageSets/Main：train.txt、val.txt
TF-record:是将voc格式的数据转化成tfrecord文件的
**参考博客：**<https://blog.csdn.net/bingbign0607/article/details/105727746>

运行：python widerface.py #文件名是widerface

注意文件夹的路径

```python
import os,cv2,sys,shutil,numpy

from xml.dom.minidom import Document
import os
def writexml(filename, saveimg, bboxes, xmlpath):
    doc = Document()

    annotation = doc.createElement('annotation')

    doc.appendChild(annotation)

    folder = doc.createElement('folder')

    folder_name = doc.createTextNode('widerface')
    folder.appendChild(folder_name)
    annotation.appendChild(folder)
    filenamenode = doc.createElement('filename')
    filename_name = doc.createTextNode(filename)
    filenamenode.appendChild(filename_name)
    annotation.appendChild(filenamenode)
    source = doc.createElement('source')
    annotation.appendChild(source)
    database = doc.createElement('database')
    database.appendChild(doc.createTextNode('wider face Database'))
    source.appendChild(database)
    annotation_s = doc.createElement('annotation')
    annotation_s.appendChild(doc.createTextNode('PASCAL VOC2007'))
    source.appendChild(annotation_s)
    image = doc.createElement('image')
    image.appendChild(doc.createTextNode('flickr'))
    source.appendChild(image)
    flickrid = doc.createElement('flickrid')
    flickrid.appendChild(doc.createTextNode('-1'))
    source.appendChild(flickrid)
    owner = doc.createElement('owner')
    annotation.appendChild(owner)
    flickrid_o = doc.createElement('flickrid')
    flickrid_o.appendChild(doc.createTextNode('muke'))
    owner.appendChild(flickrid_o)
    name_o = doc.createElement('name')
    name_o.appendChild(doc.createTextNode('muke'))
    owner.appendChild(name_o)

    size = doc.createElement('size')
    annotation.appendChild(size)

    width = doc.createElement('width')
    width.appendChild(doc.createTextNode(str(saveimg.shape[1])))
    height = doc.createElement('height')
    height.appendChild(doc.createTextNode(str(saveimg.shape[0])))
    depth = doc.createElement('depth')
    depth.appendChild(doc.createTextNode(str(saveimg.shape[2])))

    size.appendChild(width)

    size.appendChild(height)
    size.appendChild(depth)
    segmented = doc.createElement('segmented')
    segmented.appendChild(doc.createTextNode('0'))
    annotation.appendChild(segmented)
    for i in range(len(bboxes)):
        bbox = bboxes[i]
        objects = doc.createElement('object')
        annotation.appendChild(objects)
        object_name = doc.createElement('name')
        object_name.appendChild(doc.createTextNode('face'))
        objects.appendChild(object_name)
        pose = doc.createElement('pose')
        pose.appendChild(doc.createTextNode('Unspecified'))
        objects.appendChild(pose)
        truncated = doc.createElement('truncated')
        truncated.appendChild(doc.createTextNode('0'))
        objects.appendChild(truncated)
        difficult = doc.createElement('difficult')
        difficult.appendChild(doc.createTextNode('0'))
        objects.appendChild(difficult)
        bndbox = doc.createElement('bndbox')
        objects.appendChild(bndbox)
        xmin = doc.createElement('xmin')
        xmin.appendChild(doc.createTextNode(str(bbox[0])))
        bndbox.appendChild(xmin)
        ymin = doc.createElement('ymin')
        ymin.appendChild(doc.createTextNode(str(bbox[1])))
        bndbox.appendChild(ymin)
        xmax = doc.createElement('xmax')
        xmax.appendChild(doc.createTextNode(str(bbox[0] + bbox[2])))
        bndbox.appendChild(xmax)
        ymax = doc.createElement('ymax')
        ymax.appendChild(doc.createTextNode(str(bbox[1] + bbox[3])))
        bndbox.appendChild(ymax)
    f = open(xmlpath, "w")
    f.write(doc.toprettyxml(indent=''))
    f.close()
rootdir = "D:\\face\\dataset\\widerface"

gtfile = "D:\\face\\dataset\\widerface\\wider_face_split\\" \
         "wider_face_val_bbx_gt.txt";

im_folder = "D:\\face\\dataset\\widerface\\WIDER_val\\images";

##这里可以是test也可以是val
fwrite = open("D:\\face\\dataset\\widerface\\ImageSets\\Main\\test.txt", "w")
with open(gtfile, "r") as gt:
    while(True):
        gt_con = gt.readline()[:-1]
        if gt_con is None or gt_con == "":
            break
        im_path = im_folder + "/" + gt_con;
        print(im_path)
        im_data = cv2.imread(im_path)
        if im_data is None:
            continue

        ##需要注意的一点是，图片直接经过resize之后，会存在更多的长宽比例，所以我们直接加pad
        sc = max(im_data.shape)
        im_data_tmp = numpy.zeros([sc, sc, 3], dtype=numpy.uint8)
        off_w = (sc - im_data.shape[1]) // 2
        off_h = (sc - im_data.shape[0]) // 2

        ##对图片进行周围填充，填充为正方形
        im_data_tmp[off_h:im_data.shape[0]+off_h, off_w:im_data.shape[1]+off_w, ...] = im_data
        im_data = im_data_tmp
        #
        # cv2.imshow("1", im_data)
        # cv2.waitKey(0)
        numbox = int(gt.readline())
        #numbox = 0
        bboxes = []
        for i in range(numbox):
            line = gt.readline()
            infos = line.split(" ")
            #x y w h ---
            #去掉最后一个（\n）
            for j in range(infos.__len__() - 1):
                infos[j] = int(infos[j])

            ##注意这里加入了数据清洗
            ##保留resize到640×640 尺寸在8×8以上的人脸
            if infos[2] * 80 < im_data.shape[1] or infos[3] * 80 < im_data.shape[0]:
                continue

            bbox = (infos[0] + off_w, infos[1] + off_h, infos[2], infos[3])
            # cv2.rectangle(im_data, (int(infos[0]) + off_w, int(infos[1]) + off_h),
            #               (int(infos[0]) + off_w + int(infos[2]), int(infos[1]) + off_h + int(infos[3])),
            #               color=(0, 0, 255), thickness=1)
            bboxes.append(bbox)

        # cv2.imshow("1", im_data)
        # cv2.waitKey(0)

        filename = gt_con.replace("/", "_")
        fwrite.write(filename.split(".")[0] + "\n")

        cv2.imwrite("{}/JPEGImages/{}".format(rootdir, filename), im_data)

        xmlpath = "{}/Annotations/{}.xml".format(rootdir, filename.split(".")[0])
        writexml(filename, im_data, bboxes, xmlpath)

fwrite.close()
```

### 将voc数据转换成tfrecord格式

**参考博客:**<https://my.oschina.net/u/876354/blog/1927351>

**训练代码下载地址：**<https://github.com/balancap/SSD-Tensorflow>

运行：

```python
python create_face_tf_record.py --data_dir=D:\face\dataset --year=widerface --output_path=D:\face\dataset\widerface\TF_data\test.record --set=test
```



## 下载预训练模型

链接：https://pan.baidu.com/s/1U9b7IlW6C4YDoFcLroL-DA 
提取码：idbz



## 训练模型

```

```

**参考博客:**<https://my.oschina.net/u/876354/blog/1927351>

## 测试模型

安装jupyter

运行:

```
jupyter-notebook SSD-Tensorflow-master/ssd_notebook.ipynb
```

注意模型的路径命名

![](/images/20200514/1.png)

运行效果图

![](/images/20200514/2.png)