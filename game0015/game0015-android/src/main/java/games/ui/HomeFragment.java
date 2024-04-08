package games.ui;

import java.util.ArrayList;
import java.util.List;

import com.games.collection.AndroidLauncher;
import games.collection.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.games.ui.ItemData;
import hhs.game.Mountain3d.ShaderTest;
import hhs.game.doomlibgdx.MainGame;
import hhs.game.hanoi.HanoiLauncher;
import hhs.game.lost.games.FallGuys;
import pama1234.gdx.game.app.Screen0037;
import pama1234.gdx.util.launcher.MainAppBase;

/**
 * 游戏列表页面
 */
public class HomeFragment extends Fragment{
  @Override
  public View onCreateView(LayoutInflater arg0,ViewGroup arg1,Bundle arg2) {
    // TODO: Implement this method
    RecyclerView root=new RecyclerView(arg0.getContext());
    var ap=new StringAdapter();
    root.setAdapter(ap);
    root.setLayoutManager(new LinearLayoutManager(arg0.getContext()));
    return root;
  }

  static class ViewHolder extends RecyclerView.ViewHolder{
    View tv;

    public ViewHolder(View v) {
      super(v);
      tv=v;
    }
  }

  static interface OnItem{
    public void touchItem(View item,int pos);
  }

  static class StringAdapter extends RecyclerView.Adapter<ViewHolder>{
    static class MainApp0037 extends MainAppBase{
      public MainApp0037() {
        super(Screen0037.class);
      }
    }

    //    static class MainApp0011 extends MainAppBase{
    //      public MainApp0011() {
    //        super(Screen0011.class);
    //      }
    //    }

    List<ItemData> list;
    OnItem onItem;

    public StringAdapter() {
      list=new ArrayList<>();
      // 第一个数据是标题，第二个数据是游戏主类
      //      list.add(new ItemData("空想世界",MainApp0011.class));
      list.add(new ItemData("填满正方形",MainApp0037.class));
      list.add(new ItemData("测试游戏1",ShaderTest.class));
      list.add(new ItemData("测试游戏2",MainGame.class));
      list.add(new ItemData("汉诺塔",HanoiLauncher.class));
      list.add(new ItemData("ikun游戏",hhs.hhshaohao.mygame2.games.MyGame.class));
      list.add(new ItemData("电子木鱼",hhs.game.gongde.games.MyGame.class).setLandscape(false));
      list.add(new ItemData("排序可视化",hhs.game.sort.games.MyGame.class));
      list.add(new ItemData("3d平衡球",FallGuys.class));
      list.add(new ItemData("随机地图",hhs.game.lost.games.voxel.VoxelTest.class));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup arg0,int arg1) {
      View v=LayoutInflater.from(arg0.getContext()).inflate(R.layout.item,arg0,false);
      return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder arg0,int arg1) {
      TextView tv=arg0.tv.findViewById(R.id.itemTextView);
      tv.setText(list.get(arg1).text);
      tv.setOnClickListener(
        vi-> {
          AndroidLauncher.game=list.get(arg1).gameClass;
          AndroidLauncher.landscape=list.get(arg1).landscape;
          Intent it=new Intent(vi.getContext(),AndroidLauncher.class);
          vi.getContext().startActivity(it);
        });
      TextView de=arg0.tv.findViewById(R.id.description);
      de.setText(list.get(arg1).description);
      ImageView im=arg0.tv.findViewById(R.id.itemImageView);
      if(list.get(arg1).drawable!=null) im.setImageBitmap(list.get(arg1).drawable);
    }

    @Override
    public int getItemCount() {
      return list.size();
    }

    public OnItem getOnItem() {
      return this.onItem;
    }

    public void setOnItem(OnItem onItem) {
      this.onItem=onItem;
    }
  }
}
