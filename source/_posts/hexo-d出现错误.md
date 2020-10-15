---
title: hexo d出现错误
toc: true
date: 2020-06-04 10:46:47
tags: 踩坑
categories: 环境搭建
---

## hexo d 部署时出现以下错误

```
fatal: unable to access 'https://github.com/Jmlong23/Jmlong23.github.io.git/': Failed to connect to github.com port 443: Timed out
FATAL Something's wrong. Maybe you can find the solution here: https://hexo.io/docs/troubleshooting.html
Error: Spawn failed
    at ChildProcess.<anonymous> (D:\blog\blog\node_modules\hexo-util\lib\spawn.js:51:21)
    at ChildProcess.emit (events.js:310:20)
    at ChildProcess.cp.emit (D:\blog\blog\node_modules\cross-spawn\lib\enoent.js:34:29)
    at Process.ChildProcess._handle.onexit (internal/child_process.js:275:12)
```

## 解决方法

用以下配置，我也不知道为什么

```
git config --global http.proxy http://127.0.0.1:1080
```

参考链接：<https://github.com/jeffsui/jeffsui.github.io/issues/76>