# 《Jhome 分布式技术框架》

## 《jhome分布式技术框架》是一个涵盖众多组件：账户认证/授权，消息推送，事务管理，自动化配置，快速部署，分布式文件管理，代码快速生成，服务治理的开发框架，旨在帮助开发者快速构建自己的应用，搭建方便，快速上手，快速开发；

## 技术概要：
*  1.Spring全家桶 （SpringBoot Spring SpringCloud（eureka+feign+config+zuul+Hystrix） ）
*  2.Shrio 安全框架
*  3.pack4j 安全框架
*  4.NIO（Netty）
*  5.PRC远程通信协议（ grpc  Thrift ） 
*  6.分布式两阶段补偿事务（transaction）
*  7.POI(文档操作)
*  8.redisson（操作Redis缓存数据库框架）
*  9.rabbitmq（消息队列）
*  10.mybatis + mybatis-Plush
*  11.Nginx+FastDFS分布式文件系统
## jhome 架构图 
![image](https://github.com/dayuhan/Jhome/blob/master/img-storage/1.jpg) 

## jhome v1.4  
### 升级概要：搭建文件上传服务（Jhome-fileStore）
### 引入技术： 
*  FastDFS 分布式文件管理
*  阿里云上传 
*  普通本地仓库管理 

## jhome v1.3
### 升级概要：搭建可靠消息服务（Jhome-TransactionService）
### 引入技术： 
*  RabbitMq 消息队列 实现分布式系统中事务回滚


## jhome v1.2  
### 升级概要：搭建代码生成器服务（jhome-codeGenerator），采velocity模板引擎，一键生成：controller dao mapper model/bo model/vo model/query service service/impl

### 引入技术： 
*  增加mybatis-Plush AutoGenerator 代码一键批量生成功能 详情请见 Jhome-codeGenerator 服务
*  引入Tcc 分布式事务框架 两阶段补偿提交（后续引入实战demo）  
*  引入爬虫框架（后续引入实战Demo）
![image](https://github.com/dayuhan/Jhome/blob/master/img-storage/pc.jpg) 


## jhome v1.1

### 升级概要：该版本重点搭建了消息中心（jhome-NettyService），配置中心（jhome-configService），公共组件服务（jhome-common）

*  消息服务中心 （详情请见 jhome-NettyService ）
   利用Netty搭建消息服务中心，实现分布式系统中WebSocket通信，各服务之间通过rpc协议（实现框架：Grpc和Thrift）间接操作消息中心,把消息服务独立出来，降低系统耦合，从而实现高性能的前后端通信；
*  配置中心,引入了 spring Cloud Config组件 （详情请见：jhome-configService）
   利用配置中心，可以简化、集中、对分布式系统中的服务统一配置，配置信息存储到Mysql中，可以达到一键初始化效果；简化部署； 
   
### 引入技术： 
*  引入Netty框架
*  引入grpc框架
*  引入Thrift框架
*  引入SpringCloud config组件
*  引入公共组件库包括（传统阻塞式 RabbitMq、PIO Exl批量导入引擎、JWT单点登录Tokens生成库、分布式缓存锁、HttpClient、Memcached、其他）
*  引入POI(文档操作)  
*  引入rabbitmq（消息队列） （目前是IO阻塞 后续升级到NIO模式） 详情见（jhome-common 公共组件服务）
  
  
  
## jhome v1.0

### 升级概要：该版本重点引入了Shrio框架 Pack4J 框架（CAS单点登录），针对在分布式系统中，各个服务之间用户授、认证做了集中处理:  搭建了注册中心（jhome-registrationService），统一管理各个服务

### 引入技术： 
*  1.Spring 全家桶 （SpringBoot Spring SpringCloud ）
*  2.构建注册中心 引入springCloud eureka组件 实现 多服务注册，统一管理各个服务 
*  3.引入springCloud Feig组件统一请求账户服务jhome-account 实现Session 复制 
*  4.引入Shrio 安全框架实现分布式系统集中授权和认证 （详情请见 jhome-account 服务）
*  5.引入pack4j + Shrio 多数据源认证 单点登陆认证
*  6.升级mybatis 到 mybatis-Plush 简化用户配置提高开发效率
*  7.redisson（redis锁 防止缓存击穿）
*  8.RestTemplate（操作redis数据库）
 
【Shiro + SpringBoot + SpringCloud+redis 实现分布式系统认证】 流程图
![image](https://github.com/dayuhan/Jhome/blob/master/img-storage/2.jpg)

【Shiro +pack4j 集成单点登录】 流程图
![image](https://github.com/dayuhan/Jhome/blob/master/img-storage/3.jpg)

### 重点解决分布式系统 跨域 认证问题：
*  登陆成功跳转问题 登陆成功后跳转到原有地址:  (已修复，后期根据当前Session 中存储的返回地址 跳转)
*  支持单点登陆（分部署集中认证和授权解决分布式系统中的用户登陆和授权的问题，但是在微服务系统中，无法实现一次登陆多次使用）;
    （已修复）
    a、用户在B段登陆 后使用JWP 对 SessionID jwt 生成Token :
    b、将带有的Token地址跳转到A 消费段:
    c、A段过滤器拦截 解析Token 调用Subject.getSession 方法 验证Token合法性，并在返回的Session中设置认证标识，
        session.setAttribute("AUTHENTICATED_SESSION_KEY",boolean.class)。
    d、本次返回 true。
    e、等待下次过滤连执行 重新创建subject 根据 AUTHENTICATED_SESSION_KEY 存在 返回isAuthenticated为true)。

*  解决频繁从账户系统活用Session信息，只有获取授权每次从账户系统中获取;
     （已修复 通过远程接口从缓存中读取数据后写入到请求头中， 框架不需要要每次通过远程接口请求获取Session，每次请求获取一次）

*  重定向 B系统在A系统登录成功，在去请求A系统登录页面，直接跳回到B系统;
   （已修复 待验证）
*  单点登陆成功后，在提供服务B端退出，消费A段无法退出
   （正在修复，消费A段退出，先调用A段的退出把Subject 认证状态isAuthenticated
    变成false,在调用服务B段退出设置B段的Subject的isAuthenticated，刷新当前页即可 服务B段退出，A段做做感知判断）
  
### 如有问题可以联系本人，本人微信号： daxu06661    如果您觉得对您有帮助，请为我点赞~谢谢！！！



