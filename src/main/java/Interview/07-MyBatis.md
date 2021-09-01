---
title: "07-MyBatis"
sidebar: 'auto'
categories: 
 - "interview"
---

# 1. MyBatis 是什么？

MyBatis 是一个半ORM（对象关系映射）框架，其内部封装了 JDBC，开发是只需要关注 SQL 语句本身，不需要花费精力去处理加载驱动、创建连接、创建 statement 等复杂的过程。

通过直接编写原生态 SQL，可以严格控制 SQL 语句的执行性能，支持动态 SQL，灵活度高。

MyBatis 使用 XML 或注解来配置和映射原生信息，将实体类映射成数据库中的记录，避免了 JDBC 代码手动设置参数以及获取结果集的繁琐步骤。

MyBatis 通过 XML 或注解将要执行的各种 statement 进行配置，通过 Java 对象和 statement 中的动态参数进行映射并最终形成执行的 SQL 语句。最后由 MyBatis 框架执行 SQL 语句，并将结果映射为 Java 对象返回。

## MyBatis 的优缺点

优点：
* 基于 SQL 语句编程，十分灵活；SQL 语句写在 XML 文件中，解除 SQL 与业务代码的耦合，便于统一管理；提供 XML 标签，支持编写动态 SQL ，可重用
* 与 JDBC 相比，消除了大量冗余的代码，不需要手动开关连接
* 可以很好的与各种数据库兼容
* 可以很好地与 Spring 集成

缺点：
* SQL 编写工作量大，对开发人员的 SQL 功底有一定要求
* SQL 语句依赖数据库，故数据库移植性差，不能随意更换

## MyBatis 和 Hibernate 的对比

针对简单逻辑，Hibernate 和 MyBatis 都有相应的代码生成工具，可以生成简单基本的 DAO 层方法。

针对高级查询，Mybatis 需要手动编写 SQL 语句，以及 ResultMap；而 Hibernate 有良好的映射机制，开发者无需关心 SQL 的生成与结果映射，可以更专注于业务流程。Hibernate 也可以自己写 SQL 来指定需要查询的字段，但这样就破坏了 Hibernate 开发的简洁性。

举个形象的比喻：
* Mybatis：机械工具，使用方便，拿来就用，但工作还是要自己来作，不过工具是活的，怎么使由我决定。
* Hibernate：智能机器人，但研发它（学习、熟练度）的成本很高，工作都可以摆脱他了，但仅限于它能做的事。

# 2. MyBatis 的核心组件有哪些？

MyBatis 核心组件包括 SqlSessionFactoryBuilder / SqlSessionFactory / SqlSession / Mapper。

## SqlSessionFactoryBuilder

SqlSessionFactoryBuilder 是一个构建器，用于构建 SqlSessionFactory。其生命周期一般只存在于方法的局部，用完即可收回。

构建语句：
`SqlSessionFactory factory = SqlSessionFactoryBuilder.build(inputStream);`

## SqlSessionFactory

SqlSessionFactory 用于创建 SqlSession，即创建一个对话。

每次程序访问数据库，都需要使用 SqlSession，所以 SqlSessionFactory 应该存在于 MyBatis 应用的整个生命周期中。

为了减少创建会话带来的资源消耗，一般使用单例模式创建 SqlSession。

创建语句：
`SqlSession sqlSession = SqlSessionFactory.openSession();`

## SqlSession

SqlSession 就是一个对话，既可以发送 SQL 语句去执行返回结果，也可以代理 Mapper 接口。

SqlSession 是一个线程不安全的对象，其生命周期应该在请求数据库处理事务的过程中。

每次创建的 SqlSession 对象应该及时关闭，否则会使得数据库连接池的活动资源变少，影响系统性能。

## Mapper

Mapper 用于 MyBatis 代理 DAO，通过注解和 XML 文件可以获取对应的 SQL 和映射规则。

使用语句：
`XXMapper xxMapper = sqlSession.getMapper(XXMapper.class);`

# 3. MyBatis 的动态 SQL 有了解么？

MyBatis 可以在 xml 映射文件中以标签的形式实现动态 SQL，其原理是根据表达式的值完成逻辑判断并动态拼接 SQL 语句。

## 动态 SQL 标签

* if：单条件分支的判断语句
* choose, when, otherwise：多条件的分支判断语句
* foreach：列举条件，遍历集合，实现循环语句
* trim,where,set：是一些辅助元素，可以对拼接的SQL进行处理
* bind：进行模糊匹配查询的时候使用，提高数据库的可移植性

