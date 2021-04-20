---
title: vs编译器相关问题
toc: true
date: 2020-09-24 15:02:16
tags:
categories: 其他
---

# c++fopen函数unsafe

<https://blog.csdn.net/sgfmby1994/article/details/80432205>

输入_CRT_SECURE_NO_WARNINGS

![](/images/20200924/1.png)

<!--more-->

# 打不开源文件

```
在已安装好的情况下,编译时报E1696等一堆错误时,可采用此方法解决该问题.
工具 -> 获取工具和功能 -> 在windows平台开发中的可选项中将windows 10SDK (10.0.177630.0)勾选上安装即可解决.
```

<https://blog.csdn.net/weixin_44094541/article/details/103854082?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.edu_weight&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.edu_weight>

# vs2019添加外部包

项目目录，把openssl的bin、lib、include目录拷贝进来

![](/images/20200922/1.png)

引入include目录../../include

![](/images/20200922/2.png)

引入lib目录../../lib

![](/images/20200922/3.png)

导入lib里面的libcrypto.lib文件名

![](/images/20200922/4.png)

这部要留意，如果显示“无法解析的外部符号 _BIO_new，函数 "void __cdecl Base64Decode(char const *,unsigned char * *,unsigned int *)" (?Base64Decode@@YAXPBDPAPAEPAI@Z) 中引用了该符号”的错误就是没有导入该文件。

运行结果图：

![](/images/20200922/0.png)