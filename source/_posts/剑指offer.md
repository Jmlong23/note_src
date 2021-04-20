---
title: 剑指offer
toc: true
date: 2021-03-10 20:18:53
tags:
categories: leetcode
---

# 05空格替换

```
class Solution {
public:
    string replaceSpace(string s) {
        int len = s.size();
        int count = 0;
        for (int i = 0; i < len; i++) {
            if (s[i] == ' ') {
                count++;
            }
        }
        int length = len + 2 * count;
        s.resize(length); //用s.reserve不行会报错
        int i = len - 1, j = length - 1;
        while (i != j) {
            if (s[i] == ' ') {
                s[j] = '0';
                s[j - 1] = '2';
                s[j - 2] = '%';
                j = j - 2;//漏了
            }
            else {
                s[j] = s[i];
            }
            i--;
            j--;
        }
        return s;
    }
};
```

<!--more-->

## 06从尾到头打印链表

```
class Solution {//看准题意
public:
    vector<int> reversePrint(ListNode* head) {
        stack<int> stk;
        vector<int> res;
        ListNode* cur;//指向头节点啊
        while(cur != nullptr){
            stk.push(cur->val);//用了.
            cur = cur->next;
        }
        while(!stk.empty()){
            res.push_back(stk.top());
            stk.pop();
        }
        return res;
    }
};
```



## 用两个链表实现栈

```
class CQueue {
public:
    CQueue() {

    }
    stack<int> stackA;
    stack<int> stackB;
    void appendTail(int value) {
        stackA.push(value);
    }
    
    int deleteHead() {
        int temp = 0;
        if(stackA.empty() && stackB.empty()){
            return -1;
        }
        if(stackB.empty()){
            while(!stackA.empty()){
                stackB.push(stackA.top());
                stackA.pop();
            }
            temp = stackB.top();
            stackB.pop();
            return temp;
        }
        temp = stackB.top();
        stackB.pop();
        return temp;
    }
};
```



## 反转链表

```
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    ListNode* reverseList(ListNode* head) {
        ListNode *cur = head, *pre = nullptr;
        while(cur != nullptr){
            ListNode *temp = cur->next;
            cur->next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }
};
```

## 有min的栈

```
class MinStack {
public:
    /** initialize your data structure here. */
    MinStack() {

    }
    
    void push(int x) {
        stk.push(x);
        if(minStk.empty() || minStk.top() >= x){
            minStk.push(x);
        }
    }
    
    void pop() {
        if(minStk.top() == stk.top()){
            minStk.pop();
        }
        stk.pop();
    }
    
    int top() {
        return stk.top();
    }
    
    int min() {
        return minStk.top();
    }
private:
    stack<int> stk, minStk;
};

```

## 58左旋转字符串

```
class Solution {
public:
    string reverseLeftWords(string s, int n) {
        return (s.substr(n,s.size()) + s.substr(0,n));
    }
};
```



## 最小k个数

```
class Solution {
public:
    vector<int> getLeastNumbers(vector<int>& arr, int k) {
        quickSort(arr, 0, arr.size() - 1);
        vector<int> res;
        for (int i = 0; i < k; i++) {
            res.push_back(arr[i]);
        }
        return res;
    }
    void quickSort(vector<int>& arr, int l, int r) {
        if (l >= r) return;
        int i = l, j = r;
        while (i < j) {
            while ((i < j) && arr[j] >= arr[l]) j--; //不能用i <= j，会出错
            while ((i < j) && arr[i] <= arr[l]) i++;
            swap(arr[i], arr[j]);
        }
        swap(arr[i], arr[l]);
        quickSort(arr, l, i - 1);
        quickSort(arr, i + 1, r);
    }
};
```



## 数据流中的中位数

