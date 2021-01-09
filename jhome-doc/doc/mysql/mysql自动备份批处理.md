    rem ******MySQL backup start******
    @echo off
    
    forfiles /p "D:\Mysql_Backup" /m test_backup_*.sql -d -30 /c "cmd /c del /f @path"
    
    set "Ymd=%date:~0,4%%date:~5,2%%date:~8,2%0%time:~1,1%%time:~3,2%%time:~6,2%"
    mysqldump --opt --single-transaction=TRUE --user=root --password=123456 --host=127.0.0.1 --protocol=tcp --port=3306 --default-character-set=utf8 --single-transaction=TRUE --routines --events "test" > D:\Mysql_Backup\test_backup_%Ymd%.sql
    
    @echo on
    rem ******MySQL backup end******