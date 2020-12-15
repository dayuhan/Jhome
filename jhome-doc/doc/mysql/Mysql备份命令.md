### mysql 8.0前修改密码

    数据库备份
    ------------------------------------------------------------------------------------------
    cmd转到mysql的bin目录 执行备份命令
    mysqldump -uroot -p -R -E  --databases qasfjxx>E:\gta\dbBackup\fjxx20190522Dx.sql
    mysqldump -h 127.0.0.1 -P3306  -uroot -pfjxxjxzd#$2018  -R -E   --force -f  qasfjxx>E:\gta\dbBackup\fjxx20190525Dx.sql
    mysqldump -h 127.0.0.1 -P3306  -uroot -pfjxxjxzd#$2018  -R -E     qasfjxx>E:\gta\dbBackup\fjxx20190528Dx.sql 
    mysqldump -h 127.0.0.1 -P3306  -uroot -pfjxxjxzd#$2018  -R -E     qasfjxxetl>E:\gta\dbBackup\dbDBackup\db_backup\qasfjxxetl.sql
    mysqldump -h 127.0.0.1 -P3306  -uroot -pfjxxjxzd#$2018  -R -E     qas171>E:\gta\dbBackup\qas17120190625001Dx.sql 
    mysqldump -h 127.0.0.1 -P3306  -uroot -pabc104  -R -E     fjxx>d:\fjxx.sql 
    mysqldump -h 127.0.0.1 -P3306  -uroot   -R -E     qasfjxxetl>E:\gta\dbBackup\dbDBackup\db_backup\qasfjxxetl.sql
    mysqldump -h 127.0.0.1 -P8089  -uroot -proot  -R -E     qas_v1.1.5>C:\Users\Administrator\Desktop\DAXV\Dping.sql
    
    -- 数据库备份命令
    mysqldump -h 127.0.0.1 -P3306  -uroot -pgta@104  -R -E     qasfjxxetl>D:\dbbackup20191015_917917_qasfjxxETL.sql
    mysqldump -h 127.0.0.1 -P3306  -uroot -pfjxxjxzd#$2018  -R -E     qasfjxxetl>E:\gta\dbBackup\dbDBackup\db_backup\dbbackup20200901_qasfjxxETL.sql
     
    
     
     
     mysqldump -h 10.241.1.152 -P3306  -uroot -pgta@104  -R -E     qasfjxxetl>D:\dbbackup20191015_917917_qasfjxxETL.sql
     
     
     
    -- 只备份存错过程
    mysqldump -h 127.0.0.1 -P3306  -uroot -pfjxxjxzd#$2018 -n -d -t -R qasfjxxetl > D:\procedure_name.sql
     
    
     
     
     mysqldump -h 127.0.0.1 -P3307  -uroot -pabc105 -n -d -t -R qas_fjxx_v183 > D:\qas_fjxx_v183.sql
     
     mysqldump -h 127.0.0.1 -P3307  -uroot -pabc105  -R -E    sjzb> D:\sjzb.sql
     
     
     mysqldump -h 127.0.0.1  -P3307 -uroot -pabc105 --default-character-set=gbk --opt --extended-insert=false --triggers -R --hex-blob -x sjzb > D:\sjzb.sql
      
    mysqldump -h 127.0.0.1 -P8089  -uroot -proot  -R -E     qas_v1.1.5>C:\Users\Administrator\Desktop\DAXV\Dping.sql
    
    
    
    
    -- 纯净环境
    mysqldump -uroot -pabc105 -h10.1.137.105 -P3307 -R -E sjzb>d:\QasDataBase.sql
    -- 全部替换 907 个
    `sjzb`. 
     
     D:\纯境环境\QAS\API\国泰安质量保证系统-SetupFiles
     \\10.1.136.157\国泰安质量保证系统-SetupFiles
     
    mysql  -h 10.1.241.152 -P3306 -uroot -proot152 --default-character-set=utf8 -c qas_fjxx_184_yw20200629<D:\QasDataBase.sql
    
    mysql  -h 10.1.241.152 -P3306 -uroot -proot152 --default-character-set=utf8 -c qasfjxxETL20200902<C:\Users\Administrator\Desktop\dbbackup20200901_qasfjxxETL\dbbackup20200901_qasfjxxETL.sql
      
      
    数据库还原
    ------------------------------------------------------------------------------------------
    mysql  -h 127.0.0.1 -P3306 -uroot -pfjxxjxzd#$2018 --default-character-set=utf8 -c qasfjxxetl<E:\gta\dbBackup\dbDBackup\db_backup\qas_fjxx0811.sql
    mysql  -h 127.0.0.1 -P3306 -uroot -p --default-character-set=utf8 -c qasfjxxetl<E:\gta\dbBackup\dbDBackup\db_backup\dbbackup20190918_917918_qasfjxxETL.sql
    
     
     mysql  -h 127.0.0.1 -P3306 -uroot   qas171<E:\gta\dbBackup\dbDBackup\db_backup\dbbackup20190916_102816_fjxx.sql 
     mysql  -h 127.0.0.1 -P3306 -uroot -pfjxxjxzd#$2018   qas_fjxx_v183<C:\Users\Administrator\Desktop\fjxxV1.8.3\qas_fjxx_v183.sql
     
     --2019-09-12
     mysql  -h 127.0.0.1 -P3306 -uroot -p --default-character-set=utf8 -c qasfjxxetl<E:\gta\dbBackup\dbDBackup\db_backup\dbbackup20190919_20190919_qasfjxxETL.sql
     mysql  -h 127.0.0.1 -P3306 -uroot -proot --default-character-set=utf8 -c qasfjxxetl<D:\dbbackup20191015_233002_qasfjxxETL.sql
     mysql  -h 127.0.0.1 -P3306 -uroot -proot --default-character-set=utf8 -c qasfjxxetl<D:\数据库备份\dbbackup20191015_233002_qasfjxxETL.sql
     
     数据库安装
    ------------------------------------------------------------------------------------------
    mysqld -install MySQL --defaults-file="D:\mysql-5.7.23-winx64\my.ini"
    
    mysqld --initialize
    
    mysqld -install mysql57new --defaults-file="E:\mysql5.7\my.ini"
    



