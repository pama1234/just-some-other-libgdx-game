package hhs.game.sort.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.games.collection.AndroidLauncher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import hhs.game.sort.MyInputListener;
import hhs.game.sort.SortingAlgorithm;

public class ChooseScreen extends ScreenAdapter{

  MyGame game;
  private Table table;

  private ScrollPane scroll;

  private Stage ui;
  SpriteBatch batch;

  int num=-1,rowNum=5;
  int size,wait;

  public ChooseScreen(MyGame g) {
    game=g;
    batch=g.batch;
    ScrollPane.ScrollPaneStyle style=new ScrollPane.ScrollPaneStyle(null,g.ttd(g.ttool.createTexture(200,10,Color.GRAY)),g.ttd(g.ttool.createTexture(200,10,Color.GRAY)),null,null);

    table=new Table();
    table.setFillParent(true);
    table.center().top();

    scroll=new ScrollPane(table,style);
    scroll.setFillParent(true);
    scroll.setSmoothScrolling(true);
    scroll.setForceScroll(false,true);

    ui=new Stage();
    ui.addActor(scroll);

    addButton("冒泡排序",new SortingAlgorithm() {
      @Override
      public void run() {
        bubbleSort();
      }
    });
    addButton("鸡尾酒排序",new SortingAlgorithm() {
      @Override
      public void run() {
        cocatailSort();
      }
    });
    addButton("选择排序",new SortingAlgorithm() {
      @Override
      public void run() {
        selectionSort();
      }
    });
    addButton("插入排序",new SortingAlgorithm() {
      @Override
      public void run() {
        insertSort();
      }
    });
    addButton("希尔排序",new SortingAlgorithm() {
      @Override
      public void run() {
        shellSort();
      }
    });
    addButton("归并排序",new SortingAlgorithm() {
      @Override
      public void run() {
        mergeSort();
      }
    });
    addButton("快速排序",new SortingAlgorithm() {
      @Override
      public void run() {
        quickSort();
      }
    });
    addButton("堆排序",new SortingAlgorithm() {
      @Override
      public void run() {
        heapSort();
      }
    });
    addButton("计数排序",new SortingAlgorithm() {
      @Override
      public void run() {
        countingSort();
      }
    });
    addButton("基数排序",new SortingAlgorithm() {
      @Override
      public void run() {
        radixSort();
      }
    });
    addButton("桶排序",new SortingAlgorithm() {
      @Override
      public void run() {
        bucketSort();
      }
    });
    //		for (int i = 0; i < 100; i++) {
    //			addButton("button" + i, new SortingAlgorithm(){
    //					@Override
    //					public void run() {
    //					}				
    //			});
    //		}
  }
  public void addButton(final String str,final SortingAlgorithm sort) {
    num++;
    TextButton tb=new TextButton(str,game.text);
    tb.addListener(new MyInputListener() {
      @Override
      public void touchUp(InputEvent event,float x,float y,int pointer,int button) {
        final Context c=AndroidLauncher.activity;
        final LinearLayout layout=new LinearLayout(c);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT);

        //设置为纵向布局
        layout.setOrientation(LinearLayout.VERTICAL);
        //设置layout大小
        layout.setLayoutParams(layoutParams);

        final EditText et=new EditText(c);
        et.setHint("设置数组大小");
        final EditText et2=new EditText(c);
        et2.setHint("设置运行延迟(延迟至少大于0，越大运行越慢)");

        layout.addView(et);
        layout.addView(et2);

        AndroidLauncher.handle.post(new Runnable() {
          @Override
          public void run() {
            AlertDialog dialog=new AlertDialog.Builder(AndroidLauncher.activity)
              .setTitle("设置模拟数组大小")
              .setView(layout)
              .setPositiveButton("使用默认数值",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,int which) {
                  Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                      ViewSortScreen vss;
                      game.setScreen(vss=new ViewSortScreen(game,sort,200));
                      vss.setWaitTime(5);
                    }
                  });
                }
              })
              .setNegativeButton("确定",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,int which) {
                  try {
                    size=Integer.valueOf(et.getText().toString());
                  }catch(NumberFormatException e) {
                    Toast.makeText(c,"您可能没有设置数组大小，将采用默认数值",Toast.LENGTH_SHORT).show();
                    size=200;
                  }
                  try {
                    wait=Integer.valueOf(et2.getText().toString());
                  }catch(NumberFormatException e) {
                    Toast.makeText(c,"您可能没有设置运行延迟，将采用默认数值",Toast.LENGTH_SHORT).show();
                    wait=5;
                  }
                  if(size>Gdx.graphics.getWidth()*0.8) {
                    Toast.makeText(c,"警告,模拟数组过大可能会丢失细节",Toast.LENGTH_LONG).show();
                  }
                  Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                      ViewSortScreen vss=new ViewSortScreen(game,sort,size);
                      vss.setWaitTime(wait);
                      game.setScreen(vss);

                    }
                  });
                }
              })
              .create();
            dialog.show();

          }
        });
      }

    });
    if(num%rowNum==0) {
      table.row().padTop(50);
    }
    table.add(tb).padRight(50);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(ui);
  }

  @Override
  public void render(float delta) {
    batch.begin();
    batch.end();

    ui.act();
    ui.draw();
  }

  @Override
  public void dispose() {
    ui.dispose();
  }

}
