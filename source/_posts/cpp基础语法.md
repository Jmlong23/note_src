---
title: c++基础语法
toc: true
date: 2020-09-26 12:58:55
tags:
categories:
---

# LPWSTR to char* 转换

<https://blog.csdn.net/moonlightpeng/article/details/80821872?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.edu_weight&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.edu_weight>

```
int num = WideCharToMultiByte(CP_OEMCP, NULL, (LPWSTR)szKeyName, -1, NULL, 0, NULL, FALSE);
			char* pchar = new char[num];
			WideCharToMultiByte(CP_OEMCP, NULL, (LPWSTR)szKeyName, -1, pchar, num, NULL, FALSE);
			printf("%s\n", pchar);
```

# string to LPCWSTR

<https://blog.csdn.net/wangshubo1989/article/details/50274103>

```
size_t size = vProvList[i].length();
        wchar_t* buffer = new wchar_t[size + 1];
        MultiByteToWideChar(CP_ACP, 0, vProvList[i].c_str(), size, buffer, size * sizeof(wchar_t));
        buffer[size] = 0;  //确保以 '\0' 结尾 
```

# char*转wchar_t*

```
 char *s = "一二三四五六七八";
    DWORD dwNum = MultiByteToWideChar(CP_ACP, 0, s, -1, NULL, 0); // 转换成 宽字节大小9 (8+1)
    WCHAR *wch = new WCHAR[dwNum]; // 按宽字节 new 内存
    memset(wch, 0, dwNum*sizeof(wchar_t)); // 全置0  可以用这句 memset(wch, 0, sizeof(wch));
    // memset( the_array, '\0', sizeof(the_array) ); 这是将一个数组的所以分量设置成零的很便捷的方法
 
    MultiByteToWideChar(CP_ACP, 0, s, -1, wch, dwNum*sizeof(wchar_t)); //s转换到宽字节wch
 
    wcout << wch << endl; // 宽字节输出
```

