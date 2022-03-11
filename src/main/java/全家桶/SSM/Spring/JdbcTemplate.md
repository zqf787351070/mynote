---
title: "JdbcTemplate"
sidebar: 'auto'
tags:
 - "Spring"
categories: 
 - "全家桶.SSM"
---

# 1. 概念和准备工作

## 1.1 什么是JdbcTemplate？
Spring框架对JDBC进行封装，使用JdbcTemplate方便实现对数据库的操作

## 1.2 准备工作
* 引入jar包
* 在spring配置文件中配置数据库连接池
* 配置JdbcTemplate对象，注入DataSource
* 创建service类，创建dao类，在dao中注入jdbcTemplate对象

代码：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                           http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
    <!--开启组件扫描-->
    <context:component-scan base-package="classDemo.Jdbc"/>
    <!--配置数据库连接池-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close">
        <property name="url" value="jdbc:mysql://localhost:3306/test3?serverTimezone=GMT%2B8"/>
        <property name="username" value="root"/>
        <property name="password" value="961231zqf"/>
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    </bean>
    <!--配置JdbcTemplate对象，注入DataSource-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <!--注入dataSource-->
        <property name="dataSource" ref="dataSource"/>
    </bean>
</beans>
```
```java
@全家桶.Service //注解创建类
public class UserService {
    @Autowired // 按照类型注入
    private UserDao userDao;
}
```
```java
// 业务逻辑接口
public interface UserDao {
    void add(User userJdbc);
}

@Repository // 注解创建类
public class UserDaoImp implements UserDao {
    @Autowired // 按类型注入
    private JdbcTemplate jdbcTemplate;
}
```

## 1.3 JdbcTemplate操作数据库

### 增删改数据
* JdbcTemplate对象中的update方法实现：`update(String sql, Object... args)`
    * 第一个参数：sql语句
    * 第二个参数：可变参数，设置sql语句中的值

```java
// 业务逻辑接口
public interface UserDao {
    void add(User user);
    void update(User user);
    void delete(User user);
}
```
```java
@Repository // 注解创建类
public class UserDaoImp implements UserDao {
    @Autowired // 按类型注入
    private JdbcTemplate jdbcTemplate;
    // 重写业务逻辑
    @Override
    // 添加
    public void add(User user) {
        String sql = "insert into t1 values(?,?,?)";
        Object[] args = {user.getId(), user.getName(), user.getAge()};
        int res = jdbcTemplate.update(sql, args);
        System.out.println(res);
    }
    // 修改
    @Override
    public void update(User user) {
        String sql = "update t1 set name=?, age=? where id=?";
        Object[] args = {user.getName(), user.getAge(), user.getId()};
        int res = jdbcTemplate.update(sql, args);
        System.out.println(res);
    }
    // 删除
    @Override
    public void delete(User user) {
        String sql = "delete from t1 where id=?";
        int res = jdbcTemplate.update(sql, user.getId());
        System.out.println(res);
    }
}
```
```java
//  设置与存放数据库信息的类
public class User {
    private int id;
    private String name;
    private int age;

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
}
```
```java
@全家桶.Service //注解创建类
public class UserService {
    @Autowired // 按照类型注入
    private UserDao userDao;
    // 调度业务逻辑(起到一个传递的作用)
    public void add(User user) {
        userDao.add(user);
    }
    public void update(User user) {
        userDao.update(user);
    }
    public void delete(User user) {
        userDao.delete(user);
    }
}
```
```java
    @Test
    public void testUser_Jdbc_add() { // 测试添加数据
        ApplicationContext context = new ClassPathXmlApplicationContext("bean_JDBC.xml");
        UserService userService = context.getBean("userService", UserService.class);
        User user = new User();
        user.setId(7);
        user.setName("老二");
        user.setAge(5);
        userService.add(user);
    }

    @Test
    public void testUser_Jdbc_update() { // 测试修改数据
        ApplicationContext context = new ClassPathXmlApplicationContext("bean_JDBC.xml");
        UserService userService = context.getBean("userService", UserService.class);
        User user = new User();
        user.setId(7);
        user.setName("佩奇");
        user.setAge(8);
        userService.update(user);
    }

    @Test
    public void testUser_Jdbc_delete() { // 测试删除数据
        ApplicationContext context = new ClassPathXmlApplicationContext("bean_JDBC.xml");
        UserService userService = context.getBean("userService", UserService.class);
        User user = new User();
        user.setId(3);
        userService.delete(user);
    }
```

### 查询返回某个值
* JdbcTemplate对象中的queryForObject方法实现：`queryForObject(String sql, Class<T> requiredType)`
    * 第一个参数：sql语句
    * 第二个参数：返回值类型Class
    
```java
    // 查询表中的数据条数
    @Override
    public int selectCount() {
        String sql = "select count(*) from t1";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }
