---
title: linux操作系统复习总结
toc: true
date: 2020-05-07 11:37:03
tags:
categories: Linux
---

## 1操作系统概述

### 1.1操作系统概述
* 从使用者角度看
* 程序开发者角度，调用操作系统的接口，如read（）函数，这些繁琐的操作留给操作系统
* 从所处位置看，操作系统是上层软件与硬件打交道的窗口和桥梁，是其他所有用户程序运行的基础
* 从设计者角度看，目标是让各种软件资源和硬件资源高效而协调地运转起来
* 操作系统的组成，

<!--more-->

### 1. 2操作系统的发展
* 1946年宾夕法尼亚大学大学出现第一台通用计算机
* 第二代计算机，50s~60s，单道批处理系统
* 第三代计算机，60s中~70s初，多道批处理系统和分时系统和实时系统
* 四代，至今
* 软件角度下，操作系统的发展
* 单模块操作系统：进程管理，内存管理，设备管理，文件管理，通过调用函数交互，效率高，维护困难
* 微内核操作系统，用过通信机制交互，效率较低，维护简单


### 1.3开放源代码的Unix/Linux操作系统 
* Linux之父，linus torvalds
* linux遵循posix标准，所以流行

### 1.4Linux内核 
* 内核子系统
	1. 进程调度
	2. 内存管理
	3. 虚拟文件系统
	4. 网络
	5. 进程间通信

### 1.5Linux内核源代码

### 1.6Linux 内核模块编程入门

### 1.7Linux 内核中链表的实现及应用

## 2虚拟内存管理的硬件机制
### 2.1内存寻址的演变 
* 石器，8位寻址，Intel8080
* 青铜，16位寻址，Intel8086，段的引入
* 白银，保护模式的引入Intel80286
* 黄金，Intel80386，32位
* 常用寄存器
* 用于分页机制的控制寄存器
* 物理地址、虚拟地址及线性地址
### 2.2段机制
* 段表
* 地址转换及保护
### 2.3分页机制 
* 页表
* 两级页表
* 线性地址到物理地址的转换
* 页面高速缓存
### 2.4Linux中的分页机制 
### 2.5Linux中的汇编语言 
### 2.6Linux系统地址映射示例 

## 3进程
### 3.1进程介绍 
* 程序和进程
* 进程层次结构
* 进程状态
### 3.2进程控制块 
* 信息分类
* Linux进程状态及转换 
* 进程标识符 
* 进程之间的亲属关系
* 如何存放 
### 3.3进程的组织方式 
* 进程链表 
* 哈希表 
* 可运行队列 
* 等待队列
* 等待队列的操作
### 3.4进程调度 
* 调度算法
* 时间片 
* 调度时机 
* 调度函数schedule( )-变量说明 
* 调度程序的改进
### 3.5进程的创建 
Fork()函数
线程及其创建 
内核线程
### 3.6与进程相关的系统调用及其应用 
### 3.7与调度相关的系统调用及应用 
## 4内存管理
### 4.1Linux的内存管理
内存的层次结构
扩大了的记忆－虚拟内存
虚地址到实地址转换
虚拟内存、内核空间和用户空间
Linux进程在虚拟内存中的标准内存段布局
内核空间到物理内存的映射
内核映像
虚拟内存实现机制


### 4.2进程的用户空间管理
mm_struct 结构 
VM_AREA_STRUCT 结构 
vm_operation结构
创建进程用户空间
虚存映射
进程的虚存区举例
与用户空间相关的主要系统调用 
mmap() 
### 4.3请页机制
实现虚存管理的重要手段
页故障产生的原因
缺页异常处理程序 
请求调页－动态内存分配技术
写时复制

### 4.4物理内存的分配与回收
物理页描述符 struct page结构
页面分配与回收算法－伙伴算法
物理页面的分配
物理页面的回收
Slab 分配机制－分配小内存
通用缓冲区
内核空间非连续内存区的分配 
vmalloc()与 kmalloc()之区别
### 4.5交换机制
页面交换
选择被换出的页面
在交换区中存放页面 
页面交换策略
页面交换守护进程kswapd 
### 4.6内存管理实例

## 5中断与异常
### 5.1中断的基本知识
中断源的类型
外设可屏蔽中断
异常及非屏蔽中断 
中断描述符表
相关汇编指令 

### 5.2中断描述符表的初始化
IDT表项的设置
初始化陷阱门和系统门
中断门的设置 


### 5.3中断处理
中断和异常的硬件处理 
中断和异常处理中CPU的工作
中断请求队列的建立
中断服务例程与中断处理程序 
中断线共享的数据结构 
注册中断服务例程 


### 5.4中断的下半部处理机制
小任务机制 
工作队列机制
下半部 
任务队列 


### 5.5中断的应用－时钟中断
时钟运作机制 
Linux时间系统 
时钟中断处理程序 
定时器及应用 
## 6系统调用
### 6.1系统调用与API、系统命令、内核函数
系统调用与API
系统调用与系统命令
系统调用与内核函数
系统调用与内核函数



### 6.2系统调用基本概念
系统调用基本概念
系统调用处理程序及服务例程 



### 6.3系统调用实现
调用一个系统调用 
初始化系统调用 
system_call(  )函数 
参数传递 
跟踪系统调用的执行 

### 6.4封装例程

### 6.5添加新的系统调用

### 6.6系统调用实例——日志收集

## 7内核中的同步
### 7.1临界区和竞争状态
并发执行的原因 
临界区举例 
共享队列和加锁 
确定保护对象 
死  锁 
死锁的避免 


### 7.2内核同步措施
原子操作 
自旋锁 
信号量
信号量与自旋锁的比较

### 7.3生产者-消费者并发实例


### 7.4内核多任务并发实例
内核任务及其之间的并发关系 
实现机制 

## 8文件系统
### 8.1Linux文件系统
Linux的文件结构 
文件类型 
访问权限和文件模式 
软链接和硬链接 
安装文件系统 

### 8.2虚拟文件系统
虚拟文件系统的引入 
VFS中对象的演绎
VFS超级块数据结构 
VFS的索引节点 
目录项对象 
目录项对象的数据结构 
与进程相关的文件结构 －文件对象
文件对象数据结构的主要域
与进程相关的文件结构 －用户打开文件表 
与进程相关的文件结构 － fs_struct结构 
主要数据结构间的关系 


### 8.3文件系统的注册、安装与卸载
文件系统的安装 
文件系统的卸载 
页缓冲区 
address_space对象的操作函数表 


### 8.4文件的打开与读写
generic_file_read()函数所执行的主要步骤
从用户发出读请求到最终的从磁盘读取数据的步骤

### 8.5文件系统的编写
Linux文件系统的实现要素 
什么是Romfs文件系统 

## 9设备驱动
### 9.1设备驱动概述

### 9.2设备驱动程序
设备驱动程序基础 

### 9.3专用I/O端口
I/O 端口
设备文件 
中断处理
用中断实现驱动程序的典型例子
设备驱动程序框架 

### 9.4字符设备驱动程序
字符设备驱动程序的注册 
简单字符设备驱动程序举例 


### 9.5块设备驱动程序
块驱动程序的注册 
注册和注销一个驱动程序模块时所要调用的函数及数据结构 
块设备请求 

