---
title: "MyBatis 进阶"
sidebar: 'auto'
tags:
 - "MyBatis"
categories: 
 - "全家桶.SSM"
---
# 1. 主要类的介绍

## 1.1 Resource：读取主配置文件

```java
InputStream in = Resources.getResourceAsStream("mybatis.xml");
```

## 1.2 SqlSessionFactoryBuilder：创建SqlSessionFactory对象

```java
SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
SqlSessionFactory factory = builder.build(in);
```

## 1.3 SqlSessionFactory：获取SqlSession对象

这是一个重量级对象（程序创建一个对象的耗时较长，使用资源较多）

```java
SqlSession sqlSession = factory.openSession();
```

方法说明：

* openSession()：无参数的，获取的是非自动提交事务sqlSession对象
* openSession(boolean)
	* boolean为true：获取自动提交事务的SqlSession
    * boolean为false：获取非自动提交事务的SqlSession
## 1.4 SqlSession：定义了操作数据的方法

SqlSession接口的实现类：DefaultSqlSession

方法：

* selectOne()
* selectList()
* insert()
* update()
* delete()
* commit()
* rollback()
使用要求：

SqlSession对象不是线程安全的，需要在方法内部使用，在执行sql语句之前，使用openSession()获取SqlSession对象。在执行完sql语句之后，需要使用SqlSession.close()方法关闭它。这样才能保证它的使用是线程安全的。

# 2. MybatisUtils工具类

```java
package com.mybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtils {

    private static SqlSessionFactory factory = null;
    static {
        String config = "mybatis.xml"; // 需要和项目中的文件名一致
        try {
            InputStream in = Resources.getResourceAsStream(config);
            // 创建SqlSessionFactory对象，使用SqlSessionFactoryBuilder方法
            factory = new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取SqlSession的方法
    public static SqlSession getSqlSession() {
        SqlSession sqlSession = null;
        if (factory != null) {
            sqlSession = factory.openSession(); // 非自动提交事务
        }
        return sqlSession;
    }
}
```

使用：

```java
@Test
    public void test03(){
        // 使用工具类获取SqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        // 指定需要执行的sql语句的标识。
        // sql映射文件中的namespace + “.” + 标签的id值
        String sqlID = "com.mybatis.dao.StudentDao.findAll";
        // 执行sql语句
        List<Student> studentList = sqlSession.selectList(sqlID);
        // 输出结果
        for (Student stu: studentList) {
            System.out.println(stu);
        }
        // 关闭sqlSession对象
        sqlSession.close();
    }
```

# 3. Mybatis框架Dao代理

## 3.1 动态代理

不再需要Dao接口实现类，使用getMapper获取代理对象

**调用SqlSession的getMapper()方法即可获取指定接口的实现类对象。该方法的参数为指定Dao接口类的class值**

```java
package com.mybatis02.dao;

import com.mybatis02.entity.Student;

import java.util.List;

public interface StudentDao {

    List<Student> findAll();

    int insert(Student student);
}
```

```java
package com.mybatis02;

import com.mybatis02.dao.StudentDao;
import com.mybatis02.entity.Student;
import com.mybatis02.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class Test {
    @org.junit.Test
    public void testFindAll() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        List<Student> students = studentDao.findAll();
        for (Student student:students) {
            System.out.println(student);
        }
        sqlSession.close();
    }

    @org.junit.Test
    public void testInsert() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        Student student = new Student(2, "刘备", 28);
        int result = studentDao.insert(student);
        sqlSession.commit();
        sqlSession.close();
        System.out.println(result);
    }
}
```

# 4. 参数的传递

从java代码中把数据传入到mapper文件的sql语句中

## 4.1 parameterType

写在mapper文件中的一个属性，表示Dao接口方法中传递的参数的类型。（一般不使用这种方法）

```java
public interface StudentDao {
    Student findById(int id);
}
```

```xml
    <select id="findById" parameterType="java.lang.Integer" resultType="com.mybatis02.entity.Student">
        select id,name,age from t1 where id = #{id}
    </select>
```

## 4.2 单个简单类型参数的传递

java的基本数据类型和String在Mybatis中都称为简单类型。

在mapper文件中，获取一个简单类型的参数的值，使用 **#{任意字符}**

```java
public interface StudentDao {
    Student findById(int id);
}
```

```xml
    <select id="findById" resultType="com.mybatis02.entity.Student">
        select id,name,age from t1 where id = #{id}
    </select>
```

```java
    @org.junit.Test
    public void testFindById() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        Student student = studentDao.findById(6);
        System.out.println(student);
    }
```

## 4.3 传递多个参数：使用@Param

接口中的方法需要传递多个参数时，使用 **@Param** 进行传递

```java
public interface StudentDao {
    List<Student> findMulti(@Param("myname") String name, @Param("myid") int id);
}
```

```xml
    <select id="findMulti" resultType="com.mybatis02.entity.Student">
        select id,name,age from t1 where name=#{myname} or id=#{myid}
    </select>
```

