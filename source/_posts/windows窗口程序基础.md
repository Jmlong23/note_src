---
title: windows窗口程序基础
toc: true
date: 2020-09-28 17:11:20
tags:
categories: windows窗口程序
---

# 第一个程序

打开vs-》新建-》Windows桌面想到-》桌面程序-》空项目

<https://blog.csdn.net/hyman_c/article/details/53057037>

<!--more-->

```
#include "windows.h"
int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
    _In_opt_ HINSTANCE hPrevInstance,
    _In_ LPWSTR    lpCmdLine,
    _In_ int       nCmdShow)
{
    MessageBoxA(NULL, "Hello Win32", "sdk", MB_ABORTRETRYIGNORE | MB_ICONERROR);
    return 0;
}
```

"const char *" 类型的实参与 "LPCWSTR" 类型的形参不兼容,改成字符集改成多字节才不会出现乱码

![](images/20200929/2.png)

<https://blog.csdn.net/harrywater123/article/details/51418888>

官方例子

https://docs.microsoft.com/zh-cn/cpp/windows/walkthrough-creating-windows-desktop-applications-cpp?view=vs-2019

# 从零开始创建Windows窗口

<https://blog.csdn.net/hyman_c/article/details/53447695>

```
#include<windows.h>
HINSTANCE g_hInstance = 0;
//窗口处理函数
LRESULT CALLBACK WndProc(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam)
{
	switch (uMsg)
	{
	case WM_DESTROY:
		PostQuitMessage(0);//可以使GetMessage返回0
		break;
	default:
		break;
	}
	return DefWindowProc(hWnd, uMsg, wParam, lParam);
}
//注册窗口类
BOOL Register(LPCSTR lpClassName, WNDPROC wndProc)
{
	WNDCLASSEX wce = { 0 };
	wce.cbSize = sizeof(wce);
	wce.cbClsExtra = 0;
	wce.cbWndExtra = 0;
	wce.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
	wce.hCursor = NULL;
	wce.hIcon = NULL;
	wce.hIconSm = NULL;
	wce.hInstance = g_hInstance;
	wce.lpfnWndProc = wndProc;
	wce.lpszClassName = lpClassName;
	wce.lpszMenuName = NULL;
	wce.style = CS_HREDRAW | CS_VREDRAW;
	ATOM nAtom = RegisterClassEx(&wce);
	if (nAtom == 0)
		return FALSE;
	return true;

}
//创建主窗口
HWND CreateMain(LPCSTR lpClassName, LPCSTR lpWndName)
{
	HWND hWnd = CreateWindowEx(0, lpClassName, lpWndName,
		WS_OVERLAPPEDWINDOW, CW_USEDEFAULT, CW_USEDEFAULT, CW_USEDEFAULT, CW_USEDEFAULT, NULL, NULL, g_hInstance, NULL);
	return hWnd;
}
//显示窗口
void Display(HWND hWnd)
{
	ShowWindow(hWnd, SW_SHOW);
	UpdateWindow(hWnd);
}
//消息循环
void Message()
{
	MSG nMsg = { 0 };
	while (GetMessage(&nMsg, NULL, 0, 0))
	{
		TranslateMessage(&nMsg);
		DispatchMessage(&nMsg);
	}
}
int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
	_In_opt_ HINSTANCE hPrevInstance,
	_In_ LPWSTR    lpCmdLine,
	_In_ int       nCmdShow)
{
	// TODO: Place code here.

	g_hInstance = hInstance;
	BOOL nRet = Register((LPCSTR)"Main", WndProc);
	if (!nRet)
	{
		MessageBox(NULL, "注册失败", "Infor", MB_OK);
		return 0;
	}
	HWND hWnd = CreateMain((LPCSTR)"Main", (LPCSTR)"window");
	Display(hWnd);
	Message();
	return 0;
}
```

# windows程序创建过程

<https://blog.csdn.net/hyman_c/article/details/53447695>

创建一个Windows界面程序共分为7步：

（1）定义WinMain函数（这是windows界面程序的入口）。

（2）定义窗口处理函数。

（3）注册窗口

（4）创建窗口

（5）显示窗口

（6）编写消息循环函数

（7）处理消息(在第二步的窗口处理函数中)

# windows消息机制

<https://blog.csdn.net/hyman_c/article/details/53729066>

看代码1





# 代码1



