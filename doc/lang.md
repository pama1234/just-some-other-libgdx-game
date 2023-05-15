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
