# 1. 谈谈你对 ORM 框架的理解，常见的 ORM 框架有哪些？
ORM (Object Relational Mapping) 即对象关系映射，其主要实现对象到关系型数据库数据的映射。

![MyBatis01.png](./picture/MyBatis01.png)

因为原生的 JDBC 方式效率低，且需要写一大堆模板代码。ORM 是对 JDBC 的封装，我们可以直接使用，不需要重复的造轮子。

目前比较常见的 ORM 框架有 MyBatis、Hibernate、JPA 等...


## 1.1 常见的 MyBatis 框架有哪些优势？
* 入门简单，熟悉 SQL 语句的话即学即用
* 支持注解，面向接口开发
* 使用灵活，对于复杂 SQL，动态 SQL 的编写更加灵活
* 高度封装，使开发人员可以专注于业务逻辑，提高开发效率

# 2 比较一下 MyBatis 和 Hibernate
MyBatis 和 Hibernate 都支持 JDBC 和 JTA 事务，两者都可以通过 SessionFactoryBuilder 由 XML 配置文件生成 SessionFactory，然后由SessionFactory 生成 Session，由 Session 来开启和执行事务和 SQL 操作。

MyBatis 优势：
* MyBatis 可以进行更为细致的 SQL 优化，减少查询字段
* MyBatis 更易于掌握

Hibernate 优势：
* Hibernate 对 DAO 层的开发比 MyBatis 简单，MyBatis 需要维护 SQL 和结果集映射
* Hibernate 对于对象的维护和缓存优于 MyBatis，对增删改查的对象的维护更方便
* Hibernate 数据库移植性好，MyBatis 移植性差，不同的数据库需要写不同的 SQL
* Hibernate 有更好的二级缓存机制，可以使用第三方缓存，MyBatis 本身提供的缓存机制不佳

## 2.1 追问：MyBatis 和 Hibernate 的缓存机制有哪些区别？
* 相同点：
  * Hibernate和Mybatis的二级缓存除了采用系统默认的缓存机制外，都可以通过实现你自己的缓存或为其他第三方缓存方案，创建适配器来完全覆盖缓存行为。
* 不同点：
  * Hibernate的二级缓存配置在SessionFactory生成的配置文件中进行详细配置，然后再在具体的表-对象映射中配置是那种缓存。
  * MyBatis的二级缓存配置都是在每个具体的表-对象映射中进行详细配置，这样针对不同的表可以自定义不同的缓存机制。并且Mybatis可以在命名空间中共享相同的缓存配置和实例，通过Cache-ref来实现。

# 3. MyBatis 中的 # 和 $ 有什么区别？
|  | # | $ |
| --- | --- | --- |
| 字符拼接 | # 将传入的数据当作一个字符串，会将传入的数据添加一个双引号，再拼接到 SQL 语句中 | $ 将传入的数据直接拼接在 SQL 中，试想采用这种方式传入`;drop table user`会如何？ |
| SQL 注入 | # 能很大程度的防止 SQL 注入 | $ 方式无法防止 SQL 注入 |
| 使用 | # 一般用于拼接查询参数 | $ 一般用于拼接数据库对象，如表名等 |

综上所述，我们在编写 MyBatis 映射语句时，能用 # 就尽量不用 $；如果不得已一定要使用 $，则需要做好过滤，防止 SQL 注入攻击。

## 3.1 什么是 SQL 注入？
SQL 注入是一种代码注入技术，用于攻击数据驱动的应用，恶意的 SQL 语句被插入到执行的实体字段中，导致数据库数据泄露，更严重的会导致数据库的结构丢失。

所以我们在应用中需要采取一些手段来防备 SQL 注入攻击，如在一些安全性要求很高的应用中(如银行软件)，常采用将 SQL 语句替换为存储过程的方式防止 SQL 注入。

## 3.2 Mybatis 是如何防止 SQL 注入的？
MyBatis 启用了预编译功能，我们编写好的 SQL 中的 #{...} 字段将在预编译中使用占位符 ？ 代替；而在执行时，直接使用我们传入的参数替代占位符即可。
SQL 注入只能对编译过程起作用，所以采用预编译的方式能很好地避免 SQL 注入的问题。