```
class MedianFinder {
public:
    /** initialize your data structure here. */
    MedianFinder() {

    }
    
    void addNum(int num) {
        if(minHeap.size() == maxHeap.size()){
            maxHeap.push(num);
            minHeap.push(maxHeap.top());
            maxHeap.pop();
        }else{
            minHeap.push(num);
            maxHeap.push(minHeap.top());
            minHeap.pop();
        }
    }
    
    double findMedian() {
        if(minHeap.size() == maxHeap.size()){
            return (minHeap.top() + maxHeap.top())/2.0;//得是2.0
        }else{
            return minHeap.top();
        }
    }
private:
    priority_queue<int, vector<int>, greater<int>> minHeap;
    priority_queue<int, vector<int>, less<int>> maxHeap;
};

```



## 把数组排成最小数

```
class Solution {
public:
    string minNumber(vector<int>& nums) {
        vector<string> arr;
        for(int num : nums){
            arr.push_back(to_string(num));//string里面的数值转换
        }
        quickSort(arr, 0, arr.size() - 1);
        string str;
        for(string s : arr){
            str += s;
        }
        return str;
    }
    void quickSort(vector<string>& arr, int l, int r){
        if(l >= r) return;
        int i = l, j = r;
        while(i < j){
            while((i < j) && (arr[j] + arr[l] >= arr[l] + arr[j])) j--;
            while((i < j) && (arr[i] + arr[l] <= arr[l] + arr[i])) i++;
            swap(arr[i], arr[j]); 
        }
        swap(arr[l], arr[i]);
        quickSort(arr, l, i - 1);
        quickSort(arr, i + 1, r);
    }
};
```

## 扑克牌中的顺子

```
class Solution {
public:
    bool isStraight(vector<int>& nums) {
        int max = 0, min = 14;
        set<int> repeat;
        for(int i : nums){
            if(i == 0){
                continue;
            }
            if(i > max){//不能用if else if else if
                max = i;
            }
            if(i < min){
                min = i;
            }
            auto isFound = repeat.find(i);
            if(isFound != repeat.end()){
                return false;
            }
            repeat.insert(i);
        }
        if(max - min < 5){
            return true;
        }else{
            return false;
        }
    }
};
```



## 数组中重复的数字

```
class Solution {
public:
    int findRepeatNumber(vector<int>& nums) {
        set<int> repeat;
        for(int num : nums){
            if(repeat.find(num) != repeat.end()){
                return num;
            }
            repeat.insert(num);
        }
        return -1;//一定要返回-1，即要有覆盖全部可能的返回
    }
};
```

## 二维数组的查找

```
class Solution {
public:
    bool findNumberIn2DArray(vector<vector<int>>& matrix, int target) {
        if(matrix.size() == 0){//matrix[0].size()会出错，当matrix为空时
            return false;
        }
        int row = matrix.size(), colum = matrix[0].size();
        for(int i = row - 1, j = 0; i >= 0 && j < colum;){
            if(matrix[i][j] == target) return true;
            if(matrix[i][j] > target){//注意比较大小的逻辑
                i--;
            }else{
                j++;
            }
            
        }
        return false;
    }
};
```

## 旋转数组的最小数字

```
class Solution {
public:
    int minArray(vector<int>& numbers) {
        int l = 0, r = numbers.size() - 1, min;
        while(l < r){
            min = (l + r)/2;
            if(numbers[min] > numbers[r]){
                l = min + 1;
            }else if(numbers[min] < numbers[r]){
                r = min;
            }else{
                r = r - 1;//不是r = min - 1;
            }
        }
        return numbers[l];
    }
};
```



## 合并两个链表

```
class Solution {
public:
    ListNode* mergeTwoLists(ListNode* l1, ListNode* l2) {
        ListNode* cur = new ListNode();//要新建一个节点使链表连接起来，cur指向最后一个节点
        ListNode* head = cur;
        while(l1 != nullptr && l2 != nullptr){
            if(l1->val <= l2->val){
                cur->next = l1;
                l1 = l1->next;//漏了
            }else{
                cur->next = l2;
                l2 = l2->next;//漏了
            }
            cur = cur->next;
        }
        l1 == nullptr ? cur->next = l2 : cur->next = l1;
        return head->next;
    }
};
```



## 剑指 Offer 20. 表示数值的字符串

