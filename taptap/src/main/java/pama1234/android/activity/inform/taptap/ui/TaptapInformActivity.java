package pama1234.android.activity.inform.taptap.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import pama1234.R;
import pama1234.gdx.game.app.android.AndroidLauncher;

public class TaptapInformActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.inform);
    }
    public void doExit(View view) {
        finishAffinity();
        System.exit(0);
    }
    public void enterGame(View view) {
        startActivity(new Intent(this, AndroidLauncher.class));
    }
}