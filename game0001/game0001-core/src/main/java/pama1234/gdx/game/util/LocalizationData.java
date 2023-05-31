package pama1234.gdx.game.util;

public class LocalizationData{
  // 使用cursor进行更快的多语言化
  // 提示词：根据以下内容中出现的中文字符补全对应的英文变量名：【复制的代码】
  // 提示词：生成翻译包中的中文字符对应的英文变量名，根据以下内容中出现的中文字符补全对应的英文变量名，注意中文字符的语境：
  // ---
  // 提示词：根据以下两段代码生成YAML翻译包的内容，注意使用对应语言的标点符号，例如中文应当使用全角标点
  // 提示词：将以下代码中的对应部分修改为YAML翻译包的变量名，注意不要保留标点符号：
  public LocalizationData() {}
  public String main,taptap,pico;
  public String canUseGyroscope,canNotUseGyroscope,
    canUseCompass,canNotUseCompass,
    canUseAccelerometer,canNotUseAccelerometer,
    needRestart,
    thisIsIpAddr;
  public String gyroscopeX,gyroscopeY,gyroscopeZ,
    compassX,compassY,compassZ,
    gravityX,gravityY,gravityZ,
    accelerometerX,accelerometerY,accelerometerZ;
}