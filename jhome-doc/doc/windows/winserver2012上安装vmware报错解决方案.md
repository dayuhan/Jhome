###windows server2012 r2   
安装vmware 出现Microsoft runtime dll 库未安装，如果直接安装运行库，然后安装vmware 中途会出现感叹号，进行安装撤回，导致安装失败

解决办法如下:

1）下载更新包

    Windows8.1-KB2919442-x64.msu
    
    Windows8.1-KB2919355-x64.msu

下载地址

https://download.microsoft.com/download/C/F/8/CF821C31-38C7-4C5C-89BB-B283059269AF/Windows8.1-KB2919442-x64.msu

https://download.microsoft.com/download/D/B/1/DB1F29FC-316D-481E-B435-1654BA185DCF/Windows8.1-KB2919355-x64.msu

并按次序安装，注意：安装第二个包Windows8.1-KB2919355-x64.msu时需要等待一段时间才会有反应。

2） 如果已经安装了Microsoft 2015 c++ runtime dll (x64,和x86)，在控制面板-卸载程序列表-点击该程序 修复该程序进行修复，会出现设置成功界面

3)如果未安装，需要安装Microsoft 2015 c++ runtime dll (x64,和x86) 运行库

4） 安装vmware 15.04 安装成功。

安装附件：  
  windows server 2012 BD.rar

