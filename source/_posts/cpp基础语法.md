---
title: c++基础语法
toc: true
date: 2020-09-26 12:58:55
tags:
categories: cpp
hidden: true
---

begin:

<!--more-->

# cpp开发工程师学习资料

前言，牛客面经：https://www.nowcoder.com/tutorial/93/8ba2828006dd42879f3a9029eabde9f1

1. cpp primer 第5版 

c++学习手册：https://zh.cppreference.com/w/cpp

学习笔记：https://github.com/czs108/Cpp-Primer-5th-Notes-CN

2. stl源码剖析

3. 面经：

https://leetcode-cn.com/u/crystal_yyf/ 

https://blog.csdn.net/qq_41589622/article/details/109813884

4. 剑指offer：

https://github.com/gatieme/CodingInterviews

https://leetcode-cn.com/leetbook/read/illustration-of-algorithm/50c26h/

5. 设计模式：单例模式和工厂模式

https://design-patterns.readthedocs.io/zh_CN/latest/read_uml.html

https://www.bilibili.com/video/BV1KW411t7FV

6. 计算机网络：https://jiangren.work/2020/02/16/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C%E9%9D%A2%E8%AF%95%E9%A2%98%E7%9B%AE%E6%B1%87%E6%80%BB/
7. 数据库：https://leetcode-cn.com/u/crystal_yyf/
8. 项目：网融公司实习
9. qt:

时间、资料、方法





# cpp基础



## 重载（overload）覆盖（override）和重写（overwrite）区别

重载指同一作用域内，的同名函数、不同参数类型或者个数不同。称这些函数为重载函数 是静多态的一种。

覆盖指子类重新实现了父类的虚函数，要求同名同参同返回，此时子类虚函数表中只存在子类虚函数入口。是动多态的条件之一。

重写指父类与子类中存在同名函数，不考虑参数与返回值，此时在子类中默认访问子类新的实现体，如果要访问父类版实现，需要显式写出父类作用域，这种情况也称子类隐藏了父类函数。

**一、重载（overload）**
指函数名相同，但是它的参数列表个数或顺序，类型不同。但是不能靠返回类型来判断,即不以返回值类型不同作为函数重载的条件
（1）同一类中（在同一个作用域中） ；
（2）函数名字相同；
（3）参数不同；
（4）virtual 关键字可有可无。
（5）返回值可以不同；

**二、覆盖 （override）**
是指派生类重新定义基类的虚函数，特征是：
（1）不在同一个作用域（分别位于派生类与基类） ；
（2）函数名字相同；
（3）参数相同；
（4）基类函数必须有 virtual 关键字，不能有 static 。
（5）返回值相同（或是协变），否则报错；<—-协变这个概念我也是第一次才知道…

（6）重写函数的访问修饰符可以不同。尽管 virtual 是 private 的，派生类中重写改写为 public,protected 也是可以的

**三、重写（overwrite）**

指父类与子类中存在同名函数，不考虑参数与返回值，此时在子类中默认访问子类新的实现体，如果要访问父类版实现，需要显式写出父类作用域，这种情况也称子类隐藏了父类函数。

（1）不在同一个作用域（分别位于派生类与基类） ；
（2）函数名字相同；
（3）返回值可以不同；
（4）参数不同。此时，不论有无 virtual 关键字，基类的函数将被隐藏（注意别与重载以及覆盖混淆） 。
（5）参数相同，但是基类函数没有 virtual关键字。此时，基类的函数被隐藏（注意别与覆盖混淆） 。

## C++中是如何申请内存的

在C++中，虚拟内存分为代码段、数据段、BSS段、堆区、文件映射区以及栈区六部分。

栈：使用栈空间存储函数的返回地址、参数、局部变量、返回值

映射区:存储动态链接库以及调用mmap函数进行的文件映射

堆区：调用new/malloc函数时在堆区动态分配内存，同时需要调用delete/free来手动释放申请的内存。

bss 段：存储未初始化的全局变量和静态变量（局部+全局），以及所有被初始化为0的全局变量和静态变量。

数据段：存储程序中已初始化的全局变量和静态变量

代码段:包括只读存储区和文本区，其中只读存储区存储字符串常量，文本区存储程序的机器代码。



32bitCPU可寻址4G线性空间，每个进程都有各自独立的4G逻辑地址，其中0~3G是用户态空间，3~4G是内核空间，不同进程相同的逻辑地址会映射到不同的物理地址中。

![](/images/202103/4.png)

https://www.runoob.com/cplusplus/cpp-dynamic-memory.html

https://blog.csdn.net/qq_33266987/article/details/51965221

* 虚拟内存的五大段，内存的五大分区

https://blog.csdn.net/ganpengjin1/article/details/38269989

少了一个映射区

## new与malloc

new是关键字不需要引入库，malloc是库函数，

new返回完整类型指针，malloc返回（void*）类型需要强转。

malloc失败返回null指针，new失败抛出异常（bad_alloc）.

new会调用构造函数和析构函数

|         特征         |              new/delete               |             malloc/free              |
| :------------------: | :-----------------------------------: | :----------------------------------: |
|    分配内存的位置    |              自由存储区               |                  堆                  |
| 内存分配成功的返回值 |             完整类型指针              |                void*                 |
| 内存分配失败的返回值 |             默认抛出异常              |               返回NULL               |
|    分配内存的大小    |       由编译器根据类型计算得出        |          必须显式指定字节数          |
|       处理数组       |       有处理数组的new版本new[]        | 需要用户计算数组的大小后进行内存分配 |
|   已分配内存的扩充   |            无法直观地处理             |         使用realloc简单完成          |
|     是否相互调用     | 可以，看具体的operator new/delete实现 |             不可调用new              |
|  分配内存时内存不足  | 客户能够指定处理函数或重新制定分配器  |       无法通过用户代码进行处理       |
|       函数重载       |                 允许                  |                不允许                |
|  构造函数与析构函数  |                 调用                  |                不调用                |

