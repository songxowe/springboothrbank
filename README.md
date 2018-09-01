# springboothrbank
华融湘江银行

基于 Spring boot 的前后端分离的 restful 项目

1.后端技术栈: spring boot 2.0.4 + mybatis + mysql

2.前端技术栈: h5 + Hui + jQuery


00.微服务.Maven.Spring Boot
01.安装配置 MySQL,建库,建表,初始化数据
   安装设置 IntelliJ IDEA

02.IDEA 中 Spring Initializr工具,快速的构建出一个基础的Spring Boot/Cloud工程

   从 start.spring.io 下载 Maven Project 模板
   MyEclipse 导入 Maven Project



Spring Boot的基础结构共三个文件:
src/main/java  程序开发以及主程序入口
src/main/resources 配置文件
src/test/java  测试程序

03.pom.xml 中添加支持web的模块(依赖)
  <!-- 添加支持web的模块-->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

04.controller
  YML文件格式是YAML (YAML Aint Markup Language)编写的文件格式，YAML是一种直观的能够被电脑识别的的数据数据序列化格式，并且容易被人类阅读，容易和脚本语言交互的，可以被支持YAML库的不同的编程语言程序导入，比如： C/C++, Ruby, Python, Java, Perl, C#, PHP等。

  采用yaml作为配置文件的格式。xml显得冗长，properties没有层级结构，yaml刚好弥补了这两者的缺点。
  这也是Spring Boot默认就支持yaml格式的原因

  application.yml 替换 application.properties
  # : 后必需有一个空格
  server:
    port: 8086

05.启动 Application
06.Chrome: http://127.0.0.1:8080/hello

07.设置 Tomcat 端口 (默认 8080)
08.www.getpostman.com 官网下载,安装 Postman - 测试
09.前后端分离开发,与前端联调

跨域是指 不同域名之间相互访问。跨域，指的是浏览器不能执行其他网站的脚本。它是由浏览器的同源策略造成的，是浏览器对JavaScript施加的安全限制

也就是如果在A网站中，我们希望使用Ajax来获得B网站中的特定内容
如果A网站与B网站不在同一个域中，那么就出现了跨域访问问题。

什么是同一个域？
同一协议，同一ip，同一端口，三同中有一不同就产生了跨域。


postman - controller - service
1.创建 Car 实体类
2.CarService.list() - 模拟数据
3.CarController.list()
4.postman


-- 账户表
CREATE TABLE account
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    account_id varchar(100), -- 账号
    password varchar(100),   -- 密码
    remaining decimal        -- 账户余额
);

-- 交易表
CREATE TABLE trade
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    account_id bigint,      -- 账户 ID - 外键
    trade_type varchar(10), -- 交易类型: 存款 取款 转账
    money decimal,          -- 交易金额
    trade_time datetime,    -- 交易日期
    remark varchar(500),    -- 交易摘要
    FOREIGN KEY (account_id) REFERENCES account (id)
);



Spring Boot Project: bank
技术栈: Spring Boot + Mybatis + MySQL

init project:
1.Create New Project - Empty Project - New Modules
2.配置 Database: MySQL
3.修改文件名或后缀名
  application.yml
  Application.java
  ApplicationTests.java
4.修改 pom.xml 新增依赖包
    <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-starter</artifactId>
      <version>1.3.2</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
     <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.12</version>
    </dependency>

    
