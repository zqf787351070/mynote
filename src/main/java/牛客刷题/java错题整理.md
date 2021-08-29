# Java 基础相关

---
![java01.png](./picture/java01.png)

解析：
* final：表示是最终类，不能被继承；
* abstract：表示抽象类，只能被继承；
* private：表示被修饰的属性和方法不能被子类继承，是完全私有的，只能被当前类中的成员访问；
* protect：表示被修饰的属性和方法是受保护的，只有当前类的成员与继承该类的类或者同一个包中的类才能访问；

---
![java05.png](./picture/java05.png)

解析：

| | public | protect | default | private |
| --- | --- | --- | --- | --- |
| 同类 | √ | √ | √ | √ |
| 同包 | √ | √ | √ |   |
| 子类 | √ | √ |   |   |
| 通用性 | √ |   |   |   |

---
![java09.png](./picture/java09.png)

解析： final 成员变量必须满足下列两种条件之一：
* 初始化时赋值
* 在构造函数中赋值

---
![java02.png](./picture/java02.png)

---

# Java 集合相关

---
![java03.png](./picture/java03.png)

---

# Java 多线程相关

---
![java07.png](./picture/java07.png)

![java07-1.png](./picture/java07-1.png)

---

# Java JVM

---
![java11.png](./picture/java11.png)

---

# Java 代码题

---
![java04.png](./picture/java04.png)

解析：没有 break，发生 case 穿透，程序会继续向下执行，直到遇到 break 或 switch 代码段结束

---
![java06.png](./picture/java06.png)

解析：
* str.length() 得到的是字符数，不是字节数；
* str.getBytes("GBK").length() 求的是字节数，若为 GBK，一个中文字符占两个字节；若是 UTF-8，一个中文字符占三个字节；

---
![java09.png](./picture/java08.png)

解析：
1. 其实foo(‘A’);就是初始化条件，只会执行一次，所以第一个打印的肯定是A
2. 因为i=0;循环条件是i<2，所有0<2满足条件，接着会输出B，然后执行i++；i就变成1了，再输出D，再最后输出C，一次循环后的结果是：ABDC
3. 第二次循环的开始是foo(‘A’);是初始条件所以不会执行，直接从foo(‘B’)开始，输出B，然后i为1，且小于2，此时循环体内再次执行i++；i的值为2了，再次输出D，最后输出C
第二次循环输出：BDC
4. 然后循环再次执行for(foo(‘A’);foo(‘B’)&&(i<2);foo(‘C’))，直接输出B，***i的值在第二轮循环后的值变成了2，2<2不成立，终止循环，输出B

---
![java10.png](./picture/java10.png)

解析：
1. 在一个 java 文件中，public 的类只能有一个
2. 子类无法访问父类中的私有变量(private)

---
