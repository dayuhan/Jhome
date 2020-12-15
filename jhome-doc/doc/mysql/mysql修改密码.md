### mysql 8.0后修改密码
    use mysql
    ALERT USER 'root'@'localhost' identified with mysql_native_password by '新密码'
    flush privileges; #刷新权限表
    来访用户：
    use mysql
    ALERT USER 'root'@'%' identified with mysql_native_password by '新密码'
    flush privileges; #刷新权限表
### mysql 8.0前修改密码
    数据库 修改密码
    ------------------------------------------------------------------------------------------
    update mysql.user set authentication_string=password('000000') where user='root' and Host='localhost';
    flush privileges;
    
    alter user 'root'@'localhost' identified by '000000';