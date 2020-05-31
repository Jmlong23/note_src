---
title: 对象数组和ArrayList集合
date: 2020-05-06 01:17:55
tags: ["java","ArrayList"]
categories: ["java"]
toc: true
---

## 1 引入--对象数组

使用学生数组，存储三个学生对象

```java
public class Student {   
    private String name;
    private int age;
    public Student() {
    }
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    publicvoid setName(String name) {
        this.name = name;
    }
    publicint getAge() {
        return age;
    }
    publicvoid setAge(int age) {
        this.age = age;
    }
} 
public class Test01StudentArray {
    //创建学生数组
    Student[] students = new Student[3];
    //创建学生对象
    Student s1 = new Student("曹操",40);
    Student s2 = new Student("刘备",35);
    Student s3 = new Student("孙权",30);
    //把学生对象作为元素赋值给学生数组
    students[0] = s1;
    students[1] = s2;
    students[2] = s3;
    //遍历学生数组
    for(int x=0; x<students.length; x++) {
        Student s = students[x]; 
        System.out.println(s.getName()+"‐‐‐"+s.getAge());
    }
}
```

到目前为止，我们想存储对象数据，选择的容器，只有对象数组。**而数组的长度是固定的，无法适应数据变化的需 求。**为了解决这个问题，Java提供了另一个容器 java.util.ArrayList 集合类,让我们可以更便捷的存储和操作对象数据。

<!--more-->

## 2 ArrayList类

### 2.1 基本概念和使用

* 查看类

  java.util.ArrayList \<E> ：该类需要 import导入使后使用。 
  \<E> ：表示一种指定的数据类型，叫做泛型。 E ，取自Element（元素）的首字母。在出现 E 的地方，我们使用一种引用数据类型将其替换即可，表示我们将存储哪种引用类型的元素。代码如下：

  ArrayList\<String>，ArrayList\<Student>

* 查看构造方法
  public ArrayList() ：构造一个内容为空的集合。

* 基本格式:

  ArrayList\<String> list = new ArrayList\<String>();

  在JDK 7后,右侧泛型的尖括号之内可以留空，但是<>仍然要写。简化格式：

  ArrayList\<String> list = new ArrayList<>();

* 查看成员方法
  public boolean add(E e) ： 将指定的元素添加到此集合的尾部。 参数 E e ，在构造ArrayList对象时， \<E> 指定了什么数据类型，那么 add(E e) 方法中，只能添加什么数据 类型的对象。

* 例子

  使用ArrayList类，存储三个字符串元素，代码如下：

```java
public class Test02StudentArrayList {
    public static void main(String[] args) {
        //创建学生数组
        ArrayList<String> list = new ArrayList<>();
        //创建学生对象
        String s1 = "曹操";
        String s3 = "孙权";
        //打印学生ArrayList集合
        System.out.println(list);
        //把学生对象作为元素添加到集合
        list.add(s1);
        list.add(s2);
        list.add(s3);
        //打印学生ArrayList集合
        System.out.println(list);   
	} 
}

```

### 2.2 常用方法和遍历

对于元素的操作,基本体现在——增、删、查。

常用的方法有： 

* public boolean add(E e) ：将指定的元素添加到此集合的尾部。 
* public E remove(int index) ：移除此集合中指定位置上的元素。返回被删除的元素。  
* public E get(int index) ：返回此集合中指定位置上的元素。返回获取的元素。
* public int size() ：返回此集合中的元素数。遍历集合时，可以控制索引范围，防止越界。 

这些都是基本的方法，操作非常简单，代码如下:

```java
public class Demo91ArrayListMethod{
  public static void main(String[] args) {
      //创建集合对象
      ArrayList<String> list = new ArrayList<String>();
      //添加元素
      list.add("hello");     
      list.add("world");     
      list.add("java");       
      //public E get(int index):返回指定索引处的元素     
      System.out.println("get:"+list.get(0));     
      System.out.println("get:"+list.get(1));     
      System.out.println("get:"+list.get(2));       
      //public int size():返回集合中的元素的个数     
      System.out.println("size:"+list.size());       
      //public E remove(int index):删除指定索引处的元素，返回被删除的元素     
      System.out.println("remove:"+list.remove(0));       
      //遍历输出     
      for(int i = 0; i < list.size(); i++){       
          System.out.println(list.get(i));     
      }   
  } 
}  
```

### 2.3 ArrayList练习

**数值添加到集合 **

生成6个1~33之间的随机整数,添加到集合,并遍历

```java
public class Test01ArrayList {
  public static void main(String[] args) { 
      // 创建Random 对象     
      Random random = new Random();       
      // 创建ArrayList 对
      ArrayList<Integer> list = new ArrayList<>();       
      // 添加随机数到集合     
      for (int i = 0; i < 6; i++) {       
          int r = random.nextInt(33) + 1;
          list.add(r);     }       
      // 遍历集合输出     
      for (int i = 0; i < list.size(); i++) {       
          System.out.println(list.get(i));     
      }   
  } 
}

```

