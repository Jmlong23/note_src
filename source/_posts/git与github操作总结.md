---
title: git与github操作总结
toc: true
date: 2021-04-20 21:03:23
tags:
categories: 其他
---

# 提交本地内容到git

1.进入本地仓库，初始化

git init

<!--more-->

在此之前还需要设置username和email，因为github每次commit都会记录他们。

```
$ git config --global user.name "your name"
$ git config --global user.email "your_email@youremail.com"
```

2.添加远程仓库地址

```
git remote add origin yourRepo.git
```

3.修改.git文件夹下面的url如下所示

```
[remote "origin"]
	url = https://username:password@yourRepo.git
	fetch = +refs/heads/*:refs/remotes/origin/*
```

4.本地与远程仓库同步否则会报错

git push到GitHub的时候遇到! [rejected] master -> master (non-fast-forward)的问题

https://blog.csdn.net/xieneng2004/article/details/81044371?utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-1.baidujs&dist_request_id=&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-1.baidujs

git pull origin master --allow-unrelated-histories //把远程仓库和本地同步，消除差异

若是远程仓库为空则不需要这个步骤

5.本地仓库操作

```
git add <filename>
git add *
git commit -m "代码提交信息"
```

6.推送

git push origin master



用ssh未成功

https://www.runoob.com/w3cnote/git-guide.html