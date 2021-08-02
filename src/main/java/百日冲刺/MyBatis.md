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