```
```java
@全家桶.Service //注解创建类
public class UserService {
    @Autowired // 按照类型注入
    private UserDao userDao;
    // 设置业务逻辑
    // 查询返回值：查询表中的数据条数
    public void selectCount() {
        int res = userDao.selectCount();
        System.out.println(res);
    }
}
```
```java
    @Test
    public void testUser_Jdbc_selectCount() { // 查询返回某个值：查询数据数
        ApplicationContext context = new ClassPathXmlApplicationContext("bean_JDBC.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.selectCount();
    }
```

### 查询返回对象
* JdbcTemplate对象中的queryForObject方法实现：`queryForObject(String sql, RowMapper<T> rowMapper, Object... args)`
    * 第一个参数：sql语句
    * 第二个参数：RowMapper是接口，针对返回不同数据类型，使用这个接口里的实现类完成数据封装
    * 第三个参数：sql语句中的值
    
    
```java
    // 查询返回对象
    @Override
    public User findStuInfo() {
        String sql = "select * from t1 where id=?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), 6);
        return user;
    }
```
```java
@全家桶.Service //注解创建类
public class UserService {
    @Autowired // 按照类型注入
    private UserDao userDao;
    // 设置业务逻辑
    // 查询返回对象
    public void findStuInfo() {
        User user = userDao.findStuInfo();
        System.out.println(user);
    }
}
```
```java
    @Test
    public void testUser_Jdbc_findStuInfo() { // 查询返回对象
        ApplicationContext context = new ClassPathXmlApplicationContext("bean_JDBC.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.findStuInfo();
    }
```


### 查询返回集合
* JdbcTemplate对象中的query方法实现：`query(String sql, RowMapper<T> rowMapper, Object... args)`
    * 第一个参数：sql语句
    * 第二个参数：RowMapper是接口，针对返回不同数据类型，使用这个接口里的实现类完成数据封装
    * 第三个参数：sql语句中的值
    
    
```java
    // 查询返回集合
    @Override
    public List<User> findAll() {
        String sql = "select * from t1";
        List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return userList;
    }
```
```java
@全家桶.Service //注解创建类
public class UserService {
    @Autowired // 按照类型注入
    private UserDao userDao;
    // 设置业务逻辑
    // 查询返回集合
    public void findAll() {
        List<User> userList = userDao.findAll();
        for (User stu: userList) {
            System.out.println(stu);
        }
    }
```
```java
    @Test
    public void testUser_Jdbc_findAll() { // 查询返回对象集合
        ApplicationContext context = new ClassPathXmlApplicationContext("bean_JDBC.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.findAll();
    }
```

### 批量操作：增删改
* JdbcTemplate对象中的batchUpdate方法实现：`batchUpdate(String sql, List\<Object\[]> batchArgs)`
    * 第一个参数：sql语句
    * 第二个参数：List集合，添加多条语句
    

```java
    // 批量添加数据
    @Override
    public void batchAdd(List<Object[]> batchArgs) {
        String sql = "insert into t1 values(?,?,?)";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }
    // 批量修改数据
    @Override
    public void batchUpdate(List<Object[]> batchArgs) {
        String sql = "update t1 set name=?, age=? where id=?";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }
    // 批量删除数据
    @Override
    public void batchDelete(List<Object[]> batchArgs) {
        String sql = "delete from t1 where id=?";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }
```
```java
@全家桶.Service //注解创建类
public class UserService {
    @Autowired // 按照类型注入
    private UserDao userDao;
    // 设置业务逻辑
    public void batchAdd(List<Object[]> batchArgs) {
        userDao.batchAdd(batchArgs);
    }
    public void batchUpdate(List<Object[]> batchArgs) {
        userDao.batchUpdate(batchArgs);
    }
    public void batchDelete(List<Object[]> batchArgs) {
        userDao.batchDelete(batchArgs);
    }
    }
```
```java
    @Test
    public void testUser_Jdbc_batchAdd() { // 批量添加数据
        ApplicationContext context = new ClassPathXmlApplicationContext("bean_JDBC.xml");
        UserService userService = context.getBean("userService", UserService.class);
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] o1 = {10, "大狗", 3};
        Object[] o2 = {11, "小猪", 2};
        Object[] o3 = {12, "粉猪", 1};
        batchArgs.add(o1);
        batchArgs.add(o2);
        batchArgs.add(o3);
        userService.batchAdd(batchArgs);
    }

    @Test
    public void testUser_Jdbc_batchUpdate() { // 批量添加数据
        ApplicationContext context = new ClassPathXmlApplicationContext("bean_JDBC.xml");
        UserService userService = context.getBean("userService", UserService.class);
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] o1 = {"曹操", 30, 1};
        Object[] o2 = {"刘备", 20, 2};
        Object[] o3 = {"孙权", 10, 8};
        batchArgs.add(o1);
        batchArgs.add(o2);
        batchArgs.add(o3);
        userService.batchUpdate(batchArgs);
    }

    @Test
    public void testUser_Jdbc_batchDelete() { // 批量删除数据
        ApplicationContext context = new ClassPathXmlApplicationContext("bean_JDBC.xml");
        UserService userService = context.getBean("userService", UserService.class);
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] o1 = {1};
        Object[] o2 = {2};
        Object[] o3 = {8};
        batchArgs.add(o1);
        batchArgs.add(o2);
        batchArgs.add(o3);
        userService.batchDelete(batchArgs);
    }
```






