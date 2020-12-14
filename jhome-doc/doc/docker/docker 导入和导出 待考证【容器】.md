    导出容器：
    
    docker export <容器名> > <保存路径>
    如导在A机中导出容器ubuntu18:
    
    docker export ubuntu18 > ./ubuntu18.tar
    导入容器的命令：
    
    
    docker import <文件路径>  <容器名>
    把A机当前文件夹下的ubuntu18.tar文件拷贝到B机上，在B机上运行：
    
    docker import ./ubuntu18.tar ubuntu18
    启动容器：
    
    docker start <容器名>
    B机上就多了一个名为ubuntu18的容器，但导入的容器还处在未运行的状态，需要启动容器：
    
    docker start ubuntu18
    

    链接：https://www.jianshu.com/p/4e862a2a2d03
    来源：简书
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。