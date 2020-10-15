---
title: 安装openssl和gmssl
toc: true
date: 2020-09-21 16:53:27
tags:
categories: 环境搭建
---

参考链接：<https://blog.csdn.net/apianmuse/article/details/107353574>

## 一、安装openssl

直接安装下载地址，一直下一步

<https://slproweb.com/products/Win32OpenSSL.html>

测试

![](/images/20200921/01.png)

<!--more-->

## 二、安装gmssl

### 1 下载Strawberry Perl

ActiveState Perl和 Strawberry Perl最大的区别是 Strawberry Perl 里面有多包含一些 CPAN 里的模块， 所以Strawberry Perl 下载的安装文件有 80多M, 而ActiveState Perl 只有20M 左右。

下载链接：<http://strawberryperl.com/>

### 2 下载gmssl

<http://gmssl.org/docs/quickstart.html>

### 3 NASM下载地址：<www.nasm.us>

### 4 perl Configure VC-WIN32时出错

This issue is due to the Perl package File::Glob, 一些简单的修改就能修复这个. 在 Configure文件和 test/build.info这个文件,把
```
use if $^O ne "VMS", 'File::Glob' => qw/glob/;
改成
use if $^O ne "VMS", 'File::Glob' => qw/:glob/;
```

<https://www.xiaoheidiannao.com/211129.html>

### 5 nmake时出错

libcrypto.* libcrypto-1_1.* && EXIT 1)
libcrypto-1_1.def : error LNK2001: 无法解析的外部符号 EVP_get_ciphernames
libcrypto-1_1.def : error LNK2001: 无法解析的外部符号 EVP_get_digestnames
libcrypto.lib : fatal error LNK1120: 2 个无法解析的外部命令
NMAKE : fatal error U1077: “link”: 返回代码“0x1

解决方法：

<https://github.com/guanzhi/GmSSL/issues/992>



### 6 nmake install

关于glob的错误，未找到解决方法

测试，安装成功在C:\Program Files (x86)\GmSSL目录下

![](/images/20200921/02.png)