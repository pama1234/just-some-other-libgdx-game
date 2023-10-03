package hhs.game.diffjourney;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/* Launches the Android application. */
public class AndroidLauncher extends AndroidApplication{
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //LogSender.startLogging(this);
    // if (Build.VERSION.SDK_INT >= 23) { // 6.0
    //   String[] perms = {
    //     Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    //   };
    //   requestPermissions(perms, 0XCF);
    // }
    // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
    //   if (!Environment.isExternalStorageManager()) {
    //     startActivity(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION));
    //   }
    // }
    /* You can adjust this configuration to fit your needs */
    AndroidApplicationConfiguration configuration=new AndroidApplicationConfiguration();
    configuration.useImmersiveMode=true;
    initialize(new MainGame(),configuration);
  }
}