```java
    @org.junit.Test
    public void testFindMulti() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        List<Student> list = studentDao.findMulti("凡凡", 6);
        for (Student stu: list) {
            System.out.println(stu);
        }
    }
```

## 4.4 传递多个参数：使用java对象

接口中的方法需要传递多个参数时，可以传进一个对象，通过对象的属性进行多个参数的传递。

使用 **#{属性名}**

```java
public interface StudentDao {
    List<Student> findMulti1(Student student);
}
```

```xml
    <select id="findMulti1" resultType="com.mybatis02.entity.Student">
        select id,name,age from t1 where id=#{id} or name=#{name}
    </select>
```

```java
    @org.junit.Test
    public void testFindMulti1() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        Student student = new Student(6, "曹操", 0);
        List<Student> list = studentDao.findMulti1(student);
        for (Student stu: list) {
            System.out.println(stu);
        }
    }
```

## 4.5 传递多个参数：按位置

参数位置从0开始计数，使用语法 **#{arg位置}** 进行传参

注意：mybatis-3.3及之前的版本使用`#{0}, #{1}`的方式；mybatis-3.4之后使用`#{arg0}, #{arg1}`的方式

```java
public interface StudentDao {
    List<Student> findMulti2(int id, String name);
}
```

```xml
    <select id="findMulti2" resultType="com.mybatis02.entity.Student">
        select id,name,age from t1 where id=#{arg0} or name=#{arg1}
    </select>
```

```java
    @org.junit.Test
    public void testFindMulti2() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        List<Student> list = studentDao.findMulti2(6, "刘备");
        for (Student stu: list) {
            System.out.println(stu);
        }
    }
```

## 4.6 传递多个参数：使用Map

Map集合可以存储多个值，使用Map向mapper文件一次传入多个参数。Map集合使用String的key，Object类型的值存储参数。mapper文件使用 **#{key}** 引用参数值。

```java
public interface StudentDao {
    List<Student> findMulti3(Map<String, Object> map);
}
```

```xml
    <select id="findMulti3" resultType="com.mybatis02.entity.Student">
        select id,name,age from t1 where name=#{myname} or age=#{myage}
    </select>
```

```java
    @org.junit.Test
    public void testFindMulti3() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        Map<String, Object> map = new HashMap<>();
        map.put("myname", "曹操");
        map.put("myage", 28);
        List<Student> list = studentDao.findMulti3(map);
        for (Student stu: list) {
            System.out.println(stu);
        }
    }
```

## 4.7 # 与 $ 的差异

* `select id,name,age from t1 where id=#{studentID};`
使用#的结果：`select id,name,age from t1 where id=?;`
* `select id,name,age from t1 where id=${studentID};`
使用$的结果：`select id,name,age from t1 where id=6;`

**区别**：

* #使用?在sql语句中做占位，使用PrepareStatement对象执行sql，效率高
* #能够避免sql注入问题，更加安全
* $不使用占位符，是单纯的字符串连接方式，使用Statement对象执行sql，效率低
* $有sql注入的风险，缺乏安全性
* $可以替换表名或者列名

# 5. mybatis返回查询结果

## 5.1 resultType

resultType结果类型，指sql语句执行完毕后，数据转换为java对象，java类型任意

resultType的值：

1. 类型的全限定名称(推荐使用)
2. 类型的别名(不推荐使用)

### mybatis的处理方式

* mybatis执行sql语句，然后调用类的无参构造方法，创建对象
* mybatis将ResultSet指定列值赋值给类中同名的属性

### 定义自定义类型的别名

* 在mybatis只配置文件中定义，使用`<typeAlias>`或`<package>`进行定义    
	* `<typeAlias>`：单独定义每一个类的别名
    * `<package>`：统一对一个包内所有的类设置别名，别名为类名
* 在resultType中使用别名

## 5.2 resultMap

一般用于解决类属性名与查询的列名不同的情况

```xml
    <!--创建resultMap
        id：自定义的唯一名称，在select中使用
        type：期望转换为java对象的全限定名称或者别名
    -->
    <resultMap id="StudentMap" type="com.mybatis02.entity.MyStudent">
        <!--主键字段使用id-->
        <id column="id" property="stuId"/>
        <!--非主键字段使用result-->
        <result column="name" property="stuName"/>
        <result column="age" property="stuAge"/>
    </resultMap>
    
    <select id="findUseResultMap" resultMap="StudentMap">
        select id,name,age from t1 where name=#{name}
    </select>
```

另一种解决类属性名与查询的列名不同的方法：对查询的列名起别名

```xml
    <select id="findUseResultMap" resultMap="StudentMap">
        select id as stuId, name as stuName, age as stuAge from t1 where name=#{name}
    </select>
```

# 6. mybatis模糊查询

* 方式一
```java
public interface StudentDao {
    List<Student> findLike1(String str);
}
```

```xml
    <select id="findLike1" resultType="com.mybatis02.entity.Student">
        select id,name,age from t1 where name like #{studentname}
    </select>
```

