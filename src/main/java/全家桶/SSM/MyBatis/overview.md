---
title: "MyBatis 概述"
sidebar: 'auto'
tags:
 - "MyBatis"
categories: 
 - "全家桶.SSM"
---

# 1. 框架概述

* 界面层：与用户打交道，接收用户的请求参数，显示处理结果。（jsp, html, servlet）
* 业务逻辑层：接收界面层传递的数据，计算逻辑，调用数据库，获取数据。
* 数据访问层：访问数据库，执行对数据的查询、修改、删除等。

三层框架对应的包：

* 界面层：controller包（servlet）
* 业务逻辑层：service包（XXXService类）、
* 数据访问层：dao包（XXXDao类）

三层中类的交互：

* 用户使用界面-->业务逻辑层-->数据访问层(持久层)-->数据库mysql

三层对应的框架：

* 界面层-->servlet-->springMVC
* 业务逻辑层-->service类-->spring
* 数据访问层-->dao类-->mybatis

