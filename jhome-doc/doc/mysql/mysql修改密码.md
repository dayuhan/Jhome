### mysql 8.0后修改密码
mysql 8.0加密方式不一样导致很多客户端不能连接：

    use mysql
    查询表中的相关信息
     select user,host,plugin from user where user='root';
     
    本机用户：
    ALTER USER 'root'@'localhost' identified with mysql_native_password by '新密码'
    flush privileges; #刷新权限表
    
    来访用户：
    use mysql
    ALTER USER 'root'@'%' identified with mysql_native_password by '新密码'
    flush privileges; #刷新权限表

https://blog.csdn.net/yubin1285570923/article/details/83352491

### mysql 8.0前修改密码
    数据库 修改密码
    ------------------------------------------------------------------------------------------
   
    
    alter user 'root'@'localhost' identified by '000000';