# 4. Mybatis 的 Mapper 中常用标签有哪些？

* | select | insert | update | delete |
* | resultMap | resultType |
* | where | if | foreach | sql |

# 5. MyBatis 的 DAO 接口的工作原理有了解么？

DAO 接口即 Mapper 接口。接口的全限名就是映射文件中 namespace 的值；接口的方法名，就是映射文件中的 id 值；接口方法内的参数，就是传递给 SQL 的参数。

Mapper 接口没有实现类，调用接口方法的时候，使用接口全限名 + 方法名拼接字符串作为 key 值，可唯一定位一个 MapperStatement。在 MyBatis 中，每一个`<select>、<insert>、<update>、<delete>`标签，都会被解析为一个 MapperStatement对象。

Mapper 接口的工作原理是 JDK 动态代理，MyBatis 运行时会使用 JDK 动态代理为 Mapper 接口生成代理对象 proxy，代理对象会拦截接口方法，转而执行 MapperStatement 所代表的 SQL，然后将 SQL 执行结果返回。

## DAO 接口中的方法可以重载么？

不可以。因为 xml 文件中使用的是全限名 + 方法名的保存和寻找策略。

## 不同映射文件 xml 中的 id 可以重复么？

如果配置了 namesapce，那么 id 可以重复； 如果没有配置，则不可以重复。

# 6. MyBatis 中 # 和 $ 的区别

能用 # 就尽量不用 $

* \# 是预编译处理，是占位符；$ 是字符串替换，是拼接符
* Mybatis 在处理 # 时，会将 sql 中的 \# 替换为 ？，并调用 PreparedStatement 来赋值；在处理 $ 是，就是把 $ 替换成变量的值，调用 Statement 来赋值
* \# 变量替换替换后，对应的变量会自定添加单引号；$ 变量替换后，对应变量不会添加单引号
* 使用 \# 可以有效防止 SQL 注入，提高系统安全性

# 7. MyBatis 的缓存机制有了解么？

分为一级缓存和二级缓存。

* 一级缓存（同一个SqlSession）
基于 HashMap 的本地缓存，其存储作用域为 Session，当 Session flush 或 close 之后，该 Session 中的所有缓存就将清空，默认打开一级缓存。

* 二级缓存（同一个SqlSessionFactory）
二级缓存与一级缓存其机制相同，默认也是采用 HashMap 的本地存储，不同在于其存储作用域为 Mapper(Namespace)，并且可自定义存储源。

# 8. MyBatis 的接口绑定是什么？有哪些方式？

接口绑定即 MyBatis 代理 DAO 接口，将接口里的方法和 xml 映射文件中 SQL 语句绑定。我们在使用的时候直接调用接口方法即可。

* 注解绑定：`@Select、@Update`
* xml 绑定：设置 namespace 为全路径名

# 9. MyBatis 插件的原理及应用

## 什么是插件？

插件是Mybatis中的最重要的功能之一，能够对特定组件的特定方法进行增强。

MyBatis 允许我们在映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：

* 「Executor」：update, query, flushStatements, commit, rollback, getTransaction, close, isClosed

* 「ParameterHandler」: getParameterObject, setParameters

* 「ResultSetHandler」：handleResultSets, handleOutputParameters

* 「StatementHandler」: prepare, parameterize, batch, update, query

## 如何自定义插件？

插件的实现其实很简单，只需要实现 Mybatis 提供的 Interceptor 接口即可，源码如下：

```java
public interface Interceptor {
  //拦截的方法
  Object intercept(Invocation invocation) throws Throwable;
  //返回拦截器的代理对象
  Object plugin(Object target);
  //设置一些属性
  void setProperties(Properties properties);
}
```

自定义插件使用的注解：

* `@Intercepts`：标注在实现类上，表示这个类是一个插件的实现类。
* `@Signature`：作为`@Intercepts`的属性，表示需要增强 Mybatis 的某些组件中的某些方法（可以指定多个）。常用的属性如下：
	* `Class<?> type()`：指定哪个组件（Executor、ParameterHandler、ResultSetHandler、StatementHandler）
	* `String method()`：指定增强组件中的哪个方法，直接写方法名称。
	* `Class<?>[] args()`：方法中的参数，必须一一对应，可以写多个；这个属性非常重用，区分重载方法。

完成 Interceptor 接口的实现类后，创建一个配置类，在其中注册该接口实现类的 Bean 并注入 IOC 容器即可。















