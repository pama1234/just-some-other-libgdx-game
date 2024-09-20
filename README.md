# Libgdx游戏合集

<p align="center"><img src="doc/image/logo.png" alt="logo"></p>

[中文](README.md) | [English](README.en.md)

[![Jitpack Badge](https://jitpack.io/v/pama1234/just-some-other-libgdx-game.svg)](https://jitpack.io/#Java-Game-Engine-Merger/Libgdx-Processing-Plus)

## 使用 Protobuf

请先从[Protobuf 最新版本](https://github.com/protocolbuffers/protobuf/releases/latest)下载 `protoc` 并将其加入到 `PATH` 环境变量中，否则在运行 Gradle 的 `build` 和 `generateProto` 任务时会报错。

## 如何上手

### 创建新的草图

1. 阅读[游戏合集](#游戏合集)。
2. 打开对应项目（例如 `game0001`）的 `pama1234.gdx.launcher.MainApp`。
3. 创建新的 `Screen0xxx`。
4. 将你的 `Screen0xxx` 继承 `ScreenCore2D` 或 `ScreenCore3D`。

## 配置 `settings.gradle`

Gradle 速度较慢，本项目包含至少 40 个子项目，可能会产生 9000 多个任务。因此，在开发时，建议在 `settings.gradle` 文件中注释掉部分未使用的项目。如果需要使用这些项目，请取消注释。

## 创建新项目

如果开发者想要创建新项目，需要在项目根目录下的 `build.gradle` 文件的（目前为）第 35 行 `configure(subprojects.findAll {` 位置添加以下内容：

```gradle
|| it.name == '项目名-android'
```

此操作用于排除无法使用 Gradle 的 Java 插件的子项目。

## 教程和文档

（未完善，整理中）<https://github.com/Java-Game-Engine-Merger/libgdx-processing-website>

## 如何使用我们的框架

我们的框架通过 Jitpack 进行打包。

[框架地址](https://jitpack.io/#pama1234/just-some-other-libgdx-game/)

注意，菱形依赖问题尚未解决，因此在其他项目中使用 `framework0001` 及其他内容时，可能需要配置大量的 `exclude` 规则，特别是在打包安卓时。推荐仅使用以下两个基础框架：

### Gradle

```gradle
implementation "com.github.pama1234.just-some-other-libgdx-game:server-framework:$pama1234Version"
implementation "com.github.pama1234.just-some-other-libgdx-game:framework:$pama1234Version"
```

或直接指定版本号：

```gradle
implementation 'com.github.pama1234.just-some-other-libgdx-game:server-framework:bf0a359313'
implementation 'com.github.pama1234.just-some-other-libgdx-game:framework:bf0a359313'
```

### Maven

```xml

<dependency>
  <groupId>com.github.pama1234.just-some-other-libgdx-game</groupId>
  <artifactId>framework</artifactId>
  <version>bf0a359313</version>
</dependency>
```

## 游戏合集

1. 空想世界1[README.md](game0001%2FREADME.md)
2. 粒子系统【暂停维护】
3. 几何决斗【移植自 FAL 学生时期作品，已火】[README.g03.md](game0003%2FREADME.g03.md)
4. 高维塔防【3D 游戏模板】
5. 杂项
6. 中心 IDE
7. 填满正方形
8. 粒子生命：升天（魔改版 2D Processing 粒子系统）
9. 异星征途
10. 聊天室
11. 数值膨胀，以及矢量天空，及 TRPG 系统【移动到 ce4kotlin】
12. 光遇2，流浪遇球，别太荒谬了【ce4kotlin】
13. 物品管理和其他同类游戏【galgame】
14. 天顶语言
15. MBTI 模拟器

## 空想世界

《空想世界》开源游戏项目系列的托管库。

QQ群：589219461

### 配置环境

1. 确保您的电脑可以直接访问以下必要网站，推荐使用[此方案](https://github.com/getlantern/lantern)。
2. 下载并安装 `JDK-17`，配置 `JAVA_HOME` 和 `PATH` 环境变量，推荐[此版本 (GraalVM 22.1.0)](https://github.com/graalvm/graalvm-ce-builds/releases/tag/vm-22.1.0)。
3. 下载并安装 `Gradle-8`，配置 `GRADLE_HOME` 和 `GRADLE_JAVA_HOME` 环境变量。[下载链接](https://gradle.org/releases)。
4. 下载并安装 `Android Studio Canary build`，配置 `AndroidSDK` 环境变量（这是内测版，页面右侧总有最新版本）。[下载链接](https://developer.android.com/studio/preview)。

### 使用的库

- libgdx
- kryo
- vecmath

### 主要类位置

`pama1234.gdx.launcher.MainApp`

### 项目代码帮助文档

`doc\codeHelp.txt`

### 代码格式化

请使用 `doc\eclipse.formatter.xml` 进行代码格式化。如果您不愿意使用此格式化方案，请勿提交使用四格缩进的代码，其他部分可随意。

### 局部变量的命名规则

| 类型  | 总维度数 | 维度数 | 英文字符   |
|-----|------|-----|--------|
| 位置  | 3    | 1   | x      |
| 位置  | 3    | 2   | y      |
| 位置  | 3    | 3   | z      |
| 体积  | 3    | 1   | w      |
| 体积  | 3    | 2   | h      |
| 体积  | 3    | 3   | l      |
| 整数  | ?    | 1   | i      |
| 浮点数 | ?    | 1   | f      |
| 布尔值 | ?    | 1   | b      |
| 父实例 | ?    | 1   | p      |
| 翻译包 | ?    | 1   | bd, ld |

### 简写示例

| 类型  | 示例                       |
|-----|--------------------------|
| 位置  | tx, tx1, tx_1, tx2, tx_2 |
| 父实例 | p, pc, pw, pg            |

（`tx` 是 `temp-x` 的缩写）

### 待办事项

请查看 `doc\todo.txt` 文件，完成某项任务后，请将其移动到 `solved.txt`。编译安卓项目时请使用您自己的签名文件。待办项可以使用中英文或其他语言，但请勿修改已有待办项的语言类型。

### 安卓版注意事项

在修改 `native` 依赖项时，请记得手动删除 `android\libs\` 内的文件和文件夹。