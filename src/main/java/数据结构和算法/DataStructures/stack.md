---
title: "栈"
sidebar: 'auto'
tags:
 - "数据结构"
categories: 
 - "数据结构"
---

# 1. 栈的简介

* 英文为stack
* 栈是一个**先入后出**(FILO)的有序列表
* 栈是限制线性表中元素的插入和删除都只能在线性表的同一端进行的一种特殊线性表。允许插入和删除的一端，为**变化的一端，称为栈顶(Top)**，另一端为**固定的一端，称为栈底(Bottom)**
* 根据栈的定义可知，最先放入栈中的元素在栈底，最后放入的元素在栈顶；删除元素正好相反，最后放入的元素最先删除，最先放入的元素最后删除

# 2. 栈的应用场景

* 子程序的调用：在跳往子程序前，会先将下个指令的地址存到堆栈中，指导子程序执行完了之后再将地址取出，以回到原来的程序当中
* 处理递归调用：和子程序的调用类似，只是除了储存下一个指令的地址外，也将参数、区域变量等数据存入堆栈中
* 表达式的转换(中缀表达式转后缀表达式)与求值
* 二叉树的遍历
* 图形的深度优先搜索法(depth first)

# 3. 栈的快速入门(数组模拟栈)

* 使用数组来模拟栈
* 定义一个**top来表示栈顶，初始值为-1**
* **入栈操作：当有数据加入到栈时，top++; stack[top]=data;**
* **出栈操作：int value = stack[top]; top--;**
实现代码：

```java
package 栈;

public class ArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(3);
        stack.push(2);
        stack.show();
        stack.push(5);
        stack.show();
        stack.pop();
        stack.show();
        stack.push(8);
        stack.show();
        stack.push(10);
        stack.pop();
        stack.show();
    }
}

// 定义一个ArrayStack类表示栈
class ArrayStack {
    private int maxSize; // 栈的大小
    private int[] stack; // 数组模拟栈，数据存储在该数组中
    private int top = -1; // top表示栈顶，初始化为-1
    // 构造器
    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }
    // 判断是否栈满
    public boolean isFull() {
        return top == maxSize - 1;
    }
    // 判断是否栈空
    public boolean isEmpty() {
        return top == -1;
    }
    // 入栈
    public void push(int number) {
        if (isFull()) {
            System.out.println("栈满，无法添加数据！");
            return;
        }
        top ++;
        stack[top] = number;
    }
    // 出栈
    public int pop() {
        if (isEmpty()) {
            System.out.println("栈空，无法取出数据！");
        }
        int value = stack[top];
        top --;
        return value;
    }
    // 遍历栈，从栈顶开始显示数据
    public void show() {
        if (isEmpty()) {
            System.out.println("栈空！");
        }
        for (int i = top; i > -1; i--) {
            System.out.printf("stack[%d] = %d\n", i, stack[i]);
        }
        System.out.println("---------------");
    }
}

```

# 4. 栈实现综合计算器(中缀表达式)

使用栈完成一个表达式结果的计算：7*2*2-5+1-5+3+4=？

* 创建两个栈：    * 数栈numStack：用来存放数字
    * 符号栈operStack：用来存放运算符

* 通过一个index(索引)值，遍历表达式
* **若扫描到的字符为一个数字，则需要继续向后扫描一位**    * 如果后一位还是数字，则拼接后一位，然后继续向后扫描，直到后一位为符号为止，数字拼接入数栈
    * 如果后一位是符号，则直接入数栈

* **若扫描到的字符为一个符号**    * 若当前符号栈为空，则直接入符号栈
    * 若当前符号栈有操作符，则进行比较    * **若当前的操作符的优先级小于或者等于栈中的操作符**，就需要在数栈中pop出两个数字，再从符号栈中pop出一个符号，进行运算，将结果入数栈，然后将当前的字符入符号栈
        * **若当前的操作符的优先级大于栈中的操作符**，则直接入符号栈

* 当表达式扫描完毕后，就顺序的从数栈和符号栈中pop出相应的数和符号，并运行
* 最后在栈中只剩一个数字，就是表达式的结果

## 4.1 扩展功能：返回当前栈顶的值

```java
// 增加一个方法，可以返回当前栈顶的值，但是不是真正的pop
    public int peek() {
        return stack[top];
    }
```

## 4.2 扩展功能：获取运算符的优先级

```java
// 返回运算符的优先级，优先级又程序员规定，使用数字表示。数字越大，优先级越高。
    public int priority(int oper) {
        if (oper == '*' || oper == '/') {
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        } else {
            return -1; // 假设目前的表达式只有加减乘除
        }
    }
```

## 4.3 扩展功能：判断是否是运算符

```java
// 判断是否是运算符
    public boolean isOper(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }
```

## 4.4 扩展功能：计算方法

