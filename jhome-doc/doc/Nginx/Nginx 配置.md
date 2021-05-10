

    #user  nobody;
    worker_processes  2;
    
    #error_log  logs/error.log;
    #error_log  logs/error.log  notice;
    #error_log  logs/error.log  info;
    
    #pid        logs/nginx.pid;
    
    
    events {
        worker_connections  1024;
    }
    
    
    http {
        include       mime.types;
        default_type  application/octet-stream;
    
        #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
        #                  '$status $body_bytes_sent "$http_referer" '
        #                  '"$http_user_agent" "$http_x_forwarded_for"';
    
        #access_log  logs/access.log  main;
    
        sendfile        on;
        #tcp_nopush     on;
    
        #keepalive_timeout  0;
        keepalive_timeout  65;
        # 配置Nginx setCookie长度过大 设置
        proxy_connect_timeout 300;
        proxy_send_timeout 300;
        proxy_read_timeout 300;
        proxy_buffering on;
        proxy_buffer_size 100k;
        proxy_buffers 100 200k;
        proxy_busy_buffers_size 200k
        proxy_temp_file_write_size 200k;
    
        #gzip  on;
    
    
        upstream account{   
          #server 10.1.20.58:8100;
          server 10.1.136.38:8100;
        }    
    		
        error_page 404 http://www.hao123.com; #错误页
        server {  
            keepalive_requests 120; #单连接请求上限次数。
            listen       9101;   #监听端口
            server_name  127.0.0.1;   #监听地址    
                    location / {
             
                        root   web/;                       #3.dist文件的位置(我是直接放在home目录下了) 
            
                        try_files $uri $uri/ /index.html;   #4.重定向,内部文件的指向(照写)
       
       		} 
    		location  ^~/identity/ { 
    			proxy_pass  http://account/; 
     			#proxy_pass  http://10.1.136.38:8100/; 
     		        #proxy_pass  http://10.1.20.58:8100/; 	       		
    			#return 601;
    		}
    		location  ^~/taskchain/ {		
    			proxy_pass  http://10.1.136.38:8300/;
    			#proxy_pass  http://10.1.20.58:8300/; 
    			#return 602; 
    		}
    		location  ^~/bigscreen/ {
    		        proxy_pass  http://10.1.136.38:6410/;
    			#proxy_pass  http://10.1.20.58:6410/; 
    			#return 603;
    		} 
    		location  ^~/configService/ {
    		        proxy_pass  http://10.1.136.38:8769/;
    			#proxy_pass  http://10.1.20.58:8769/; 
    			#return 604;
    		} 
    		location  ^~/fileStore/ {
    		        #proxy_pass  http://10.1.20.23:8400/;
    			#proxy_pass  http://10.1.136.38:8400/; 
    			proxy_pass  http://10.1.20.58:8400/; 
    			#return 605;
    		}
    		location  ^~/diagnosisIndex/ {
    		        #proxy_pass  http://10.1.20.58:8500/;
    			proxy_pass  http://10.1.136.38:8500/; 
    			#return 605;
    		}
    
    	
    
    		 
            #location  ~*^.+$ {       #请求的url过滤，正则匹配，~为区分大小写，~*为不区分大小写。
               #root path;  #根目录
               #index vv.txt;  #设置默认页
               #proxy_pass  http://mysvr;  #请求转向mysvr 定义的服务器列表
               #deny 127.0.0.1;  #拒绝的ip
               #allow 172.18.5.54; #允许的ip           
            #} 
        }
    	
    	 
    
        # another virtual host using mix of IP-, name-, and port-based configuration
        #
        #server {
        #    listen       8000;
        #    listen       somename:8080;
        #    server_name  somename  alias  another.alias;
    
        #    location / {
        #        root   html;
        #        index  index.html index.htm;
        #    }
        #}
    
    
        # HTTPS server
        #
        #server {
        #    listen       443 ssl;
        #    server_name  localhost;
    
        #    ssl_certificate      cert.pem;
        #    ssl_certificate_key  cert.key;
    
        #    ssl_session_cache    shared:SSL:1m;
        #    ssl_session_timeout  5m;
    
        #    ssl_ciphers  HIGH:!aNULL:!MD5;
        #    ssl_prefer_server_ciphers  on;
    
        #    location / {
        #        root   html;
        #        index  index.html index.htm;
        #    }
        #}
    
    }

