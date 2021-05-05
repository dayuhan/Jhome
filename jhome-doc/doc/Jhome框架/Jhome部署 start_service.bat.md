   @echo off

    echo 开始启动 jhome-configService 服务...
    start javaw -jar jhome-configService.jar
    ping 127.0.0.1 -n 15 > nul
    echo 服务jhome-configService启动成功!
     
    
    echo 开始启动 jhome-registration 服务...
    start javaw -jar jhome-registration.jar
    ping 127.0.0.1 -n 15 > nul
    echo 服务jhome-registration启动成功!
    
    
    echo 开始启动 jhome-nettyService服务...
    start javaw -jar jhome-nettyService.jar
    ping 127.0.0.1 -n 5 > nul
    echo 服务jhome-AgreementService启动成功!  
    
    echo 开始启动 jhome-account 服务...
    start javaw -jar jhome-account.jar
    ping 127.0.0.1 -n 30 > nul
    echo 服务jhome-account启动成功!
    
     
    echo 开始启动 jhome-web服务...
    start javaw -jar jhome-web.jar
    ping 127.0.0.1 -n 25 > nul
    echo 服务jhome-web启动成功! 
    
    
    
    exit