请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100"、"5e2"、"-123"、"3.1416"、"-1E-16"、"0123"都表示数值，但"12e"、"1a3.14"、"1.2.3"、"+-5"及"12e+5.4"都不是。

方法一：确定有限状态自动机
预备知识

确定有限状态自动机（以下简称「自动机」）是一类计算模型。它包含一系列状态，这些状态中：

有一个特殊的状态，被称作「初始状态」。
还有一系列状态被称为「接受状态」，它们组成了一个特殊的集合。其中，一个状态可能既是「初始状态」，也是「接受状态」。
起初，这个自动机处于「初始状态」。随后，它顺序地读取字符串中的每一个字符，并根据当前状态和读入的字符，按照某个事先约定好的「转移规则」，从当前状态转移到下一个状态；当状态转移完成后，它就读取下一个字符。当字符串全部读取完毕后，如果自动机处于某个「接受状态」，则判定该字符串「被接受」；否则，判定该字符串「被拒绝」。

注意：如果输入的过程中某一步转移失败了，即不存在对应的「转移规则」，此时计算将提前中止。在这种情况下我们也判定该字符串「被拒绝」。

一个自动机，总能够回答某种形式的「对于给定的输入字符串 S，判断其是否满足条件 P」的问题。在本题中，条件 P 即为「构成合法的表示数值的字符串」。

自动机驱动的编程，可以被看做一种暴力枚举方法的延伸：它穷尽了在任何一种情况下，对应任何的输入，需要做的事情。

自动机在计算机科学领域有着广泛的应用。在算法领域，它与大名鼎鼎的字符串查找算法「KMP」算法有着密切的关联；在工程领域，它是实现「正则表达式」的基础。

问题描述

在 C++ 文档 中，描述了一个合法的数值字符串应当具有的格式。具体而言，它包含以下部分：

符号位，即 +++、−-− 两种符号
整数部分，即由若干字符 0−90-90−9 组成的字符串
小数点
小数部分，其构成与整数部分相同
指数部分，其中包含开头的字符 e\text{e}e（大写小写均可）、可选的符号位，和整数部分
相比于 C++ 文档而言，本题还有一点额外的不同，即允许字符串首末两端有一些额外的空格。

在上面描述的五个部分中，每个部分都不是必需的，但也受一些额外规则的制约，如：

如果符号位存在，其后面必须跟着数字或小数点。
小数点的前后两侧，至少有一侧是数字。
思路与算法

根据上面的描述，现在可以定义自动机的「状态集合」了。那么怎么挖掘出所有可能的状态呢？一个常用的技巧是，用「当前处理到字符串的哪个部分」当作状态的表述。根据这一技巧，不难挖掘出所有状态：

起始的空格
符号位
整数部分
左侧有整数的小数点
左侧无整数的小数点（根据前面的第二条额外规则，需要对左侧有无整数的两种小数点做区分）
小数部分
字符 e\text{e}e
指数部分的符号位
指数部分的整数部分
末尾的空格
下一步是找出「初始状态」和「接受状态」的集合。根据题意，「初始状态」应当为状态 1，而「接受状态」的集合则为状态 3、状态 4、状态 6、状态 9 以及状态 10。换言之，字符串的末尾要么是空格，要么是数字，要么是小数点，但前提是小数点的前面有数字。

最后，需要定义「转移规则」。结合数值字符串应当具备的格式，将自动机转移的过程以图解的方式表示出来：

![](..\images\202104\0.png)

比较上图与「预备知识」一节中对自动机的描述，可以看出有一点不同：

我们没有单独地考虑每种字符，而是划分为若干类。由于全部 101010 个数字字符彼此之间都等价，因此只需定义一种统一的「数字」类型即可。对于正负号也是同理。
在实际代码中，我们需要处理转移失败的情况。例如当位于状态 1（起始空格）时，没有对应字符 e\text{e}e 的状态。为了处理这种情况，我们可以创建一个特殊的拒绝状态。如果当前状态下没有对应读入字符的「转移规则」，我们就转移到这个特殊的拒绝状态。一旦自动机转移到这个特殊状态，我们就可以立即判定该字符串不「被接受」。