5.修改 application.yml 设置云端服务器端口 + datasource + mybatis
  url: jdbc:mysql://127.0.0.1:3306/employee?useSSL=false&serverTimezone=Asia/Shanghai
   username: root
   password: root
    
  mybatis:
  type-aliases-package: com.springboot.bank.domain
  mapper-locations: classpath:mybatis/mapper/*.xml
  
6.修改 Application.java 新增 @MapperScan

7.实体类
8.映射器接口
  映射器文件

9.service
10.junit

11.controller
12.postman




-- 权限管理系统
-- redis   - 缓存机制
-- mongodb - 日志

-- spring boot + jpa


-- --------------------------------------------------
-- Spring boot 2.0.4 + MySQL 5.7.18
-- 华融湘江银行: Bank Manager System
-- Create by SONG 2018-08-27 09:00
-- --------------------------------------------------

-- Open CMD: mysql -uroot -p

-- 查询已有数据库
-- show databases;
-- 创建数据库
-- create database if not exists BANk;



-- 系统表 ---------------------------------------------
-- 菜单信息表
create table SYS_MENUS
(
  ID        BIGINT AUTO_INCREMENT NOT NULL, -- 菜单序号,主键
  PARENT_ID BIGINT,                         -- 父级序号
  SEQ       BIGINT,                         -- 菜单排序
  NAME      VARCHAR(50),                    -- 菜单名称
  DESCN     VARCHAR(400),                   -- 菜单说明
  LINK_URL  VARCHAR(200),                   -- 链接地址
  STATUS    VARCHAR(20),                    -- 是否可用
  CONSTRAINT PK_SYS_MENUS PRIMARY KEY (ID),
  CONSTRAINT FK_SYS_MENUS_1 FOREIGN KEY (PARENT_ID) REFERENCES SYS_MENUS (ID)
);



-- 角色信息表
create table SYS_ROLES
(
  ID    BIGINT AUTO_INCREMENT NOT NULL, -- 角色序号,主键
  NAME  VARCHAR(50),                    -- 角色名称
  CODE  VARCHAR(50),                    -- 角色编号 ROLE_XXX
  DESCN VARCHAR(400),                   -- 角色说明
  CONSTRAINT PK_SYS_ROLES PRIMARY KEY (ID)
);



-- 菜单角色对应表
create table SYS_MENU_ROLE
(
  MENU_ID BIGINT, -- 菜单编号
  ROLE_ID BIGINT, -- 角色编号
  constraint FK_SYS_MENUS_2 foreign key (MENU_ID) references SYS_MENUS (ID),
  constraint FK_SYS_ROLES_1 foreign key (ROLE_ID) references SYS_ROLES (ID)
);



-- 系统用户信息表
create table SYS_USERS
(
  ID         BIGINT AUTO_INCREMENT NOT NULL, -- 系统用户序号,主键
  USERNAME   VARCHAR(50) not null,           -- 用户名
  PASSWORD   VARCHAR(100) not null,           -- 用户密码
  email      varchar(100),                   -- 用户电子邮箱
  STATUS     VARCHAR(20),                    -- 用户状态 (1 启用 0 禁用)
  last_password_reset_date datetime,         -- 用户最后修改密码日期
  PHOTO_PATH VARCHAR(200),                   -- 用户照片路径
  constraint PK_SYS_USERS primary key (ID)
);



-- 系统用户角色对应表
create table SYS_USER_ROLE
(
  USER_ID BIGINT, -- 用户编号
  ROLE_ID BIGINT, -- 角色编号
  constraint FK_SYS_ROLES_2 foreign key (ROLE_ID) references SYS_ROLES (ID),
  constraint FK_SYS_USERS foreign key (USER_ID) references SYS_USERS (ID)
);



-- 初始化数据 ----------------------------------------------
-- 菜单 ---------------------------------------------------
-- 管理员
insert into SYS_MENUS (id, parent_id, seq, name, descn, link_url, status)
values (1, null, 100, '系统管理', '系统管理', null, '1');
insert into SYS_MENUS (id, parent_id, seq, name, descn, link_url, status)
values (2, 1, 103, '菜单管理', '菜单管理', 'menulist.html', '1');
insert into SYS_MENUS (id, parent_id, seq, name, descn, link_url, status)
values (3, 1, 102, '角色管理', '角色管理', 'rolelist.html', '1');
insert into SYS_MENUS (id, parent_id, seq, name, descn, link_url, status)
values (4, 1, 101, '用户管理', '用户管理', 'userlist.html', '1');

-- 银行管理
insert into SYS_MENUS (id, parent_id, seq, name, descn, link_url, status)
values (5, null, 200, '银行管理', '银行管理', null, '1');

-- 银行管理 二级菜单
insert into SYS_MENUS (id, parent_id, seq, name, descn, link_url, status)
values (6, 5, 201, '储户登录', '储户登录', 'accountlogin.html', '1');
insert into SYS_MENUS (id, parent_id, seq, name, descn, link_url, status)
values (7, 5, 202, '存款', '存款', 'accountoper.html', '1');
insert into SYS_MENUS (id, parent_id, seq, name, descn, link_url, status)
values (8, 5, 203, '取款', '取款', 'accountoper.html', '1');
insert into SYS_MENUS (id, parent_id, seq, name, descn, link_url, status)
values (9, 5, 204, '转账', '转账', 'accounttrans.html', '1');
insert into SYS_MENUS (id, parent_id, seq, name, descn, link_url, status)
values (10, 5, 205, '查询交易明细', '查询交易明细', 'tradelist.html', '1');
commit;



-- 角色 -------------------------------------------------
insert into SYS_ROLES (id, name, code, descn)
values (1, '系统管理员', 'ROLE_ADMIN', '系统管理员');
insert into SYS_ROLES (id, name, code, descn)
values (2, '行长', 'ROLE_PRESIDENT', '行长');
insert into SYS_ROLES (id, name, code, descn)
values (3, '经理', 'ROLE_MANAGER', '经理');
insert into SYS_ROLES (id, name, code, descn)
values (4, '股长', 'ROLE_DIRECTOR', '股长');
insert into SYS_ROLES (id, name, code, descn)
values (5, '柜员', 'ROLE_CLERK', '柜员');
commit;



-- 菜单角色 ----------------------------------------------
-- 管理员
insert into SYS_MENU_ROLE (menu_id, role_id)
values (1, 1);
insert into SYS_MENU_ROLE (menu_id, role_id)
values (2, 1);
insert into SYS_MENU_ROLE (menu_id, role_id)
values (3, 1);
insert into SYS_MENU_ROLE (menu_id, role_id)
values (4, 1);

commit;



-- 系统用户 --------------------------------------------
insert into SYS_USERS (id, USERNAME, password, email, status, last_password_reset_date, photo_path)
values (1, 'admin', '$2a$10$EWi.gHQ24odlS4pRY3e9p.HLd8Y.gm3JPSUfG/gTgHFsSP.eIzVp2','songxowe@gmail.com', '1', now(),'82117791.jpg');
commit;



-- 用户角色 -----------------------------------------
insert into SYS_USER_ROLE (user_id, role_id)
values (1, 1);
commit;



问题:
未登录可以访问 http://127.0.0.1:8080/ticket/welcome.html

在编写Web应用时，经常需要对页面做一些安全控制，比如：对于没有访问权限的用户需要转到登录表单页面。要实现访问控制的方法多种多样，可以通过Aop、拦截器实现，也可以通过框架实现（如：Apache Shiro、Spring Security）

Spring Security 是一个专门针对基于Spring的项目的安全框架,主要是利用了 AOP(Spring基础配置)来实现的。



JWT和Spring Security保护REST API

通常情况下，把API直接暴露出去是风险很大的，不说别的，直接被机器攻击就喝一壶的。那么一般来说，对API要划分出一定的权限级别，然后做一个用户的鉴权，依据鉴权结果给予用户开放对应的API。目前，比较主流的方案有几种:

用户名和密码鉴权，使用Session保存用户鉴权结果。
使用OAuth进行鉴权（其实OAuth也是一种基于Token的鉴权，只是没有规定Token的生成方式）
自行采用Token进行鉴权
第一种就不介绍了，由于依赖Session来维护状态，也不太适合移动时代，新的项目就不要采用了。第二种OAuth的方案和JWT都是基于Token的，但OAuth其实对于不做开放平台的公司有些过于复杂。我们主要介绍第三种：JWT。

什么是JWT？
JWT是 Json Web Token 的缩写。它是基于 RFC 7519 标准定义的一种可以安全传输的 小巧 和 自包含 的JSON对象。由于数据是使用数字签名的，所以是可信任的和安全的。JWT可以使用HMAC算法对secret进行加密或者使用RSA的公钥私钥对来进行签名。

JWT的工作流程
下面是一个JWT的工作流程图。模拟一下实际的流程是这样的（假设受保护的API在/protected中）

1.用户导航到登录页，输入用户名、密码，进行登录
2.服务器验证登录鉴权，如果用户合法，根据用户的信息和服务器的规则生成JWT Token
3.服务器将该token以json形式返回（不一定要json形式，这里说的是一种常见的做法）
4.用户得到token，存在localStorage、cookie或其它数据存储形式中。
5.以后用户请求/protected中的API时，在请求的header中加入 Authorization: Bearer xxxx(token)。此处注意token之前有一个7字符长度的 Bearer
6.服务器端对此token进行检验，如果合法就解析其中内容，根据其拥有的权限和自己的业务逻辑给出对应的响应结果。
7.用户取得结果

Spring Security是一个基于Spring的通用安全框架

如何利用Spring Security和JWT一起来完成API保护

简单的背景知识
如果你的系统有用户的概念的话，一般来说，你应该有一个用户表，最简单的用户表，应该有三列：Id，Username和Password，类似下表这种

ID	USERNAME	PASSWORD
10	wang	abcdefg
而且不是所有用户都是一种角色，比如网站管理员、供应商、财务等等，这些角色和网站的直接用户需要的权限可能是不一样的。那么我们就需要一个角色表：

ID	ROLE
10	USER
20	ADMIN
当然我们还需要一个可以将用户和角色关联起来建立映射关系的表。

USER_ID	ROLE_ID
10	10
20	20



1.pom.xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt -->
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt</artifactId>
  <version>0.9.0</version>
</dependency>

<!-- https://mvnrepository.com/artifact/com.google.code.findbugs/findbugs -->
<dependency>
  <groupId>com.google.code.findbugs</groupId>
  <artifactId>findbugs</artifactId>
  <version>3.0.1</version>
</dependency>




将Redis作为二级缓存
1.pom.xml中增加redis的依赖
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
  </dependency>

  <!-- redis依赖commons-pool
  https://mvnrepository.com/artifact/org.apache.commons/commons-pool2 -->
  <dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
    <version>2.6.0</version>
  </dependency>

2.application.properties配置文件中配置Redis基础信息
  spring:
    redis:
      host: 127.0.0.1
      port: 6379
      lettuce:
        pool:
          max-idle: 8
          min-idle: 0
          max-active: 8

3.Application.java 启动类上添加启用缓存的注解@EnableCaching

4.service的方法上 @Cacheable 注解使用缓存
  设置查询使用缓存:
  @Cacheable(value = "trade.find")
  设置新增时刷新指定缓存
  @CachePut(cacheNames = "trade.find")

5.测试 JUnit



Lettuce 和 Jedis 的都是连接Redis Server的客户端程序。
Jedis在实现上是直连redis server，多线程环境下非线程安全，除非使用连接池，为每个Jedis实例增加物理连接。
Lettuce基于Netty的连接实例（StatefulRedisConnection），可以在多个线程间并发访问，且线程安全，满足多线程环境下的并发访问，同时它是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例。



Mybatis的二级缓存原理:Mybatis的二级缓存可以自动地对数据库的查询做缓存，并且可以在更新数据时同时自动地更新缓存。

--------------------------------------------

背景
在分布式系统中，有多个web app，这些web app可能分别部署在不同的物理服务器上，并且有各自的日志输出。当生产问题来临时，很多时候都需要去各个日志文件中查找可能的异常，相当耗费人力。日志存储多以文本文件形式存在，当有需求需要对日志进行分析挖掘时，这个处理起来也是诸多不便，而且效率低下。

为了方便对这些日志进行统一管理和分析，可以将日志统一输出到指定的数据库系统中，再由日志分析系统去管理。由于这里是mongodb的篇章，所以主观上以mongodb来做日志数据存储；客观上，一是因为它轻便、简单，与log4j整合方便，对系统的侵入性低。二是因为它与大型的关系型数据库相比有很多优势，比如查询快速、bson存储结构利于扩展、免费等。


NoSQL & MongoDB



NoSQL:Not Only SQL (不只是SQL)

数据存储方案:
应用程序存储和检索数据有以下三种方案
文件系统直接存储
关系型数据库
NoSQL 数据库（是对非关系型数据库的统称）

最重要的差别是 NoSQL 不使用 SQL 作为查询语言。
数据存储可以不需要固定的表格模式（行和列），避免使用SQL的JOIN操作，有更高的性能及水平可扩展性的特征。
NoSQL 在 ACID（原子性、一致性、隔离性、持久性） 的支持方面没有传统关系型数据完整。

文档数据库   MongoDB / CouchDB
键／值数据库 redis   / Cassandra
列数据库     Hbase   / Cassandra
图数据库     Neo4J



MongoDB 基于文档存储模型，数据对象以BSON（二进制 JSON）格式被存储在集合的文档中，而不是关系数据库的行和列中。

集合
使用集合将数据编组，是一组用途相同的文档，类似表的概念，但集合不受模式的限制，在其中的文档格式可以不同。

文档
文档表示单个实体数据，类似一条记录（行）；与行的差别：行的数据是扁平的，每一列只有一个值，而文档中可以包含子文档，提供的数据模型与应用程序的会更加一致。


一个文档 Demo:
{
  name:'X Fimaly'
  address: ['NY','LA']
  person: [{'name':'Jack'},{'name':'Rose'}]
}



安装 MongoDB
官网:https://www.mongodb.com/

下载社区版:mongodb-win32-x86_64-3.4.9-signed.msi

设置环境变量:
把安装目录 mongodb/bin 添加到系统 path 中
...;D:\Program Files\MongoDB\Server\3.4\bin

cmd:
  mongo --help
  mongo --version

  tips:出错 缺少 api-ms-win-crt-runtime-xxx.dll 则安装 vc_redist.x64.exe

创建一个存放数据的目录如：D:/Oracle/MongoDB/data
从命令行执行 mongod --dbpath D:/Oracle/MongoDB/data 启动服务器 [不能关闭]
从命令行执行 mongo 启动交互窗口（mongoDB shell）



MongoDB 使用:
数据库:
启动 mongo shell  [相当于 mongo 客户端]

显示数据库
>show dbs

切换数据库（若不存在则创建数据库）
  >use employee [相当于 mongo 的一个数据库]

显示当前使用的数据库
>db

删除当前数据库
  db.dropDatabase()



Collection(集合):
显示所有集合
>show collections

创建一个集合
db.createCollection('emps') [相当于一张表 emps]

删除一个集合
  db.emps.drop()



MongoDB CRUD:
插入一个文档
db.collection.insertOne()
db.emps.insertOne({name:'SMITH',age:27})

插入多个文档
db.collection.insertMany()
db.emps.insertMany([{name:'SCOTT',age:26},{name:'KING',age:24,phone:['155','186']}])

查询（检索文档）
db.emps.find()

name 是 KING
db.emps.find({name:'KING'})

age 大于 25
db.emps.find({age:{$gt:25}})

age 小于 25 且 name 是 KING
db.emps.find({age:{$lt:25},name:'KING'})

电话号码为 186
db.emps.find({phone:'186'})



更新一个文档
db.collection.updateOne()
更新多个文档
db.collection.updateMany()

db.emps.updateOne(
	{name:'SCOTT'},	// 更新的条件
	{$set:{age:19}}	// 新的数据
)

// update 时新增字段
db.emps.updateOne(
	{name:'SMITH'},
  {$set:{phoneabc:'186'}}
)



删除一个文档
db.collection.deleteOne()
删除多个文档
db.collection.deleteMany()

db.emps.deleteOne({name:'SCOTT'})
db.emps.deleteMany({age:{$lt:30}})


--------------------------------------------------------
准备:
1.start mysql [数据库服务器]
2.start redis [redis服务器 - 二级缓存]
  - 切换至 bin 目录,cmd 执行: redis-server.exe redis.windows.conf

  start MongoDB [日志服务器]
  - cmd 执行: mongod --dbpath D:/Oracle/MongoDB/data
3.start idea  [云端/后端服务器]
  - run Application
4.start live-server [前端服务器]
5.Chrome




使用logback实现http请求日志导入mongodb

spring boot自带logback作为其日志新系统，但是在实际工作中，常常需要对日志进行管理或分析，
如果只是单纯的将日志导入文本文件，则在查询时操作过于繁琐，
如果将其导入mysql等关系型数据库进行存储，又太影响系统性能，同时由于Mysql其结构化的信息存储结构，导致在存储时不够灵活。
因此，在此考虑将springboot系统中产出的日志(logback) 存入mongodb中

1.pom.xml 引入依赖
  https://mvnrepository.com 搜索最新的 jar 包
  <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-mongodb -->
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-mongodb</artifactId>
      <version>2.0.4.RELEASE</version>
  </dependency>

  <!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-core -->
  <dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-core</artifactId>
    <version>1.2.3</version>
  </dependency>

  <!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-classic -->
  <dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.3</version>
  </dependency>

  <!-- AOP 依赖 -->
  <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-aop -->
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-aop</artifactId>
      <version>2.0.4.RELEASE</version>
  </dependency>

 <dependency>
   <groupId>log4j</groupId>
   <artifactId>log4j</artifactId>
   <version>1.2.17</version>
 </dependency>

2.添加实体类: logback.MyLog.java
3.添加数据访问接口: LogRepository.java
4.Appender 类: MongoDBAppender.java

5.切面中使用mongodb logger:
  logger取名为MONGODB
  通过getBasicDBObject函数从HttpServletRequest和JoinPoint对象中获取请求信息，并组装成BasicDBObject
  getHeadersInfo函数从HttpServletRequest中获取header信息
  通过logger.info()，输出BasicDBObject对象的信息到mongodb

6.resources/logback.xml - 更新 <appender name="MONGODB" />
            application.yml 配置spring boot的文件配置标签

            spring:
              data:
                mongodb:
                  uri: mongodb://127.0.0.1:27017/logs

7.controller

8.start Application
  Chrome: http://127.0.0.1:8080/mongo | greeting

9.cmd - mongo 进入客户端
  >use logs
  >db.myLog.find()
  >db.myLog.find({_class:"com.newer.springboot.logback.MyLog"})
