package android.launcher;

import android.os.Bundle;
import com.tapsdk.bootstrap.TapBootstrap;
import com.tds.common.entities.TapConfig;
import com.tds.common.models.TapRegionType;
import pama1234.gdx.android.UtilAndroidApplication;

public class TapTapLauncher extends UtilAndroidApplication{
  public static String taptapClientID="6ceedq4hm3b4zoofve";
  public static String taptapClientToken="GZUcpWPW5mep9JU79HRmtIY0PwoW1mAwQruQD6iF";

  public TapTapLauncher() {}

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // activity 为当前 Activity 实例
    TapConfig tdsConfig=new TapConfig.Builder()
      .withAppContext(this) // Context 上下文
      .withClientId("your_client_id") // 必须，开发者中心对应 Client ID
      .withClientToken("your_client_token") // 必须，开发者中心对应 Client Token
      .withServerUrl("https://your_server_url") // 必须，开发者中心 > 你的游戏 > 游戏服务 > 基本信息 > 域名配置 > API
      .withRegionType(TapRegionType.CN) // TapRegionType.CN：中国大陆，TapRegionType.IO：其他国家或地区
      .build();
    TapBootstrap.init(this,tdsConfig);
  }
}
