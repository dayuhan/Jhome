docker 安装 postGreSql
这里我们拉取postgres 和 gis 组合的镜像,目前组合的版本有如下几个
11.0-2.5
10.0-2.4
9.6-2.4

    #docker pull postgres #最新版本
    docker pull kartoza/postgis:11.0-2.5
    docker run --name my_postgres -e TZ="Asia/Shanghai"  -v /root/Deploy/docker_postgres_data:/var/lib/postgresql/data -e POSTGRES_USER='postgres'   -e POSTGRES_PASSWORD=postgres1232 -e ALLOW_IP_RANGE=0.0.0.0/0 -p 5432:5432 -d 00409934f62a
    -p端口映射    
    -v将数据存到宿主服务器      
    -e TZ=PRC时区，中国  
    -e ALLOW_IP_RANGE=0.0.0.0/0，这个表示允许所有ip访问，如果不加，则非本机 ip 访问不了
    -e POSTGRES_PASSWORD=123456，设置环境变量，指定数据库的登录口令为123456 密码（默认用户名postgres） 
    -e POSTGRES_USER='postgres 设置环境变量，指定数据库用户名为postgres   
    -d kartoza/postgis:9.6-2.4 后台运行 ，允许该容器以守护态（Daemonized）形式运行于后台  
    --name容器名称

***参考博客***：
https://www.cnblogs.com/dengxiaoning/p/12173605.html