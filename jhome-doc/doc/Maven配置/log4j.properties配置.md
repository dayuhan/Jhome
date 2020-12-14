    log4j.rootCategory=debug, CONSOLE ,LOGFILE
    
    log4j.logger.com.bjsxt.mapper=DEBUG
    
    log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
    log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
    log4j.appender.CONSOLE.layout.ConversionPattern=%C %d{YYYY-MM-dd hh:mm:ss}  %m %n
    
    log4j.appender.LOGFILE=org.apache.log4j.FileAppender
    log4j.appender.LOGFILE.File=d:/my.log
    log4j.appender.LOGFILE.Append=true
    log4j.appender.LOGFILE.layout=org.apache.log4j.PatternLayout
    log4j.appender.LOGFILE.layout.ConversionPattern=%m %n

 配制二

    log4j.rootLogger=INFO,CONSOLE
    log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
    log4j.appender.CONSOLE.Target=System.out
    log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
    log4j.appender.CONSOLE.layout.ConversionPattern=%d{yyyy-MM-dd HH\:mm\:ss.SSS} %-5p [%t] %c %x - %m%n