链接：https://leetcode-cn.com/problems/biao-shi-shu-zhi-de-zi-fu-chuan-lcof/solution/biao-shi-shu-zhi-de-zi-fu-chuan-by-leetcode-soluti/




```
class Solution {
public:
    enum State {
        STATE_INITIAL,
        STATE_INT_SIGN,
        STATE_INTEGER,
        STATE_POINT,
        STATE_POINT_WITHOUT_INT,
        STATE_FRACTION,
        STATE_EXP,
        STATE_EXP_SIGN,
        STATE_EXP_NUMBER,
        STATE_END,
    };

    enum CharType {
        CHAR_NUMBER,
        CHAR_EXP,
        CHAR_POINT,
        CHAR_SIGN,
        CHAR_SPACE,
        CHAR_ILLEGAL,
    };

    CharType toCharType(char ch) {
        if (ch >= '0' && ch <= '9') {
            return CHAR_NUMBER;
        } else if (ch == 'e' || ch == 'E') {
            return CHAR_EXP;
        } else if (ch == '.') {
            return CHAR_POINT;
        } else if (ch == '+' || ch == '-') {
            return CHAR_SIGN;
        } else if (ch == ' ') {
            return CHAR_SPACE;
        } else {
            return CHAR_ILLEGAL;
        }
    }

    bool isNumber(string s) {
        unordered_map<State, unordered_map<CharType, State>> transfer{
            {
                STATE_INITIAL, {
                    {CHAR_SPACE, STATE_INITIAL},
                    {CHAR_NUMBER, STATE_INTEGER},
                    {CHAR_POINT, STATE_POINT_WITHOUT_INT},
                    {CHAR_SIGN, STATE_INT_SIGN},
                }
            }, {
                STATE_INT_SIGN, {
                    {CHAR_NUMBER, STATE_INTEGER},
                    {CHAR_POINT, STATE_POINT_WITHOUT_INT},
                }
            }, {
                STATE_INTEGER, {
                    {CHAR_NUMBER, STATE_INTEGER},
                    {CHAR_EXP, STATE_EXP},
                    {CHAR_POINT, STATE_POINT},
                    {CHAR_SPACE, STATE_END},
                }
            }, {
                STATE_POINT, {
                    {CHAR_NUMBER, STATE_FRACTION},
                    {CHAR_EXP, STATE_EXP},
                    {CHAR_SPACE, STATE_END},
                }
            }, {
                STATE_POINT_WITHOUT_INT, {
                    {CHAR_NUMBER, STATE_FRACTION},
                }
            }, {
                STATE_FRACTION,
                {
                    {CHAR_NUMBER, STATE_FRACTION},
                    {CHAR_EXP, STATE_EXP},
                    {CHAR_SPACE, STATE_END},
                }
            }, {
                STATE_EXP,
                {
                    {CHAR_NUMBER, STATE_EXP_NUMBER},
                    {CHAR_SIGN, STATE_EXP_SIGN},
                }
            }, {
                STATE_EXP_SIGN, {
                    {CHAR_NUMBER, STATE_EXP_NUMBER},
                }
            }, {
                STATE_EXP_NUMBER, {
                    {CHAR_NUMBER, STATE_EXP_NUMBER},
                    {CHAR_SPACE, STATE_END},
                }
            }, {
                STATE_END, {
                    {CHAR_SPACE, STATE_END},
                }
            }
        };

        int len = s.length();
        State st = STATE_INITIAL;

        for (int i = 0; i < len; i++) {
            CharType typ = toCharType(s[i]);
            if (transfer[st].find(typ) == transfer[st].end()) {
                return false;
            } else {
                st = transfer[st][typ];
            }
        }
        return st == STATE_INTEGER || st == STATE_POINT || st == STATE_FRACTION || st == STATE_EXP_NUMBER || st == STATE_END;
    }
};
```