https://www.cnblogs.com/qg-whz/p/5140930.html

https://blog.csdn.net/nie19940803/article/details/76358673

## 多态

https://github.com/czs108/Cpp-Primer-5th-Notes-CN/tree/master/Chapter-15%20Object-Oriented%20Programming

三个条件
1、基类有虚函数
2、派生类覆盖（override）了基类虚函数。（要求函数名，返值类型，函数参数个数及类型全部匹配。并根据派生类的需要重新定义函数体。可以为任意访问权限）
3、通过基类指针或引用指向派生类对象，调用公共接口（虚函数）

如果派生类重写（overwrite）基类的函数比如没加virtual，以及函数重载和模板，那叫静多态，因为函数调用在程序执行前就准备好了。

## 虚函数表机制

https://blog.csdn.net/lihao21/article/details/50688337

* 虚函数与多态

多态的实现主要分为静态多态和动态多态，静态多态主要是重载、模板，在编译的时候就已经确定；动态多态是用虚函数机制实现的，在运行期间动态绑定。举个例子：一个父类类型的指针指向一个子类对象时候，使用父类的指针去调用子类中重定义了的父类中的虚函数的时候，会调用子类重写过后的函数，在父类中声明为加了virtual关键字的函数，在子类中重写时候不需要加virtual也是虚函数。
虚函数的实现：在有虚函数的类中，**类的最开始部分是一个虚函数表的指针**，这个指针指向一个虚函数表，表中放了虚函数的地址，实际的虚函数在代码段(.text)中。当子类继承了父类的时候也会继承其虚函数表，当子类重写父类中虚函数时候，会将其继承到的虚函数表中的地址替换为重新写的函数地址。（父类和子类分别有自己的虚函数表）使用了虚函数，会增加访问内存开销，降低效率。

C++中，一个类存在虚函数，那么编译器就会为这个类生成一个虚函数表，在虚函数表里存放的是这个类所有虚函数的地址。当生成类对象的时候，编译器会自动的将类对象的前四个字节设置为虚表的地址，而这四个字节就可以看作是一个指向虚函数表的指针。**虚函数表可以看做一个函数指针数组。**



1. 虚函数表属于类，类的所有对象共享这个类的虚函数表。
2. 虚函数表由编译器在编译时生成，保存在.rdata只读数据段。

https://blog.csdn.net/qq_39885372/article/details/105626044

https://blog.csdn.net/fw72fw72/article/details/68488739?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.baidujs&dist_request_id=1328707.494.16166787919971027&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.baidujs

### 虚函数与数据封装private

C++中**, 虚函数**可以为private, 并且可以被子类覆盖（因为虚函数表的传递），但子类不能调用父类的private虚函数。虚函数的重载性和它声明的权限无关。

一个成员函数被定义为private属性，标志着其只能被当前类的其他成员函数(或友元函数)所访问。而virtual修饰符则强调父类的成员函数可以在子类中被重写，因为重写之时并没有与父类发生任何的调用关系，故而重写是被允许的。

编译器不检查虚函数的各类属性。被virtual修饰的成员函数，不论他们是private、protect或是public的，都会被统一的放置到虚函数表中。对父类进行派生时，子类会继承到拥有相同偏移地址的虚函数表（相同偏移地址指，各虚函数相对于VPTR指针的偏移），则子类就会被允许对这些虚函数进行覆盖。且覆盖时可以给重载函数定义新的属性，例如public，其只标志着该重载函数在该子类中的访问属性为public，和父类的private属性没有任何关系！

**纯虚函数**可以设计成私有的，不过这样不允许在本类之外的非友元函数中直接调用它，子类中只有覆盖这种纯虚函数的义务，却没有调用它的权利。

https://www.cnblogs.com/yangguang-it/p/6547314.html

## 多继承与虚继承

https://blog.csdn.net/qq_41589622/article/details/109783029

https://github.com/czs108/Cpp-Primer-5th-Notes-CN/blob/master/Chapter-18%20Tools%20for%20Large%20Programs/README.md#%E5%A4%9A%E9%87%8D%E7%BB%A7%E6%89%BF%E4%B8%8E%E8%99%9A%E7%BB%A7%E6%89%BFmultiple-and-virtual-inheritance

### 多重继承（Multiple Inheritance）

是指派生类可以有多个基类。多重继承的派生类继承了所有父类的属性。

### 虚继承（Virtual Inheritance）

尽管在派生列表中同一个基类只能出现一次，但实际上派生类可以多次继承同一个类。如果某个类在派生过程中出现了多次，则派生类中会包含该类的多个子对象。这种默认情况对某些类并不适用，存在二义性。虚继承可以让某个类共享它的基类，其中共享的基类子对象称为虚基类（virtual base class）。在该机制下，不论虚基类在继承体系中出现了多少次，派生类都只包含唯一一个共享的虚基类子对象。

## 类型转换（Type Conversions）

无须程序员介入，会自动执行的类型转换叫做隐式转换（implicit conversions）。

* 算术转换（Integral Promotions）

把一种算术类型转换成另一种算术类型叫做算术转换。

整型提升（integral promotions）负责把小整数类型转换成较大的整数类型。

* 其他隐式类型转换（Other Implicit Conversions）

在大多数表达式中，数组名字自动转换成指向数组首元素的指针。

常量整数值0或字面值`nullptr`能转换成任意指针类型；指向任意非常量的指针能转换成`void*`；指向任意对象的指针能转换成`const void*`。

