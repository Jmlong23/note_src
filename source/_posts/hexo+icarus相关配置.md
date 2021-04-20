---
title: win下Github和hexo搭建个人博客
date: 2020-05-01 11:40:35
tags: ["hexo"]
categories: ["环境搭建","博客搭建"]
---

## 1 Git安装

<https://git-scm.com/download/win>

## 2 Node.js安装

<https://nodejs.org/en/download/>

确认是否安装成功

```
git –version
node -v
npm -v
```

<!--more-->

## 3 GitHub新建一个仓库

名字：username.github.io

##  4 安装hexo

npm install hexo-cli -g

好像要科学上网才可以成功安装

**常用命令：**

```
hexo g //生成
hexo s //开启本地服务器
hexo new "标题"
hexo d //部署
```

**配置config文件**

```
url: https://Jmlong23.github.io 
deploy:
  type: git
  repo: https://github.com/Jmlong23/Jmlong23.github.io.git
  branch: master
```

**更换主题**

1. 在GitHub复制主题仓库地址
2. 把文件复制到themes目录下
3. 在config里面改动theme: hexo-theme-next

**next主题基础操作**

仓库readme有



## hexo隐藏文章

<https://github.com/printempw/hexo-hide-posts>

加上：

hidden: true



## icarus文章布局换成两行

<https://blog.nowcoder.net/n/0ccada7d538b4b62b4587298d8468553>

# 更多配置

https://kuang.netlify.app/blog/hexo.html