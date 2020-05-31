---
title: InputStream和OutputStream
toc: true
date: 2020-05-06 18:25:22
tags:
categories: ["java"]
---

## 1 例子

<!--more-->

```java
import java.io.*;
public class TestRead {
    public static void main(String[] args) throws IOException {//注意一定要抛出异常
        String s;
        try (InputStream input = new FileInputStream("readme.c")) {//1.创建
            s = readAsString(input);
        }
        System.out.println(s);
        OutputStream output = new FileOutputStream("write.txt");// 一、创建
        output.write(s.getBytes("UTF-8")); //二、调用方法
        output.close();
    }

    private static String readAsString(InputStream input) throws IOException {
        int n;
        StringBuilder sb = new StringBuilder();
        while ((n = input.read()) != -1) {//2.使用方法
            System.out.println(n);
            sb.append((char) n);
        }
        return sb.toString();
    }
   
}
```

