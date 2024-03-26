package com.games.collection;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import com.simple.spiderman.SpiderMan;
import java.io.IOException;

public class MainActivity extends AppCompatActivity{

  DrawerLayout layout;
  AppBarConfiguration mAppBarConfiguration;
  public static AssetManager assets;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    assets=getAssets();

    setContentView(R.layout.activity_main);

    setSupportActionBar(findViewById(R.id.toolbar));
    getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    var v=findViewById(R.id.fab);
    v.setOnClickListener(vv-> {
      share();
    });

    layout=findViewById(R.id.drawer_layout);
    final View contentView=findViewById(R.id.app_bar_main);
    var actionBarDrawerToggle=new ActionBarDrawerToggle(
      this,
      layout,
      findViewById(R.id.toolbar),
      R.string.navigation_drawer_open,
      R.string.navigation_drawer_close) {
      @Override
      public void onDrawerSlide(View drawerView,float slideOffset) {
        super.onDrawerSlide(drawerView,slideOffset);
        float slideX=drawerView.getWidth()/2f*slideOffset;
        contentView.setTranslationX(slideX);
      }

      @Override
      public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
      }

      @Override
      public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
      }
    };
    actionBarDrawerToggle.syncState();
    layout.addDrawerListener(actionBarDrawerToggle);
    layout.setScrimColor(Color.TRANSPARENT);

    NavigationView nav=findViewById(R.id.nav_view);
    nav.setNavigationItemSelectedListener(
      new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem arg0) {
          layout.close();
          return true;
        }
      });

    mAppBarConfiguration=new AppBarConfiguration.Builder(
      R.id.nav_home,R.id.nav_gallery,R.id.nav_about)
        .setOpenableLayout(findViewById(R.id.drawer_layout))
        .build();
    NavController navController=Navigation.findNavController(this,R.id.nav_host_fragment_content_main);
    NavigationUI.setupActionBarWithNavController(this,navController,mAppBarConfiguration);
    NavigationUI.setupWithNavController(nav,navController);

  }

  @Override
  protected void onStart() {
    super.onStart();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main,menu);
    try {
      ImageView iv=findViewById(R.id.nav_imageView);
      Bitmap bitmap=BitmapFactory.decodeStream(assets.open("icon/icon128.png"));
      iv.setImageBitmap(bitmap);
    }catch(IOException e) {
      SpiderMan.show(e);
    }
    return true;
  }
  public void share() {
    Intent sendIntent=new Intent();
    sendIntent.setAction(Intent.ACTION_SEND);
    sendIntent.putExtra(Intent.EXTRA_TEXT,"要分享的文本");

    // Android 10 开始，可以通过 Intent.EXTRA_TITLE 添加描述信息，ClipData 添加缩略图
    sendIntent.putExtra(Intent.EXTRA_TITLE,"我是标题");

    // 设置分享的类型
    sendIntent.setType("text/plain");

    Intent shareIntent=Intent.createChooser(sendIntent,null);
    startActivity(shareIntent);
  }
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // TODO: Implement this method
    if(item.getItemId()==R.id.action_share) {
      share();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    if(layout.isOpen()) {
      layout.close();
      return;
    }
    super.onBackPressed();
  }
  @Override
  public boolean onSupportNavigateUp() {
    NavController navController=Navigation.findNavController(this,R.id.nav_host_fragment_content_main);
    return NavigationUI.navigateUp(navController,mAppBarConfiguration)
      ||super.onSupportNavigateUp();
  }
}
