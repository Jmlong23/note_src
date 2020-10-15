---
title: hcl路由器ospf和交换机rip配置
toc: true
date: 2020-06-05 22:31:45
tags:
categories: 组网技术
---

实现RT1和RT3的两个环回路lo0互相之间可以通信

![](images/20200605/2.png)

<!--more-->

![](images/20200605/3.png)

![](images/20200605/4.png)

![](images/20200605/5.png)

![](images/20200605/6.png)

实现SW1和SW3的两个环回路lo0互相之间可以通信

![](images/20200605/11.png)

![](../images/20200605/7.png)

![](images/20200605/8.png)

![](images/20200605/9.png)

![](images/20200605/10.png)

注意sw1时network那里错了,应该是172.16.1.1

然后，port的时候还应该有其他信息，没有信息，应该重启一下交换机，因为后来改了的时候没有截图，命令都差不多就放这些图片了。

参考链接：

<https://www.xiaoheidiannao.com/HCL-Switch-RIP-Configuration.html>

<https://www.xiaoheidiannao.com/HCL-Router-OSPF-Configuration.html>