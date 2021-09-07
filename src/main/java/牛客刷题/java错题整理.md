---
title: "牛客错题整理"
sidebar: 'auto'
categories:
- "interview"
---

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
![java13.png](./picture/java13.png)

解析：子类重写父类方法时，方法的访问权限不能小于原访问权限。在接口中，方法的默认权限是 public，所以子类重写之后只能是 public。

---
![java14.png](./picture/java14.png)

解析：java 程序初始化过程：
1. 父类的静态代码块
2. 子类的静态代码块
3. 父类的普通代码块
4. 父类的构造方法
5. 子类的普通代码块
6. 子类的构造方法

---
![java15.png](./picture/java15.png)

解析：
1. 以 stream 结尾的都是字节流；以 writer 或 reader 结尾的都是字符流；
2. 只是读写文件，和文件内容无关的，一般选用字节流；读写文件时需要按行对内容进行处理时，一般选用字符流；

---
![java16.png](./picture/java16.png)

解析：数据类型的转换分为强制类型转换和自动类型转换
* 强制类型转换：必须在代码中进行说明，转换顺序不受限制；
* 自动类型转换：不需要声明，一般按照从位数低向位数高的类型转换，顺序限制如下：`byte,short,char -> int -> long -> float -> double`；

---
![java17.png](./picture/java17.png)

解析：
* 非静态方法只能通过实例对象来调用，不能通过类名直接调用；
* 静态方法才能通过类名直接调用；

---
![java20.png](./picture/java20.png)

解析：
* A：抽象类可以有构造方法，只是不能够直接创建抽象类的实例对象；
* B：在接口中不能有构造方法；
* C：Java 类不允许多继承；
* D：jdk 1.8 之后接口中允许存在方法体，JDK 1.8 之前不可以；

---
![java21.png](./picture/java21.png)

解析：
* D：类中有 abstract 方法，则必须用 abstract 修饰类；但 abstract 类中可以没有抽象方法；

---
![java23.png](./picture/java23.png)

解析：
* == 比较的是地址，看是否是同一个对象；equals 比较的是值；
* `.toLowerCase()`方法底层为 new 一个新的字符串返回，存在于堆中，故地址不同，不是同一个对象，返回 false；

---
![java24.png](./picture/java24.png)

---
![java29.png](./picture/java29.png)

解析：java 构造方法可以由任意访问修饰符修饰，如 public, protected, private 或没有修饰，但是不能被非访问性质的修饰符修饰，如 abstract, static, final, native, synchronized。

---
![java30.png](./picture/java30.png)

解析：flush() 方法用于强制将缓冲区中的字符流或字节流输出，其目的是如果输出流输出到缓冲区后，缓冲区被有被填满，那么缓冲区将会一直等待被填满。所以在关闭输出流之后要调用 flush()。

---
![java31.png](./picture/java31.png)

---
![java32.png](./picture/java32.png)

解析：
* 接口中的方法默认为`public abstract`;
* 接口中的变量默认为`public static final`;

---


# Java 集合相关

---
![java03.png](./picture/java03.png)

---
![java18.png](./picture/java18.png)

解析：
* 如果在循环过程中调用集合的 remove() 方法，就会导致在循环过程中 list.size() 变化，导致错误；
* 如果需要在循环过程中删除集合中的某个元素，需要调用迭代器 Iterator 的 remove() 方法；

---
![java28.png](./picture/java28.png)

解析：集合框架中线程安全的有：喂(Vector)，S(Stack)H(HashTable)E(Enumeration);

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
![java22.png](./picture/java22.png)

---
![java25.png](./picture/java25.png)

解析：java 中，判断一块内存空间是否符合垃圾收集器的收集标准只有两个：
* 给对象赋值为 null，并且之后没在调用过；
* 给对象分配了新值，重新分配了内存空间；

---
![java26.png](./picture/java26.png)

解析：
* A：在虚拟机中提供了 3 中类加载器：引导（Bootstrap）类加载器、扩展（Extension）类加载器、系统（System）类加载器（也称应用类加载器）；
* B：类加载器不加载接口；
* D：装载一个不存在的类的时候，因为采用的双亲加载模式，所以强制加载会直接报错；
* F：自定义类加载器继承 ClassLoader 后重写了 findClass() 方法加载指定路径上的 class；

---


# 计算机基础

---
![java12.png](./picture/java12.png)

解析：位 < 字节 < 字 < 双字

---
![java27.png](./picture/java27.png)

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
![java19.png](./picture/java19.png)

解析：在本题中，本着一个原则，即：调用的方法都是实例化的子类中的重写方法，只有明确调用了 super.xxx 关键词或者是子类中没有该方法时，才会去调用父类相同的同名方法。
