---
title: Java关键字
toc: true
date: 2020-05-06 18:47:05
tags:
categories: java
---

## 1 静态static关键字

### 1.1 静态static关键字概述

一旦用了static关键字修饰，那么这个元素就不再属于对象自己，而是属于类的，凡是本类的对象都共享同一份

1. 比如下面的所在教室属性，用了static修饰，那么就属于这个学生类

![](/images/20200506/1.png)

![](/images/20200506/2.png)

<!--more-->

## 2 private关键字

### 2.1 概述

问题描述：定义Person的年龄时，无法阻止不合理的数值被设置进来。
解决方案：用private关键字将需要保护的成员变量进行修饰。

一旦使用了private进行修饰，那么本类当中仍然可以随意访问。
但是！超出了本类范围之外就不能再直接访问了。

只可以间接访问private成员变量，就是定义一对儿Getter/Setter方法

```java

/*
必须叫setXxx或者是getXxx命名规则。
对于Getter来说，不能有参数，返回值类型和成员变量对应；
对于Setter来说，不能有返回值，参数类型和成员变量对应。
 */
public class Person {

    String name; // 姓名
    private int age; // 年龄

    public void show() {
        System.out.println("我叫：" + name + "，年龄：" + age);
    }

    // 这个成员方法，专门用于向age设置数据
    public void setAge(int num) {
        if (num < 100 && num >= 9) { // 如果是合理情况
            age = num;
        } else {
            System.out.println("数据不合理！");
        }
    }

    // 这个成员方法，专门私语获取age的数据
    public int getAge() {
        return age;
    }
}
```