```java
    @org.junit.Test
    public void testFindLike1() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        String keyword = "%楠%";
        List<Student> list = studentDao.findLike1(keyword);
        for (Student stu: list) {
            System.out.println(stu);
        }
    }
```

* 方式二
```java
public interface StudentDao {
    List<Student> findLike2(String str);
}
```

```xml
    <select id="findLike2" resultType="com.mybatis02.entity.Student">
        select id,name,age from t1 where name like "%" #{studentname} "%"
    </select>
```

```java
    @org.junit.Test
    public void testFindLike2() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        String keyword = "楠";
        List<Student> list = studentDao.findLike2(keyword);
        for (Student stu: list) {
            System.out.println(stu);
        }
    }
```

# 7. mybatis动态sql

## 7.1 where和if的搭配使用

```java
public interface StudentDao {
    List<Student> findByWhere(Student student);
}
```

```xml
    <select id="findByWhere" resultType="com.mybatis02.entity.Student">
        select id,name,age from t1
        <where>
            <if test="name != null and name != ''">
                and name=#{name}
            </if>
            <if test="age > 0">
                and age>5
            </if>
        </where>
    </select>
```

```java
    @org.junit.Test
    public void testFindByWhere() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        Student student = new Student(0, null, 5);
        List<Student> list = studentDao.findByWhere(student);
        for (Student stu: list) {
            System.out.println(stu);
        }
    }
```

## 7.2 foreach

* 遍历简单类型的list
```java
public interface StudentDao {
    List<Student> findByForeach1(List<Integer> list);
}
```

```xml
    <select id="findByForeach1" resultType="com.mybatis02.entity.Student">
        select id,name,age from t1
        <if test="list !=null and list.size()>0">
            where id in
            <foreach collection="list" open="(" close=")" item="id" separator=",">
                #{id}
            </foreach>
        </if>
    </select>
```

```java
    @org.junit.Test
    public void testFindByForeach1() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(6);
        list.add(7);
        List<Student> stuList = studentDao.findByForeach1(list);
        for (Student stu: stuList) {
            System.out.println(stu);
        }
    }
```

* 遍历自定义类型的list
```java
public interface StudentDao {
    List<Student> findByForeach2(List<Student> list);
}
```

```xml
    <select id="findByForeach2" resultType="com.mybatis02.entity.Student">
        select id,name,age from t1
        <if test="list !=null and list.size()>0">
            where id in
            <foreach collection="list" open="(" close=")" item="stu" separator=",">
                #{stu.id}
            </foreach>
        </if>
    </select>
```

```java
    @org.junit.Test
    public void testFindByForeach2() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, null, 0));
        list.add(new Student(2, null, 0));
        list.add(new Student(12, null, 0));
        List<Student> stuList = studentDao.findByForeach2(list);
        for (Student stu: stuList) {
            System.out.println(stu);
        }
    }
```

## 7.3 动态sql之代码段

使用`<sql><sql/>`标签可以定义代码片段，以便其他sql标签复用

```java
public interface StudentDao {
    List<Student> findMulti4(@Param("id1") int id1, @Param("id2") int id2);
}
```

```xml
    <sql id="studentSql">
        select id,name,age from t1
    </sql>
    <select id="findMulti4" resultType="com.mybatis02.entity.Student">
        <include refid="studentSql"/>
        where id=#{id1} or id=#{id2}
    </select>
```

```java
    @org.junit.Test
    public void testFindMulti4() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        List<Student> stuList = studentDao.findMulti4(1, 2);
        for (Student stu: stuList) {
            System.out.println(stu);
        }
    }
```

# 8. 数据库的属性配置文件

为了方便对数据库连接进行管理，DB连接四要素的数据一般放在一个专门的属性文件中。Mybatis主配置文件需要从这个属性文件中读取这些数据。

步骤：

* 在resources目录创建jdbc.properties文件
* 修改主配置文件，使用`<properties>`标签引入配置文件
* 使用key指定值： **${key}**
```java
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/test3
jdbc.username=root
jdbc.password=961231zqf
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <properties resource="jdbc.properties"/>
```

```xml
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
```

# 9. 扩展：分页插件

使用步骤：

* 引入依赖
* 在mybatis主配置文件中加入plugin配置（在`<environments>`标签之前）
* 在调用查询方法前使用PageHelper对象的静态方法：`PageHelper.startPage(int page, int pagesize)`
	* page：表明显示第几页的内容
    * pagesize：表明每一页显示几条数据
```xml
    <!-- https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper -->
    <dependency>
      <groupId>com.github.pagehelper</groupId>
      <artifactId>pagehelper</artifactId>
      <version>5.1.10</version>
    </dependency>
```

```xml
    <plugins>
        <plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
    </plugins>
```

```java
    @org.junit.Test
    public void testFindAllByPage() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        PageHelper.startPage(2, 3);
        List<Student> students = studentDao.findAll();
        for (Student student:students) {
            System.out.println(student);
        }
        sqlSession.close();
    }
```