```java
// 计算方法
    public int cal(int num1, int num2, int oper) {
        int res = 0; // 用于存放计算的结果
        switch (oper) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1; // 注意顺序
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num1 / num2;
                break;    
        }
        return res;
    }
```

## 4.5 实现代码

```java
public class Calculator {
    public static void main(String[] args) {
        String expression = "7*2*2-5+1-5+3+4";
        // 创建两个栈：数栈和符号栈
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);
        // 定义所需的变量
        int index = 0; // 用于扫描字符串
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        char ch = ' '; // 将每次扫描得到的char保存到ch
        String keepNum = ""; // 用于拼接多位数
        // while循环扫描expression
        while (true) {
            // 依次得到expression的每一个字符
            ch = expression.substring(index, index + 1).charAt(0);
            // 判断ch是什么，然后做相应的处理
            if (operStack.isOper(ch)) { // 如果是运算符
                // 判断当前的符号栈是否为空
                if (!operStack.isEmpty()) { // 若符号栈非空，就用当前操作符与栈顶操作符进行比较
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        // 若当前操作符优先值低于栈顶操作符
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1, num2, oper);
                        // 将结果入数栈
                        numStack.push(res);
                        // 将当前符号入符号栈
                        operStack.push(ch);
                    } else {
                        // 若当前操作符优先值高栈顶操作符，则直接入符号栈
                        operStack.push(ch);
                    }
                } else { // 若符号栈为空则直接入符号栈
                    operStack.push(ch);
                }
            } else { // 如果是数字，直接入数栈
                // 多位数的处理
                keepNum += ch;
                // 如果ch已经是expression的最后一位，就直接入栈
                if (index == expression.length() - 1) {
                    numStack.push(Integer.parseInt(keepNum));
                } else {
                    // 判断下一位是不是数字，如果是数字，就继续扫描；如果是运算符，就入栈
                    if (operStack.isOper(expression.substring(index + 1, index + 2).charAt(0))) {
                        // 如果最后一位是运算符，则入栈
                        numStack.push(Integer.parseInt(keepNum));
                        // 清空keepNum
                        keepNum = "";
                    }
                }
            }
            // index向后移动，并判断是否扫描到最后
            index ++;
            if (index >= expression.length()) {
                break;
            }
        }
        // 表达式扫描结束后，顺序的从数栈和符号栈中pop处相应的数和符号，并运行
        while (true) {
            // 如果符号栈为空，则计算得到最后一个结果
            if (operStack.isEmpty()) {
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1, num2, oper);
            numStack.push(res);
        }
        // 将数栈中最后的数字pop出，即为结果
        int result = numStack.pop();
        System.out.printf("%s = %d", expression, result);
    }
}
```

# 5. 逆波兰(后缀表达式)计算器

需要完成一个逆波兰计算器，要求：

* 输入一个逆波兰表达式(后缀表达式)， 使用栈计算其结果
* 支持小括号和多位数整数
* 思路分析：示例(3+4)*5-6的后缀表达式为3 4 + 5 * 6 -    * 从左扫描到右，将3和4压入堆栈
    * 遇到+运算符，弹出4和3(栈顶元素和次顶元素)，计算出3+4的值7，将7入栈
    * 将5入栈
    * 接下来是*运算符，因此弹出5和7，计算5*7的值35，将35入栈
    * 最后是-运算符，计算35-6的值29，即最终结果
代码实现：

```java
package DataStructures.栈;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PolandNotation {
    public static void main(String[] args) {
        String suffixException1 = "3 4 + 5 * 6 -"; // 29
        String suffixException2 = "4 5 * 8 - 60 + 8 2 / +"; // 76
        List<String> list = getListString(suffixException2);
        System.out.println("rpnList=" + list);
        int res = calculate(list);
        System.out.println("计算结果为：" + res);
    }

    // 读取一个逆波兰表达式，依次将数据和运算符放入到ArrayList中
    public static List<String> getListString(String suffixExpression) {
        // 将suffixExpression分割
        String[] split = suffixExpression.split(" ");
        List<String> list = new ArrayList<String>();
        for (String ele: split) {
            list.add(ele);
        }
        return list;
    }

    // 完成对逆波兰表达式的运算
    public static int calculate(List<String> list) {
        // 创建堆栈，一个就够
        Stack<String> stack = new Stack<String>();
        // 遍历list
        for (String item: list) {
            // 此处使用正则表达式识别数字
            if (item.matches("\\d+")) { // 匹配多位数字
                stack.push(item);
            } else { // 如果是符号，pop出两个数字进行运算，再入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                } else if (item.equals("*")) {
                    res = num1 * num2;
                } else if (item.equals("/")) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("符号有误！");
                }
                stack.push(String.valueOf(res));
            }
        }
        return Integer.parseInt(stack.pop());
    }
}
```

# 6. 中缀表达式转换为后缀表达式