任意一种算术类型或指针类型都能转换成布尔类型。如果指针或算术类型的值为0，转换结果是`false`，否则是`true`。

指向非常量类型的指针能转换成指向相应的常量类型的指针。

* 显式转换（Explicit Conversions）

显式类型转换也叫做强制类型转换（cast）。命名的强制类型转换（named cast）形式如下：

```
cast-name<type>(expression);
```

其中*type*是转换的目标类型，*expression*是要转换的值。如果*type*是引用类型，则转换结果是左值。*cast-name*是`static_cast`、`dynamic_cast`、`const_cast`和`reinterpret_cast`中的一种，用来指定转换的方式。



- `dynamic_cast`：运行时类型识别。当expression指向目标类型时，返回目标类型对象地址，否则返回0，0，720
- `static_cast`：用于各种隐式转换，比如非const转const，void*转指针等
- `const_cast`：用于将const变量转为非const
- `reinterpret_cast`：几乎什么都可以转，比如将int转指针，可能会出问题，尽量少用；

早期版本的C++语言中，显式类型转换包含两种形式：

```
type (expression);    // function-style cast notation
(type) expression;    // C-language-style cast notation
```

https://blog.csdn.net/qq_41589622/article/details/106464662



## 数组与链表的区别

链表：可以无限拉链，分配的空间可以不连续

数组：分配的空间必须连续，数组如果溢出，需要新建更大的数组再搬迁数据，比较麻烦。

查找：效率的话，数组是优于链表的，因为通过下标访问是O(1)。而链表需要遍历，O(n)。
插入删除：链表比较方便，需要断开节点，插入或者删除节点，而数组可能需要后移数据。

## 智能指针

https://www.cnblogs.com/lanxuezaipiao/p/4132096.html

C++里面的四个智能指针: auto_ptr, shared_ptr, weak_ptr, unique_ptr 其中后三个是c++11支持，并且第一个已经被11弃用。

为什么要使用智能指针：

智能指针的作用是管理一个指针，因为存在以下这种情况：申请的空间在函数结束时忘记释放，造成内存泄漏。使用智能指针可以很大程度上的避免这个问题，因为智能指针就是一个类，当超出了类的作用域是，类会自动调用析构函数，析构函数会自动释放资源。所以智能指针的作用原理就是在函数结束时自动释放内存空间，不需要手动释放内存空间。

https://zhuanlan.zhihu.com/p/79850064

## 指针和引用的区别

指针所指向的内存空间在程序运行过程中可以改变，而引用所绑定的对象一旦绑定就不能改变。（是否可变）
指针本身在内存中占有内存空间，引用相当于变量的别名，在内存中不占内存空间（是否占内存）
指针可以为空，但是引用必须绑定对象（是否可为空）
指针可以有多级，但是引用只能一级（是否能为多级）

作者：Crystal
链接：https://leetcode-cn.com/circle/discuss/f40g4J/

## cpp的左值和右值

*左值 (lvalue, locator value)* 表示了一个占据内存中某个可识别的位置（也就是一个地址）的对象。

*右值 (rvalue)* 则使用排除法来定义。一个表达式不是 *左值* 就是 *右值* 。 那么，右值是一个 *不* 表示内存中某个可识别位置的对象的表达式。

https://nettee.github.io/posts/2018/Understanding-lvalues-and-rvalues-in-C-and-C/

## cpp程序编译过程

https://leetcode-cn.com/circle/discuss/f40g4J/



编译过程分为四个过程：编译（编译预处理、编译、优化），汇编，链接

编译预处理：处理以 # 开头的指令
编译、优化：将源码 .cpp 文件翻译成 .s 汇编代码
汇编：将汇编代码 .s 翻译成机器指令 .o 文件
链接：汇编程序生成的目标文件并不会立即执行，可能有源文件中的函数引用了另一个源文件中定义的符号或者调用了某个库文件中的函数。那链接的目的就是将这些目标文件连接成一个整体，从而生成可执行的程序 .exe 文件。

链接分类：

静态链接：代码从其所在的动态链接库中拷贝到最终的可执行程序中，在该程序被执行时，这些代码会被装入到该进程的虚拟地址空间中
动态链接：代码被放到动态链接库或共享对象的某个目标文件中，链接程序只是在最终的可执行程序中记录了共享对象的名字等一些信息。在程序执行时，动态链接库的全部内容会被映射到运行时相应进行的虚拟地址的空间

二者的优缺点：

静态链接 浪费空间，每个可执行程序都会有目标文件的一个副本，这样如果目标文件进行了更新操作，就需要重新进行编译链接生成可执行程序（更新困难）；优点就是执行的时候运行速度快，因为可执行程序具备了程序运行的所有内容
动态链接：节省内存、更新方便，但是动态链接是在程序运行时，每次执行都需要进行链接，性能会有一定的损失。

## 变量作用域

作用域是程序的一个区域，一般来说有三个地方可以定义变量：

- 在函数或一个代码块内部声明的变量，称为局部变量。
- 在函数参数的定义中声明的变量，称为形式参数。
- 在所有函数外部声明的变量，称为全局变量。

## RTTI（run-time type identification) 运行时类型识别



## 请你回答一下C++中拷贝赋值函数的形参能否进行值传递？

不能。如果是这种情况下，调用拷贝构造函数的时候，首先要将实参传递给形参，这个传递的时候又要调用拷贝构造函数。。如此循环，无法完成拷贝，栈也会满。

## bits/stdc++.h头文件

https://blog.csdn.net/prime_lee/article/details/80489284

很多小伙伴估计看有的代码会碰见没有多余的其它头文件比如algorithm、cmath、iostream而是用了一行#include<bits/stdc++.h>这样的头文件并感到诧异，想这是什么。其实这是一个包含了C++所有头文件的一个头文件，为了方便而发明的，其中包含了一下头文件


