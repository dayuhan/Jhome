    @echo off   
    
    echo 1、开始启动 jhome-configService 服务...
    start javaw -jar jhome-configService.jar --spring.config.location=application-configService.yml
    ping 127.0.0.1 -n 10 > nul
    echo 服务jhome-configService.jar启动成功!
    ping 127.0.0.1 -n 4 > nul
     
    exit





