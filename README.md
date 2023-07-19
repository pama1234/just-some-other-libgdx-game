# Libgdx游戏合集

[![Sonatype Nexus (Releases)](https://img.shields.io/badge/Jit_version-bf0a359313-blue)](https://jitpack.io/#pama1234/just-some-other-libgdx-game/bf0a359313)

## 如何上手

1. 阅读[游戏合集](#游戏合集)
2. 打开对应项目（例如game0001）的`pama1234.gdx.launcher.MainApp`
3. 创建新的`Screen0xxx`
4. 将你的`Screen0xxx`继承`ScreenCore2D`或`ScreenCore3D`

## 配置`settings.gradle`

众所周知，gradle很慢，这个托管内有至少40个子项目，这会产生8000多个甚至更多的tasks，因此，我们开发时会将一部分用不上的项目在`settings.gradle`文件内注释掉，如果需要使用，取消注释即可

## 创建新项目

如果各位开发者想要创建新的项目，则需要在项目的根目录下的`build.gradle`的（目前）第35行的`configure(subprojects.findAll {`位置添加

```gradle
|| it.name =='项目名-android'
```

用于排除这个以及其他不能使用gradle的java plugin的子项目

## 如何使用我们的的框架

我们的框架通过jit进行打包

<https://jitpack.io/#pama1234/just-some-other-libgdx-game/>

注意，菱形依赖的问题并没有解决，因此在其他项目内使用framework0001和其他内容时可能会需要配置一大堆exclude规则，尤其是在打包安卓的时候，推荐只使用以下两个基础框架

### Gradle

```gradle
implementation "com.github.pama1234.just-some-other-libgdx-game:server-framework:$pama1234Version"
implementation "com.github.pama1234.just-some-other-libgdx-game:framework:$pama1234Version"
```

或直接标明版本

```gradle
implementation 'com.github.pama1234.just-some-other-libgdx-game:server-framework:bf0a359313'
implementation 'com.github.pama1234.just-some-other-libgdx-game:framework:bf0a359313'
```

### Maven

```maven
 <dependency>
     <groupId>com.github.pama1234.just-some-other-libgdx-game</groupId>
     <artifactId>framework</artifactId>
     <version>bf0a359313</version>
 </dependency>
```

## 游戏合集

0001. 空想世界1
0002. 粒子系统
0003. 几何决斗【从FAL的学生作品移植过来，已火】
0004. 高维塔防
0005. 杂项
0006. 中心IDE
0007. 填满正方形
0008. 粒子生命：升天（魔改版2D processing粒子系统）

[中文](#空想世界) | [English](#game-with-java)

## 空想世界

《空想世界》开源游戏项目系列的托管库

QQ群：589219461

配置环境：

0. 确保自己使用的电脑可以直接访问需要访问的以下网站，推荐[此方案](https://github.com/getlantern/lantern)
1. 下载并安装`jdk-17`，配置javahome和path环境变量，推荐[此版本(graalvm-22.1.0)](https://github.com/graalvm/graalvm-ce-builds/releases/tag/vm-22.1.0)
2. 下载并安装`gradle-8`，配置gradle和gradle-javahome环境变量，[链接](https://gradle.org/releases)
3. 下载并安装`Android studio Canary build`，配置AndroidSDK环境变量（也就是内测版，在页面的右侧，总之哪个新就安装哪个好啦）[链接](https://developer.android.com/studio/preview)

使用的库：libgdx，kryo，vecmath

主要类位置：pama1234.gdx.launcher.MainApp

项目代码帮助文档：`doc\codeHelp.txt`

代码格式化请使用`doc\eclipse.formatter.xml`，如果不愿意使用此格式化方案，那么请勿提交四格缩进的代码，其他随意

局部变量的命名规则：

|类型|总维度数|维度数|英文字符|
|---|---|---|---|
|位置|3|1|x|
|位置|3|2|y|
|位置|3|3|z|
|体积|3|1|w|
|体积|3|2|h|
|体积|3|3|l|
|整数|?|1|i|
|浮点数|?|1|f|
|布尔值|?|1|b|
|父实例|?|1|p|
|翻译包|?|1|bd,ld|

简写示例:

|类型|示例|
|---|---|
|位置|tx, tx1, tx_1, tx2, tx_2|
|父实例|p, pc, pw, pg|

（tx是temp-x的缩写）

待办事项请看doc文件的todo.txt,若完成了某一项，请将其剪切粘贴到solved.txt，编译安卓项目请用自己的签名文件, 可以使用中英文或其他语言作为待办项，但不能修改已有待办项的语言类型

安卓版改native依赖项的时候记得手动删除`android\libs\`内的文件和文件夹

## Game-With-Java

The git repo of the open-source game project series of Game-With-Java (the english name is to be determined)

Configure environment:

1. Download and install `jdk-17` and configure javahome and path environment variables. This version is recommended→ <https://github.com/graalvm/graalvm-ce-builds/releases/tag/vm-22.1.0>
2. Download and install `gradle-8`, configure gradle and gradle-javahome environment variables <https://gradle.org/releases>
3. Download and install `Android studio Canary build` and configure the Android SDK environment variables (The preview version, or whatever that is on the right side of the page, whatever is new should be installed) <https://developer.android.com/studio/preview>

Libraries used: libgdx, kryo, vecmath, aparapi

Main class location: pama1234.gdx.launcher.MainApp

Code help doc: `doc\codeHelp.txt`

Please use `doc\eclipse.formatter.xml` for code format. If you do not want to use this format scheme, please do not submit four-space indented code

Naming rules of local variables:

|Type|Total dimensions|Dimensions|English characters|
|---|---|---|---|
|Position|3|1|x|
|Position|3|2|y|
|Position|3|3|z|
|Volume|3|1|w|
|Volume|3|2|h|
|Volume|3|3|l|
|Integer|?|1|i|
|Float|?|1|f|
|Boolean|?|1|b|
|Parent|?|1|p|
|Translation package|?|1|bd,ld|

example:

|Type|Example|
|---|---|
|Position|tx, tx1, tx_1, tx2, tx_2|
|Parent|p, pc, pw, pg|

(tx is short for temp-x)

For to-do items, please view `/doc/todo.txt` file, when you finished a to-do item ,please move it to `/doc/solved.txt`. You can write to-do items freely with different languages , but you cannot change the language of existing to-do items.

Please use your own signature file to compile Android sub-project

Please delete files and folders in `android\libs\` manually when changing native dependencies in Android sub-project