## fork（）函数

https://cloud.tencent.com/developer/article/1338482



## 类中的引用成员变量

https://blog.csdn.net/lazyq7/article/details/48186291

* 请你回答一下C++类内可以定义引用数据成员吗？

可以，必须通过成员函数初始化列表初始化。

## 异常处理

https://github.com/czs108/Cpp-Primer-5th-Notes-CN/tree/master/Chapter-18%20Tools%20for%20Large%20Programs#%E5%BC%82%E5%B8%B8%E5%A4%84%E7%90%86exception-handling

### noexcept异常说明

https://blog.csdn.net/LaoJiu_/article/details/50781352

## 野指针

https://blog.csdn.net/bl520025/article/details/8967648

### <<移位运算

    //1*2^(n-1)
    int value = 1 << (n - 1);
https://stackoverflow.com/questions/10983078/c-what-does-ab-mean#

1 << 1 means:

```cpp
00000000 00000001 changes to 00000000 00000010
```

1 << 8 means:

```cpp
00000000 00000001 changes to 00000001 00000000
```

It's a bit shift operation. For every 1 on the right, you can think of yourself as multiplying the value on the left by 2. So, 2 << 1 = 4 and 2 << 2 = 8. This is much more efficient than doing 1 * 2.

Also, you can do 4 >> 1 = 2 (and 5 >> 1 = 2 since you round down) as the inverse operation.

## 请你说说内存泄漏如何判断

