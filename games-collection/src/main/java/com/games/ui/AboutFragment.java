package com.games.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import androidx.fragment.app.Fragment;
import com.games.collection.R;

public class AboutFragment extends Fragment{
  @Override
  public View onCreateView(LayoutInflater arg0,ViewGroup arg1,Bundle arg2) {
    View layout=arg0.inflate(R.layout.about,arg1,false);
    ;
    WebView web=layout.findViewById(R.id.aboutWebView);
    //为了方便在网络上实时更新，可以改为网络链接
    web.loadUrl("file:///android_asset/index.html");
    return layout;
  }

}
