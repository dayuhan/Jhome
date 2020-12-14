##创建库：

    把当前目录修改成git仓库 
    $ git init
    
    用命令git add告诉Git，把文件添加到仓库：
    $ git add readme.txt
    
    第二步，用命令git commit告诉Git，把文件提交到仓库：
    $ git commit -m "wrote a readme file"
    
    git status 命令可以让我们时刻掌握仓库当前的状态
    $ git status
    
    看看具体修改了什么内容
    $ git diff readme.txt 




 1、新建代码库

    # 在当前目录新建一个Git代码库
     git init
    # 新建一个目录，将其初始化为Git代码库
    git init [project-name]
    # 下载一个项目和它的整个代码历史
    git clone [url]


 2、查看文件状态

    #查看指定文件状态
    git status [filename]
    #查看所有文件状态
    git status

3、工作区<-->暂存区

    # 添加指定文件到暂存区
    git add [file1] [file2] ...
    # 添加指定目录到暂存区，包括子目录
    git add [dir]
    # 添加当前目录的所有文件到暂存区
    git add .
    #当我们需要删除暂存区或分支上的文件, 同时工作区也不需要这个文件了, 可以使用（⚠️）
    git rm file_path
    #当我们需要删除暂存区或分支上的文件, 但本地又需要使用, 这个时候直接push那边这个文件就没有，如果push之前重新add那么还是会有。
    git rm --cached file_path
    #直接加文件名   从暂存区将文件恢复到工作区，如果工作区已经有该文件，则会选择覆盖
    #加了【分支名】 +文件名  则表示从分支名为所写的分支名中拉取文件 并覆盖工作区里的文件
    git checkout


 4、工作区<-->资源库（版本库）


    #将暂存区-->资源库（版本库）
    git commit -m '该次提交说明'
    #如果出现:将不必要的文件commit 或者 上次提交觉得是错的  或者 不想改变暂存区内容，只是想调整提交的信息
    #移除不必要的添加到暂存区的文件
    git reset HEAD 文件名
    #去掉上一次的提交（会直接变成add之前状态）   
    git reset HEAD^ 
    #去掉上一次的提交（变成add之后，commit之前状态） 
    git reset --soft  HEAD^ 


5、远程操作

    # 取回远程仓库的变化，并与本地分支合并
    git pull
    # 上传本地指定分支到远程仓库
    git push

6、其它常用命令

    # 显示当前的Git配置
    git config --list
    # 编辑Git配置文件
    git config -e [--global]
    #初次commit之前，需要配置用户邮箱及用户名，使用以下命令：
    git config --global user.email "you@example.com"
    git config --global user.name "Your Name"
    #调出Git的帮助文档
    git --help
    #查看某个具体命令的帮助文档
    git +命令 --help
    #查看git的版本
    git --version

