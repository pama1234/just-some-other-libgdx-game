# 多语言

也叫做localization

## 需要修改的文件位置

- game0001\game0001-core\src\main\java\pama1234\gdx\game\state\state0001\settings\SettingsUtil.java
- game0001\game0001-core\src\main\java\pama1234\gdx\game\state\state0001\Announcement.java
- game0001\game0001-core\src\main\java\pama1234\gdx\game\state\state0001\ExceptionState.java
- game0001\game0001-core\src\main\java\pama1234\gdx\game\state\state0001\FirstRun.java
- game0001\game0001-core\src\main\java\pama1234\gdx\game\state\state0001\GameMenu.java
- game0001\game0001-core\src\main\java\pama1234\gdx\game\state\state0001\Loading.java
- game0001\game0001-core\src\main\java\pama1234\gdx\game\state\state0001\Settings.java
- game0001\game0001-core\src\main\java\pama1234\gdx\game\state\state0001\StartMenu.java
- game0001\game0001-core\src\main\java\pama1234\gdx\game\ui\generator\InfoUtil.java
- game0001\game0001-core\src\main\java\pama1234\gdx\game\ui\generator\UiGenerator.java
- game0001\game0001-core\src\main\java\pama1234\gdx\launcher\MainApp.java
- game0001\game0001-lwjgl3\src\main\java\pama1234\gdx\game\app\lwjgl3\Launcher.java

## 框架设计

1. 需要能够读取特定类型的文化或字符串（发表到知乎）并排序（可选）后转换为数组
2. 存储到本地
3. 每次运行时加载
4. 高性能，易于调用
5. 归入framework-0001-localization中
6. 使用YAML和kryo

## 现有框架

1. Java Internationalization (Java i18n) - Java自带的国际化和本地化库，用于将应用程序本地化为不同的语言并适应不同的区域设置。
2. Spring Framework - Spring框架提供了一组方便的工具和类来支持本地化和国际化。它包括MessageSource接口和LocaleResolver接口等类。
3. Apache ResourceBundle - 这是一个开源的Java库，可让您轻松管理本地化资源和翻译。使用该库，可以在应用程序中集中管理所有文本字符串。
4. ICU4J - 该框架提供了开箱即用的本地化和国际化解决方案。该框架提供了Unicode公共语言数据存储库（CLDR）的实现，该存储库包含有关世界各地各种语言和文化的详细信息。
5. Joda-Time - 可以处理日期、时间和时区等问题。它可与许多本地化库结合使用，如Java Internationalization（Java i18n）和ICU4J。
