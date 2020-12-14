    拉取Dubbo:
    docker pull chenchuxin/dubbo-admin
    

    
    启动dubbo :
    docker run -d \
    -p 8888:8080 \
    -e dubbo.registry.address=zookeeper://10.1.137.181:2181 \
    -e dubbo.admin.root.password=root \
    -e dubbo.admin.guest.password=root \
    --name dubbo-admin --restart always  chenchuxin/dubbo-admin 
    
    
    启动dubbo 已经内置:
    docker run --name dubbo-admin -p 8888:8080 --link (zookeeper镜像名称) -d --restart always  df6b79f2b754
    
    
    其他资源：
    https://my.oschina.net/fufangchun/blog/3154303
    http://wurao.xin/blog/articles/370.html
    https://www.jianshu.com/p/e73d9f3576dd
    https://blog.csdn.net/qq_33562996/article/details/80599922
