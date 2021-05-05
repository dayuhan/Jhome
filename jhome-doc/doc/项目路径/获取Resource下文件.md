###获取Resource下文件
     String path = this.getClass().getClassLoader().getResource("Ser
     路径中包括中文：
     path = URLDecoder.decode(path, "UTF-8");