---
title: facenet人脸识别算法训练
toc: true
date: 2020-05-15 14:42:42
tags:
categories: 深度学习
---

### 实验环境

<!--more-->

win10

tensorflow 1.12.0

anaconda3.6

**facenet源码地址：**<https://github.com/davidsandberg/facenet>

## 数据预处理

### 下载数据集

fw--->链接：https://pan.baidu.com/s/1kH-OcCCAvLVLP1wEQxlvRg 
提取码：twmg 

64_CASIA-FaceV5
链接：https://pan.baidu.com/s/1vHk_BE6ycoz7ujPSvB9e9A 
提取码：20a7 

CASIA-WebFace
链接：https://pan.baidu.com/s/17m9Ym45g4km7VLCxedQ7GA 
提取码：c951 

Celeba
链接：https://pan.baidu.com/s/18RmCCj7uHfvtkmmb8LZoHw 
提取码：a6ug 

### 用人脸检测模型检测并且剪裁出人脸



