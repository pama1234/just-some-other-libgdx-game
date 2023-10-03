package pama1234.gdx.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;

import pama1234.gdx.Pama;

public class UtilAndroidApplication extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        {
            Pama.mobile=new AndroidMobileUtil(this);
        }
    }
}
