**拉取redis:4这个版本，输入命令:**

    docker pull redis:4

**启动redis命令：**

    docker run --name some-redis1 -p 6379:6379 -d redis:4 --requirepass "123456"

**redis客户端连接命令：**
`docker exec -it 0b63b7eacc29 redis-cli` 启动时使用container id进行启动0b63b7eacc29， ﻿​