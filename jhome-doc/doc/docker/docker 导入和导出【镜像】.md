

    镜像命名
    docker tag 8d238fgs888sdf kkfileview
    
    导出镜像 标准格式：
    docker save -o <保存路径> <镜像名称:标签>
    
    把镜像导出到当前目录：
    docker save -o ./java8u111.tar java:8u111
    
    导入镜像 镜像所在的当前目录中导入：
    docker load --input ./java8u111.tar

#### 参考网址：
https://www.cnblogs.com/zhangwufei/p/9034997.html
https://www.jianshu.com/p/4e862a2a2d03