1.尽量不去手动[分配内存](https://www.baidu.com/s?wd=分配内存&tn=SE_PcZhidaonwhc_ngpagmjz&rsv_dl=gh_pc_zhidao)。比如，我一般不使用数组，而使用STL的vector.

 2.如果需要手动分配数组，尽量使用STL中的分配方式，或者使用STL和BOOST中的[智能指针](https://www.baidu.com/s?wd=智能指针&tn=SE_PcZhidaonwhc_ngpagmjz&rsv_dl=gh_pc_zhidao)。

 3.某些应用，比如MSXML，尽量使用[智能指针](https://www.baidu.com/s?wd=智能指针&tn=SE_PcZhidaonwhc_ngpagmjz&rsv_dl=gh_pc_zhidao)。

 4.凡是使用new和delete的地方，首先注意指针的初始化，然后要注意new和delete的配对，再就是要注意错误的捕捉。很多时候，内存泄漏不是因为new和delete的配对造成的，而是在自己没有考虑到的可能结果中，程序中断而没有delete手动分配的内存.

 5.使用varglind，mtrace检测,不过我只听说过，没用过。

## cpp多线程

https://zhuanlan.zhihu.com/p/194198073



## 构造函数为什么不能是虚指针

 虚函数的调用需要虚函数表指针，而该指针存放在对象的内容空间中；若构造函数声明为虚函数，那么由于对象还未创建，还没有内存空间，更没有虚函数表地址用来调用虚函数——构造函数了。

https://www.cnblogs.com/ktao/p/7563289.html

# STL

https://leetcode-cn.com/circle/discuss/qNLDjE/

### 六大组件

* 空间配置器 allocator:负责动态空间的配置和管理
* 迭代器 iterators: 在23个设计模式中，有一种是迭代器模式（提供一种方法，使之能够依序访问某个容器所含的各个元素，而无需暴露该容器的内部表述方式），其行为类似于智能指针；STL的设计中，将数据容器和算法分开，彼此独立设计，通过迭代器(容器和算法的胶粘剂)将他们撮合在一起。
* 容器 containers ：各种数据结构，从实现的角度来看，STL容器是一种class template
* 算法 algorithms: 算法作用于容器。它们提供了执行各种操作的方式，包括对容器内容执行初始化、排序、搜索和转换等操作。从实现角度来看，STL算法是一种function template
* 仿函数 functors:也叫函数对象，行为类似于函数；从实现角度来看，仿函数是重载了operator()的class或者class template 
* 配接器 adapters:用来修饰容器或者迭代器接口

### 常用容器

### 序列式容器

* vector：可变大小的数组。底层数据结构为数组，支持快速随机访问，在尾部之外的位置插入或者删除元素可能很慢

1. vector底层存储机制
   vector是一个动态数组，里面是一个指针指向一片连续的空间，当空间不够用时，会自动申请一块更大的空间（一般是增加当前容量的50%或者100%），然后把原来的数据拷贝过去，接着释放原来的空间；当释放或者删除里面的数据时，其存储空间不释放，仅仅是清空了里面的数据

* string：与vector相似的容器，专门用于存储字符。随机访问快，在尾位置插入/删除速度快
* list：双向链表。底层数据结构为双向链表，支持双向顺序访问。在list任何位置插入/删除速度很快

1. list以节点为单位存放数据，节点的地址在内存中不一定连续，每次插入或者删除数据时，就配置或者释放一个元素的空间

* deque：双端队列。底层数据结构为一个中央控制器和多个缓冲区，支持快速随机访问，在头尾位置插入/删除速度很快

1. deque底层存储机制
   deque动态的以分段连续的空间组成，随时可以增加一段新的连续的空间并链接起来，不提供空间保留（reserve）功能。
   deque采用一块map(不是STL的map容器)作为主控，其为一小块连续的空间，其中的每个元素都是指针，指向另一段较大的连续空间（缓冲区）

* priority_queue：优先队列。底层用vector实现，堆heap为处理规则来管理底层容器的实现

  源码剖析p183

  ```
  #include<queue>
  priority_queue<int, vector<int>, less<int>> maxHeap;
  priority_queue<int, vector<int>, greater<int>> minHeap;最小堆
  https://blog.csdn.net/my_lovely_lemon_tree/article/details/78007316
  ```

  

* stack：栈。底层用deque实现，封闭头部，在尾部进行插入和删除元素

* queue：队列。底层用deque实现

### 关联式容器

* set：集合。底层为红黑树，元素有序，不重复

* map：底层为红黑树，键有序，不重复

  红黑树详解：https://xieguanglei.github.io/blog/post/red-black-tree.html

1. map底层机制
   map以红黑树作为底层机制，红黑树是平衡二叉树的一种，在要求上比AVL树更宽泛。通过map的迭代器只能修改其实值，不能修改其键值，所以map的迭代器既不能是const也不是mutable

2. 红黑树满足以下几个条件：
   每个节点不是红色就是黑色
   根节点是黑色
   红色节点的子节点必须是黑色（不能有连续的红节点）
   从根节点到NULL的任何路径所含的黑节点数目相同
   叶子节点是黑色的NULL节点（注：这里不是我们常说的二叉树中的叶节点，是它的子节点（NULL））

3. 是弱平衡的二叉搜索树，查找删除添加时间复杂度都是log(n)

* hash_map：底层为哈希表，无序，不重复

1. 说一下unordered_map是底层是数组加链表，如果冲突则在相同哈希值的链表尾部添加节点。（拉链法）
   说说其他解决哈希冲突的方法：再哈希还有线性探测。

## 迭代器 iterator

https://leetcode-cn.com/circle/discuss/musCOX/

数组型数据结构，如vector：该数据结构的元素是分配在连续的内存中，insert和erase操作，都会使得删除点和插入点之后的元素挪位置，所以，插入点和删除掉之后的迭代器全部失效，也就是说insert(*iter)(或erase(*iter))，然后在iter++，是没有意义的。解决方法：erase(*iter)的返回值是下一个有效迭代器的值。 iter =cont.erase(iter);

链表型数据结构，如list：对于list型的数据结构，使用了不连续分配的内存，删除运算使指向删除位置的迭代器失效，但是不会失效其他迭代器.解决办法两种，erase(*iter)会返回下一个有效迭代器的值，或者erase(iter++).

树形数据结构，如set、map： 使用红黑树来存储数据，插入不会使得任何迭代器失效；删除运算使指向删除位置的迭代器失效，但是不会失效其他迭代器.erase迭代器只是被删元素的迭代器失效，但是返回值为void，所以要采用erase(iter++)的方式删除迭代器。

注意：经过erase(iter)之后的迭代器完全失效，该迭代器iter不能参与任何运算，包括iter++,*ite《大佬的解释》


## 常见容器的操作时间复杂度

https://blog.csdn.net/qq_36631379/article/details/108833192

## stl常见容器api总结

http://vernlium.github.io/2019/12/29/C-STL%E5%B8%B8%E7%94%A8%E5%AE%B9%E5%99%A8API%E6%80%BB%E7%BB%93/

## stl常用算法总结

https://www.cnblogs.com/linuxAndMcu/p/10264339.html

# 数据结构与算法





## 求二叉树的最大高度

https://cloud.tencent.com/developer/article/1491138

https://www.geeksforgeeks.org/iterative-method-to-find-height-of-binary-tree/

### 二叉树定义与遍历

```
typedef struct BiTNode{
	ElemType data;
	Struct BiTNode *lchild, *rchild;
}BiTNode,*BiTree;
void preOrder(BiTree T){
	if(T != NULL){
		visit(T);
		preOrder(T->lchild);
		preOrder(T->rchild);
	}
}

void layerTrace(BTreeNode *T)
{
	if(T== nullptr)return;
	BTreeNode *p=T;
	queue<BTreeNode*>q;
	q.push(p);
	while(!q.empty())
	{
		p=q.front();
		q.pop();
		cout<<<<p->data;
		if(p->left!= nullptr)q.push(p->left);
		if(p->right!= nullptr)q.push(p->right);
	}
}
```



递归遍历

## 堆排序

https://blog.csdn.net/qq_41589622/article/details/107853558

王道p330

## 快速排序

https://leetcode-cn.com/leetbook/read/illustration-of-algorithm/ohwddh/

```
   void quickSort(vector<int>& arr, int l, int r) {
        // 子数组长度为 1 时终止递归
        if (l >= r) return;
        // 哨兵划分操作（以 arr[l] 作为基准数）
        int i = l, j = r;
        while (i < j) {
            while (i < j && arr[j] >= arr[l]) j--; //不能用i <= j，会出错
            while (i < j && arr[i] <= arr[l]) i++;
            swap(arr[i], arr[j]);
        }
        swap(arr[i], arr[l]);
        // 递归左（右）子数组执行哨兵划分
        quickSort(arr, l, i - 1);
        quickSort(arr, i + 1, r);
    }
```

* 链表的快速排序（不交换节点只交换值）
```
class Solution {
public:
    ListNode *sortList(ListNode *head) {
        // IMPORTANT: Please reset any member data you declared, as
        // the same Solution instance will be reused for each test case.
        //链表快速排序
        if(head == NULL || head->next == NULL)return head;
        qsortList(head, NULL);
        return head;
    }
    void qsortList(ListNode*head, ListNode*tail)
    {
        //链表范围是[low, high)
        if(head != tail && head->next != tail)
        {
            ListNode* mid = partitionList(head, tail);
            qsortList(head, mid);
            qsortList(mid->next, tail);
        }
    }
    ListNode* partitionList(ListNode*low, ListNode*high)
    {
        //链表范围是[low, high)
        int key = low->val;
        ListNode* loc = low;
        for(ListNode*i = low->next; i != high; i = i->next)
            if(i->val < key)
            {
                loc = loc->next;
                swap(i->val, loc->val);
            }
        swap(loc->val, low->val);
        return loc;
    }
};
```



## 折半查找

又称二分查找

基本思想：首先给定值key与表中中间的位置的元素比较，若相等，则查找成功，返回该元素的存储位置；若不等，则所需查找的元素只能在中间元素以外的前半部分或后半部分。然后在缩小的范围内继续进行同样的查找，如此重复，直到找到为止，或确定表中没有所需要查找的元素，则查找不成功，返回查找失败信息。

```
int Binary_Search(SeqList L, ElemType key){
	int low = 0, high = L.length - 1, mid;
	while(low <= high){
		mid = (low + high)/2;
		if(L[mid] == key) return mid;
		else if(L[mid] > key){
			high = mid - 1;
		}else{
			low = mid + 1;
		}
	}
	return -1;
}
```

## 常见的排序算法

1、冒泡排序：

从数组中第一个数开始，依次遍历数组中的每一个数，通过相邻比较交换，每一轮循环下来找出剩余未排序数的中的最大数并“冒泡”至数列的顶端。

稳定性：稳定

平均时间复杂度：O(n ^ 2)

2、插入排序：

从待排序的n个记录中的第二个记录开始，依次与前面的记录比较并寻找插入的位置，每次外循环结束后，将当前的数插入到合适的位置。

稳定性：稳定

平均时间复杂度：O(n ^ 2)

3、希尔排序（缩小增量排序）：

希尔排序法是对相邻指定距离(称为增量)的元素进行比较，并不断把增量缩小至1，完成排序。

希尔排序开始时增量较大，分组较多，每组的记录数目较少，故在各组内采用直接插入排序较快，后来增量di逐渐缩小，分组数减少，各组的记录数增多，但由于已经按di−1分组排序，文件叫接近于有序状态，所以新的一趟排序过程较快。因此希尔 排序在效率上比直接插入排序有较大的改进。

在直接插入排序的基础上，将直接插入排序中的1全部改变成增量d即可，因为希尔排序最后一轮的增量d就为1。

稳定性：不稳定

平均时间复杂度：希尔排序算法的时间复杂度分析比较复杂，实际所需的时间取决于各次排序时增量的个数和增量的取值。时间复杂度在O(n ^ 1.3)到O(n ^ 2)之间。

4、选择排序：

从所有记录中选出最小的一个数据元素与第一个位置的记录交换；然后在剩下的记录当中再找最小的与第二个位置的记录交换，循环到只剩下最后一个数据元素为止。

稳定性：不稳定

平均时间复杂度：O(n ^ 2)

5、快速排序

1）从待排序的n个记录中任意选取一个记录（通常选取第一个记录）为分区标准;

2）把所有小于该排序列的记录移动到左边，把所有大于该排序码的记录移动到右边，中间放所选记录，称之为第一趟排序；

3）然后对前后两个子序列分别重复上述过程，直到所有记录都排好序。

稳定性：不稳定

平均时间复杂度：O(nlogn)

6、堆排序：

堆：

1、完全二叉树或者是近似完全二叉树。

2、大顶堆：父节点不小于子节点键值，小顶堆：父节点不大于子节点键值。左右孩子没有大小的顺序。

堆排序在选择排序的基础上提出的，步骤：

1、建立堆

2、删除堆顶元素，同时交换堆顶元素和最后一个元素，再重新调整堆结构，直至全部删除堆中元素。

稳定性：不稳定

平均时间复杂度：O(nlogn)

7、归并排序：

采用分治思想，现将序列分为一个个子序列，对子序列进行排序合并，直至整个序列有序。

稳定性：稳定

平均时间复杂度：O(nlogn)

8、桶排序：

步骤：

1）设置一个定量的数组当作空桶子； 常见的排序算法及其复杂度：

2）寻访序列，并且把记录一个一个放到对应的桶子去；

3）对每个不是空的桶子进行排序。

4）从不是空的桶子里把项目再放回原来的序列中。

时间复杂度：O(n+C) ，C为桶内排序时间。

## 哈夫曼编码

1. 构造方法

给定n个权值分别为w1，w2,……,wn的结点，构造哈夫曼的算法描述如下：

1) 将这n个结点分别作为n棵仅含一个结点的二叉树，构成森林F

2）构造一个新的结点，从F中选取两棵根结点权值最小的树作为新结点的左、右子树，并且将新结点的权值置为左、右子树上根结点的权值之和。

3）从F中删除刚才选出的两棵树，同时将新得到的树加入F中

4）重复2）3），直至F中仅剩一棵树为止

# 项目

### 功能概述

https://zhuanlan.zhihu.com/p/32754315

1. 能显示x509数字证书的信息
2. 用CSP密码服务对信息进行签名与验证
3. 获取u盾的私钥进行签名（数字证书里面没有U盾的私钥），和获取U盾的公钥进行验证

**证书结构：**

https://www.cnblogs.com/jiu0821/p/4598352.html

https://blog.csdn.net/weixin_34367845/article/details/86145395（实例）

1.简单类型的编码

1>BOOLEAN：01

2>INTEGER：02

2.构造类型数据的编码

1>序列构造类型：30

2>集合构造类型：31



/*
1、序列号 INTEGER：02
2、签名算法 OBJECT IDENTIFIER：06
3、使用者   12 无 字符串
4、公钥 BIT STRING：03
5、有效期开始，有效期结束 时间 23  UTCTime：17
*/

## windows程序创建过程

https://blog.csdn.net/hyman_c/article/details/53447695

创建一个Windows界面程序共分为7步：

（1）定义WinMain函数（这是windows界面程序的入口）。

（2）定义窗口处理函数。

（3）注册窗口

（4）创建窗口

（5）显示窗口

（6）编写消息循环函数

（7）处理消息(在第二步的窗口处理函数中)

### 定义WinMain函数（这是windows界面程序的入口）

程序入口的wWinMain函数：

lpCmdLine —— 命令行参数，我们执行程序时可以用命令行的形式传入一些参数。

nCmdShow —— 窗口的显示方式，最大化、最小化那种。

```
int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
_In_opt_ HINSTANCE hPrevInstance,
_In_ LPWSTR    lpCmdLine,
_In_ int       nCmdShow)
{
}
```



### 定义窗口处理函数。

```
//窗口处理函数
LRESULT CALLBACK WndProc(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam)
{
    switch (uMsg)
    {
        case WM_DESTROY:
        PostQuitMessage(0);//可以使GetMessage返回0
        break;
        default:
        break;
    }
    return DefWindowProc(hWnd, uMsg, wParam, lParam);
}
```

hWnd —— 是窗口的句柄。

uMsg —— 是传入的消息，它的本质就是无符号整形的数字。

wParam、lParam —— 是消息附带的两个参数。

### 注册窗口

 一个窗口，在创建之前需要先向操作系统进行注册，这类似现实生活中我们开公司，要先向工商局进行注册一样。wce是一个WNDCLASSEX 类型的结构体，这就是一个窗口类，**包含了我们所需要的窗口信息，包括菜单、图标、背景色等等。**它最主要的是两个成员，hInstance是当前程序的实例句柄，lpszClassName是我们注册的类名称，其他的这里不再解释，可参照MSDN。

我们通过RegisterClassEx()这个API将窗口类在操作系统中进行注册，并通过其返回值判断是否注册成功。我们在WinMain函数中进行窗口的注册工作，请看下面的代码：



```cpp

```

```
//注册窗口类
    WNDCLASSEX wce = { 0 };
    wce.cbSize = sizeof(wce);
    wce.cbClsExtra = 0;
    wce.cbWndExtra = 0;
    wce.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
    wce.hCursor = NULL;
    wce.hIcon = NULL;
    wce.hIconSm = NULL;
    wce.hInstance = hInstance;
    wce.lpfnWndProc = wndProc;
    wce.lpszClassName = “Main”;
    wce.lpszMenuName = NULL;
    wce.style = CS_HREDRAW | CS_VREDRAW;
    ATOM nAtom = RegisterClassEx(&wce);
    if (!nAtom )
    {
        MessageBox(NULL, "注册失败", "Infor", MB_OK);
        return 0;
    }

```



### 创建窗口

接下来我们创建窗口，创建窗口我们使用CreateWindowEx系统API，请留意下面它的第2、3两个参数，第二个参数使我们之前注册的窗口类名称，第三个参数是窗口的标题名称，其他的是一些窗口显示的参数，这里不再详细解释。

```
HWND hWnd = CreateWindowEx(0, “Main”, “Window”,
WS_OVERLAPPEDWINDOW, CW_USEDEFAULT, CW_USEDEFAULT, CW_USEDEFAULT, CW_USEDEFAULT, NULL, NULL, g_hInstance, NULL);
```



### 显示窗口

https://blog.csdn.net/hyman_c/article/details/53453438

创建了窗口之后，如果要想运行时能够看见窗口，我们还需要显式的设置显示窗口：

```
ShowWindow(hWnd, SW_SHOW);
UpdateWindow(hWnd);
```

​       ShowWindow和UpdateWindow都是系统的API，前者是设置窗口的显示属性。后者重新更新窗口属性，只有更新之后，属性才会生效。


### 编写消息循环函数

所谓的消息循环，其实就是一个死循环，不断的通过GetMessage捕捉着窗口的消息，再通过TranslateMessage将虚拟键消息转换为字符消息,然后通过DispatchMessage将消息分派给我们的窗口程序进行处理（所谓处理就是调用我们定义的窗口处理函数WinProc）：



```
void Message()
{
    MSG nMsg = { 0 };
    while (GetMessage(&nMsg, NULL, 0, 0))
    {
        TranslateMessage(&nMsg);
        DispatchMessage(&nMsg);
    }
}
```



### ### 最终代码

```
#include<windows.h>
HINSTANCE g_hInstance = 0;
//窗口处理函数
LRESULT CALLBACK WndProc(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam)
{
	switch (uMsg)
	{
	case WM_DESTROY:
		PostQuitMessage(0);//可以使GetMessage返回0
		break;
	default:
		break;
	}
	return DefWindowProc(hWnd, uMsg, wParam, lParam);
}
//注册窗口类
BOOL Register(LPSTR lpClassName, WNDPROC wndProc)
{
	WNDCLASSEX wce = { 0 };
	wce.cbSize = sizeof(wce);
	wce.cbClsExtra = 0;
	wce.cbWndExtra = 0;
	wce.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
	wce.hCursor = NULL;
	wce.hIcon = NULL;
	wce.hIconSm = NULL;
	wce.hInstance = g_hInstance;
	wce.lpfnWndProc = wndProc;
	wce.lpszClassName = lpClassName;
	wce.lpszMenuName = NULL;
	wce.style = CS_HREDRAW | CS_VREDRAW;
	ATOM nAtom = RegisterClassEx(&wce);
	if (nAtom == 0)
		return FALSE;
	return true;
 
}
//创建主窗口
HWND CreateMain(LPSTR lpClassName, LPSTR lpWndName)
{
	HWND hWnd = CreateWindowEx(0, lpClassName, lpWndName,
		WS_OVERLAPPEDWINDOW, CW_USEDEFAULT, CW_USEDEFAULT, CW_USEDEFAULT, CW_USEDEFAULT, NULL, NULL, g_hInstance, NULL);
	return hWnd;
}
//显示窗口
void Display(HWND hWnd)
{
	ShowWindow(hWnd, SW_SHOW);
	UpdateWindow(hWnd);
}
//消息循环
void Message()
{
	MSG nMsg = { 0 };
	while (GetMessage(&nMsg, NULL, 0, 0))
	{
		TranslateMessage(&nMsg);
		DispatchMessage(&nMsg);
	}
}
int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
	_In_opt_ HINSTANCE hPrevInstance,
	_In_ LPWSTR    lpCmdLine,
	_In_ int       nCmdShow)
{
	// TODO: Place code here.
 
	g_hInstance = hInstance;
	BOOL nRet = Register("Main", WndProc);
	if (!nRet)
	{
		MessageBox(NULL, "注册失败", "Infor", MB_OK);
		return 0;
	}
	HWND hWnd = CreateMain("Main", "window");
	Display(hWnd);
	Message();
	return 0;
}
```



## Base64的编码与解码

原理：

http://blog.chacuo.net/719.html

c语言实现：

https://blog.csdn.net/qq_26093511/article/details/78836087





# 数据库

## 连接查询



left join （左连接）：返回包括左表中的所有记录和右表中连接字段相等的记录。
right join （右连接）：返回包括右表中的所有记录和左表中连接字段相等的记录。
inner join （等值连接或者叫内连接）：只返回两个表中连接字段相等的行。
full join （全外连接）：返回左右表中所有的记录和左右表中连接字段相等的记录。

原文链接：https://blog.csdn.net/weixin_39220472/article/details/81193617

# vscode的cpp配置

<https://zhuanlan.zhihu.com/p/87864677>



<!--more-->

task.json

```

{
    // See https://go.microsoft.com/fwlink/?LinkId=733558 
    // for the documentation about the tasks.json format
    "version": "2.0.0",
    "tasks": [
        {
            "type": "shell",
            "label": "g++.exe build active file",//任务的名字，就是刚才在命令面板中选择的时候所看到的，可以自己设置
            "command": "D:\\Program Files (x86)\\mingw64\\bin\\g++.exe",
            "args": [//编译时候的参数
                "-g",//添加gdb调试选项
                "${file}",
                "-o",//指定生成可执行文件的名称
                "${fileDirname}\\${fileBasenameNoExtension}.exe"
            ],
            "options": {
                "cwd": "D:\\Program Files (x86)\\mingw64\\bin"
            },
            "problemMatcher": [
                "$gcc"
            ],
            "group": {
                "kind": "build",
                "isDefault": true//表示快捷键Ctrl+Shift+B可以运行该任务
            }
        }
    ]
}
```



launch.json

```

{
    // Use IntelliSense to learn about possible attributes.
    // Hover to view descriptions of existing attributes.
    // For more information, visit: https://go.microsoft.com/fwlink/?linkid=830387
    "version": "0.2.0",
    "configurations": [

        {
            "name": "(gdb) Launch",
            "preLaunchTask": "g++.exe build active file",//调试前执行的任务，就是之前配置的tasks.json中的label字段
            "type": "cppdbg",//配置类型，只能为cppdbg
            "request": "launch",//请求配置类型，可以为launch（启动）或attach（附加）
            "program": "${fileDirname}\\${fileBasenameNoExtension}.exe",//调试程序的路径名称
            "args": [],//调试传递参数
            "stopAtEntry": false,
            "cwd": "${workspaceFolder}",
            "environment": [],
            "externalConsole": true,//true显示外置的控制台窗口，false显示内置终端
            "MIMode": "gdb",
            "miDebuggerPath": "D:\\Program Files (x86)\\mingw64\\bin\\gdb.exe",
            "setupCommands": [
                {
                    "description": "Enable pretty-printing for gdb",
                    "text": "-enable-pretty-printing",
                    "ignoreFailures": true
                }
            ]
        }
    ]
}
```



# LPWSTR to char* 转换

<https://blog.csdn.net/moonlightpeng/article/details/80821872?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.edu_weight&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.edu_weight>

```
int num = WideCharToMultiByte(CP_OEMCP, NULL, (LPWSTR)szKeyName, -1, NULL, 0, NULL, FALSE);
			char* pchar = new char[num];
			WideCharToMultiByte(CP_OEMCP, NULL, (LPWSTR)szKeyName, -1, pchar, num, NULL, FALSE);
			printf("%s\n", pchar);
```

# string to LPCWSTR

<https://blog.csdn.net/wangshubo1989/article/details/50274103>

```
size_t size = vProvList[i].length();
        wchar_t* buffer = new wchar_t[size + 1];
        MultiByteToWideChar(CP_ACP, 0, vProvList[i].c_str(), size, buffer, size * sizeof(wchar_t));
        buffer[size] = 0;  //确保以 '\0' 结尾 
```

# char*转wchar_t*

```
 char *s = "一二三四五六七八";
    DWORD dwNum = MultiByteToWideChar(CP_ACP, 0, s, -1, NULL, 0); // 转换成 宽字节大小9 (8+1)
    WCHAR *wch = new WCHAR[dwNum]; // 按宽字节 new 内存
    memset(wch, 0, dwNum*sizeof(wchar_t)); // 全置0  可以用这句 memset(wch, 0, sizeof(wch));
    // memset( the_array, '\0', sizeof(the_array) ); 这是将一个数组的所以分量设置成零的很便捷的方法
 
    MultiByteToWideChar(CP_ACP, 0, s, -1, wch, dwNum*sizeof(wchar_t)); //s转换到宽字节wch
 
    wcout << wch << endl; // 宽字节输出
```

