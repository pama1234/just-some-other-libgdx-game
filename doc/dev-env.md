开发环境配置
===========


## 开发工具

> 可以搜索到详细的安装教程，此处不再赘述

### git
版本管理、多人协作
https://git-scm.com/

可选：git GUI 界面
- 使用 IDE 自带的 git 界面
- 使用 Github 的客户端 https://desktop.github.com/

### libGDX
游戏引擎
https://libgdx.com/wiki/start/setup

备注
- IDEA 用户按照文档自行配置环境
- 如果你没有安装过 IDEA，则建议直接安装 Android Studio 全家桶
- 需要安装 2022.2.1 Beta 1 版本以后的 Android Studio 才支持 gradle 8.0+。  
    不然你只能使用命令行编译

### Java (JDK)
Java 开发运行环境
https://adoptium.net/

注意！
- libGDX 要求 JDK 版本为：11~18。  
    建议使用 JDK 17 (LTS) x64


## 环境配置

- [IDEA/Android Studio] 开启 Gradle 面板  
    不开启你的 Gradle 面板就是空的
    - 打开 File/Settings/Experimental
    - 取消掉 Gradle: Only include test tasks in the Gradle task...