```

#include <windows.h>

// C 运行时头文件
#include <stdlib.h>
#include <malloc.h>
#include <memory.h>
#include <tchar.h>
#include <stdio.h>
HINSTANCE g_hInstance = 0;
//窗口处理函数 
LRESULT CALLBACK WndProc(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam)
{
	switch (uMsg)
	{
	case WM_DESTROY:
		PostQuitMessage(0);//可以使GetMessage返回0  
		break;
	case  WM_CREATE:
	{
		CREATESTRUCT crt = *((CREATESTRUCT*)lParam);
		char buf[256] = { 0 };
		sprintf(buf, "创建的窗口类名称是%s，窗口名称是%s", crt.lpszClass, crt.lpszName);
		MessageBox(NULL, buf, "消息处理", MB_OK);
	}
	case WM_QUIT:
	{
		int param = (int)wParam;
		char buf[256];
		sprintf(buf, "进程退出，退出码:%d", param);
		MessageBox(NULL, buf, "消息处理", MB_OK);
	}
	case WM_SYSCOMMAND:
	{
		if (wParam == SC_MAXIMIZE)
		{
			short x = LOWORD(lParam);
			short y = HIWORD(lParam);
			char buf[256];
			sprintf(buf, "窗口最大化，x坐标:%d，y坐标:%d", x, y);
			MessageBox(NULL, buf, "消息处理", MB_OK);
		}
	}
	case WM_SIZE:
	{
		if (wParam == SIZE_MAXIMIZED)
		{
			short width = LOWORD(lParam);
			short hight = HIWORD(lParam);
			char buf[256];
			sprintf(buf, "窗口最大化，高度:%d，宽度:%d", hight, width);
			MessageBox(NULL, buf, "消息处理", MB_OK);
		}
	}
	default:
		break;
	}
	return DefWindowProc(hWnd, uMsg, wParam, lParam);
}
//注册窗口类  
BOOL Register(LPSTR lpClassName, WNDPROC wndProc)
{
	WNDCLASSEX wce = { 0 };
	wce.cbSize = sizeof(wce);
	wce.cbClsExtra = 0;
	wce.cbWndExtra = 0;
	wce.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
	wce.hCursor = NULL;
	wce.hIcon = NULL;
	wce.hIconSm = NULL;
	wce.hInstance = g_hInstance;
	wce.lpfnWndProc = wndProc;
	wce.lpszClassName = lpClassName;
	wce.lpszMenuName = NULL;
	wce.style = CS_HREDRAW | CS_VREDRAW;
	ATOM nAtom = RegisterClassEx(&wce);
	if (nAtom == 0)
		return FALSE;
	return true;

}
//创建主窗口  
HWND CreateMain(LPSTR lpClassName, LPSTR lpWndName)
{
	HWND hWnd = CreateWindowEx(0, lpClassName, lpWndName,
		WS_OVERLAPPEDWINDOW, CW_USEDEFAULT, CW_USEDEFAULT, CW_USEDEFAULT, CW_USEDEFAULT, NULL, NULL, g_hInstance, NULL);
	return hWnd;
}
//显示窗口  
void Display(HWND hWnd)
{
	ShowWindow(hWnd, SW_SHOW);
	UpdateWindow(hWnd);
}
//消息循环  
void Message()
{
	MSG nMsg = { 0 };
	//GetMessage()不断的在消息队列中抓取消息
	while (GetMessage(&nMsg, NULL, 0, 0))
	{
		/*
		 GetMessage()获取到消息后，TranslateMessage会将消息进行翻译，
		 主要是把虚拟键消息转换为字符消息。字符消息被寄送到调用线程的消息队列里，
		 当下一次线程调用函数GetMessage或PeekMessage时被读出。
		 Windows中每一个键盘按键，都对应了一个宏，这个键盘按键发出的消息就是虚拟键消息。
		 TranslateMessage的作用就是将虚拟键消息转成字符消息WM_CHAR、WM_SYSCHAR等等。
		*/
		TranslateMessage(&nMsg);
		//把信息发给信息处理函数
		DispatchMessage(&nMsg);
	}
}
int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
	_In_opt_ HINSTANCE hPrevInstance,
	_In_ LPWSTR    lpCmdLine,
	_In_ int       nCmdShow)
{
	// TODO: Place code here.  

	g_hInstance = hInstance;
	BOOL nRet = Register((LPSTR)"Main", WndProc);
	if (!nRet)
	{
		MessageBox(NULL, "注册失败", "Infor", MB_OK);
		return 0;
	}
	HWND hWnd = CreateMain((LPSTR)"Main", (LPSTR)"window");
	Display(hWnd);
	Message();
	return 0;
}
```

