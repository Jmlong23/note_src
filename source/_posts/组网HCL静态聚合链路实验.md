---
title: HCL静态聚合链路实验
date: 2020-05-02 16:14:54
tags: ["hcl"]
categories: ["组网技术"]
---

## 1 题目

![](/images/20200502/1.png)

<!--more-->

## 2 我的预览图

![](/images/20200502/2.png)

## 3 配置sw1

1. 配置与pc连接的口vlan10

![](/images/20200502/3.png)

2. sw1与sw3之间的trunk口

![](/images/20200502/4.png)

3. sw1与sw2之间的聚合链路，先配置聚合集，再配置trunk类型

![](/images/20200502/5.png)

4. 验证聚合链路是否配置成功

![](/images/20200502/6.png)

5. 在聚合链路组上配置trunk类型

![](/images/20200502/7.png)

## 3 配置sw3

接口配置为trunk类型

![](/images/20200502/8.png)

## 4 pc1 ping pc2

![](/images/20200502/9.png)

## 5 pc2



![](/images/20200502/10.png)

![](/images/20200502/11.png)



<https://xiaoheidiannao.com/articles/HCL-Link-Aggregation.html>

