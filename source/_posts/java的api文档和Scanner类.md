---
title: java的api文档和Scanner类
toc: true
date: 2020-05-06 15:13:12
tags:
categories: ["java"]
---

## 1 API概述

1. api概述

API(Application Programming Interface)，应用程序编程接口。Java API是一本程序员的 字典 ，是JDK中提供给 我们使用的类的说明文档。这些类将底层的代码实现封装了起来，我们不需要关心这些类是如何实现的，只需要学 习这些类如何使用即可。所以我们可以通过查询API的方式，来学习Java提供的类，并得知如何使用它们。

2. api使用步骤
   1. 打开帮助文档。 
   2. 点击显示，找到索引，看到输入框。 
   3. 你要找谁？在输入框里输入，然后回车。 
   4. 看包。java.lang下的类不需要导包，其他需要。
   5. 看类的解释和说明。 
   6. 学习构造方法。
   7. 使用成员方法。 
   
   <!--more-->

## 2 引用类型的一般使用步骤

1. 导包
import 包路径.类名称;
如果需要使用的目标类，和当前类位于同一个包下，则可以省略导包语句不写。
只有java.lang包下的内容不需要导包，其他的包都需要import语句。

2. 创建
类名称 对象名 = new 类名称();

3. 使用
对象名.成员方法名()

## 3 Scanner类

```java
package cn.itcast.day07.demo01;

import java.util.Scanner; // 1. 导包

/*
Scanner类的功能：可以实现键盘输入数据，到程序当中。

获取键盘输入的一个int数字：int num = sc.nextInt();
获取键盘输入的一个字符串：String str = sc.next();遇到空格就会结束
sc.nextLine();遇到Enter键才会结束
 */
public class Demo01Scanner {

    public static void main(String[] args) {
        // 2. 创建
        // 备注：System.in代表从键盘进行输入
        Scanner sc = new Scanner(System.in);

        // 3. 获取键盘输入的int数字
        int num = sc.nextInt();
        System.out.println("输入的int数字是：" + num);

        // 4. 获取键盘输入的字符串
        String str = sc.next();
        System.out.println("输入的字符串是：" + str);
    }

}
```

