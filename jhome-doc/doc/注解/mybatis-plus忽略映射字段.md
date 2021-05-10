    mybatis-plus使用对象属性进行SQL操作，经常会出现对象属性非表字段的情况，忽略映射字段使用以下注解：
    
    @TableField(exist = false)：表示该属性不为数据库表字段，但又是必须使用的。
    @TableField(exist = true)：表示该属性为数据库表字段。
    
    Mybatis-Plus 插件有这个功能，可以看一下
    
     
    
    @TableName：数据库表相关
    
    @TableId：表主键标识
    
    @TableField：表字段标识
    
    @TableLogic：表字段逻辑处理注解（逻辑删除）
    ————————————————
    版权声明：本文为CSDN博主「huayang183」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
    原文链接：https://blog.csdn.net/qq_18671415/article/details/110499025