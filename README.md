# just-some-other-libgdx-game

[中文](#空想世界) | [English](#game-with-java)

## 空想世界

《空想世界》开源游戏项目系列的托管库

使用的库：libgdx，kryo，vecmath

主要类位置：pama1234.gdx.launcher.MainApp

项目代码帮助文档：`doc\codeHelp.txt`

代码格式化请使用`doc\eclipse.formatter.xml`，如果不愿意使用此格式化方案，那么请勿提交四格缩进的代码，其他随意

命名规则：tx是temp-x的缩写，一般用于局部变量中,其他还有ty，tw，th，这些分别表示位置x，位置y，体积w，体积z，之类的

待办事项请看doc文件的todo.txt,若完成了某一项，请将其剪切粘贴到solved.txt，编译安卓项目请用自己的签名文件, 可以使用中英文或其他语言作为待办项，但不能修改已有待办项的语言类型

安卓版改native依赖项的时候记得手动删除`android\libs\`内的文件和文件夹

## Game-With-Java

The git repo of the open-source game project series of Game-With-Java (the english name is to be determined)

Libraries used: libgdx, kryo, vecmath, aparapi

Main class location: pama1234.gdx.launcher.MainApp

Code help doc: `doc\codeHelp.txt`

Please use `doc\eclipse.formatter.xml` for code format. If you do not want to use this format scheme, please do not submit four-space indented code

Naming rules: tx is the abbreviation of temp-x, which is generally used in local variables. Other variables include ty, tw, and th, which represent position x, position y, volume w, and volume z respectively, and so on.

For to-do items, please view `/doc/todo.txt` file, when you finished a to-do item ,please move it to `/doc/solved.txt`. You can write to-do items freely with different languages , but you cannot change the language of existing to-do items.

Please use your own signature file to compile Android sub-project

Please delete files and folders in `android\libs\` manually when changing native dependencies in Android sub-project
