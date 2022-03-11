---
title: "SpringMVC 问题"
sidebar: 'auto'
tags:
 - "SpringMVC"
categories: 
 - "全家桶.SSM"
---

# 1. maven仓库中存在jar包却找不到

解决方法：

* 注释掉maven的settings.xml中的本地仓库地址，在idea中配置
* 在idea的setting中配置maven的仓库地址，如下图

![d0f7e6440a29ea2d04d8d7d41569ed37.png](./image/d0f7e6440a29ea2d04d8d7d41569ed37.png)

# 2. pom.xml配置文件中注意添加jdk版本信息

```xml
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>14</maven.compiler.source>
        <maven.compiler.target>14</maven.compiler.target>
    </properties>
```

# 3. 在项目发布中添加lib依赖

![7b9976aacc27af6ca4c1163c59460bd9.png](./image/7b9976aacc27af6ca4c1163c59460bd9.png)

# 4. 配置tomcat

![742e47e480680f4805d0d311320cb0d2.png](./image/742e47e480680f4805d0d311320cb0d2.png)

![f77fc5ef061a6a92a82d0ed762a5205c.png](./image/f77fc5ef061a6a92a82d0ed762a5205c.png)

# 5. tomcat控制台中文乱码问题

`D:\Tomcat\apache-tomcat-9.0.39\conf`路径下的logging.properties文件

修改其中的

![04006e0e92356ee3a98d02ae1a56fbf2.png](./image/04006e0e92356ee3a98d02ae1a56fbf2.png)

