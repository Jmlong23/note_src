---
title: java读取指定文件夹下的文件名称和文件内容
toc: true
date: 2020-05-12 00:04:07
tags: 
categories: java
hidden: true
---



## 直接看代码

<!--more-->

```java
import java.io.*;


/**  
 * 
 * 该类可以输出指定路径下所有的文件名（文件名和文件夹名）
 * 指定一个路径即可
 *  
 */  
public class test {    

    public static void main(String[] args) throws IOException {
        //这是需要获取的文件夹路径  
        String path = "C:\\Users\\15626\\Documents\\庄育飞软件172项目管理论文选题";   
        getFile(path,0);   
    }   
    /*
     * 函数名：getFile
     * 作用：使用递归，输出指定文件夹内的所有文件
     * 参数：path：文件夹路径   deep：表示文件的层次深度，控制前置空格的个数
     * 前置空格缩进，显示文件层次结构
     */
    private static void getFile (String path,int deep) throws IOException{   
        // 获得指定文件对象  
        File file = new File(path);   
        // 获得该文件夹内的所有文件   
        File[] array = file.listFiles();
        String str;
        Writer writer = new FileWriter("软件172项目管理选题.txt");   
        for(int i=0;i<array.length;i++)
        {   
            if(array[i].isFile())//如果是文件
            {   
                    for (int j = 0; j < deep; j++)//输出前置空格
                    System.out.print(" ");
                // 只输出文件名字  
                System.out.println( array[i].getName().replace(".txt", ":"));   
                // 输出当前文件的完整路径   
               // System.out.println("#####" + array[i]);   
                // 同样输出当前文件的完整路径   大家可以去掉注释 测试一下   
               // System.out.println(array[i].getPath()); 
               Reader reader = new FileReader(path +'\\'+array[i].getName()); // 字符编码是???
               char[] buffer = new char[100];
                int n;
                writer.append(array[i].getName().replace(".txt", ":\n"));
                while ((n = reader.read(buffer)) != -1) {
                    System.out.println(buffer);
                    
                    for(int k = 0; k < n; k++){
                        writer.append(buffer[k]);
                    }
                }
                writer.write("\n");
               reader.close(); // 关闭流  
            }
            else if(array[i].isDirectory())//如果是文件夹
            {  
                    for (int j = 0; j < deep; j++)//输出前置空格
                    System.out.print(" ");

                    System.out.println( array[i].getName());
                    //System.out.println(array[i].getPath());
                    //文件夹需要调用递归 ，深度+1
                getFile(array[i].getPath(),deep+1);  
            }   
        } 
        writer.close();  
    }   
} 
```

### 遇到的bug

* 把文件txt文件都转化成utf-8形式，不然会有乱码
* write时记得关闭文件，不然数据停留在缓冲区，写不进磁盘，在txt文件里面看不见
* 这里用到的是字符流，区别之前的字节流
* 此代码参考廖雪峰老师的教程编写和jdk1.6中文文档编写