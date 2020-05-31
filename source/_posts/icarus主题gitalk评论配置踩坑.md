---
title: icarus主题gitalk评论配置踩坑
date: 2020-05-04 12:58:44
tags: ["踩坑","环境搭建"]
categories: ["环境搭建","ftp配置"]
---

## 1 在GitHub上面注册一个OAuth Apps

获取相关的id和secret

## 2 在/Icarus/_config.yml下填写以下配置

```
comment:
    type: gitalk
    client_id: *******e35afa66dfd
    client_secret: ********eb0bf8c1
    repo: username.github.io
    owner: username
    admin: [username]
```

<!--more-->

## 3 遇到的错误

我在写admin时直接写了username，结果报一下错误

```
{
keyword: 'type',
dataPath: '.comment.admin',
schemaPath: '/comment/gitalk.json/properties/admin/type',
params: { type: 'array' },
message: 'should be array'
},
```

错误是因为admin接收的时数组array而不是string。所以就算你可以正常应用，但还是会提示warn。

结果没看懂，这个错误，去GitHub问了Icarus原作者，才帮我指正，很谢谢原作者的指正。



**参考资料**

<[https://blog.zhangruipeng.me/hexo-theme-icarus/Plugins/Comment/icarus%E7%94%A8%E6%88%B7%E6%8C%87%E5%8D%97-%E7%94%A8%E6%88%B7%E8%AF%84%E8%AE%BA%E6%8F%92%E4%BB%B6/#Gitalk](https://blog.zhangruipeng.me/hexo-theme-icarus/Plugins/Comment/icarus用户指南-用户评论插件/#Gitalk)>

[icarus项目地址](https://github.com/ppoffice/hexo-theme-icarus)

