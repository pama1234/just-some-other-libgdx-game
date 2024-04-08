package hhs.game.lost.games.RandomScreen;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

public class TestMap{

  public float map[][];
  private int meteoNumber=200;
  public TestMap(Vector2 mapSize,float landHeight,boolean mainIsland,Vector2 meteoSize) {
    map=new float[(int)mapSize.x][(int)mapSize.y];

    float halfDiagLine=(float)Math.sqrt(Math.pow(mapSize.x/2,2)+Math.pow(mapSize.y/2,2));

    for(int i=0;i<mapSize.x;i++) {
      for(int j=0;j<mapSize.y;j++) {
        map[i][j]=landHeight;

        //越靠近中间的地方越高
        if(mainIsland==true) map[i][j]+=2*(1-Math.sqrt(Math.pow(mapSize.x/2-i,2)+Math.pow(mapSize.y/2-j,2))/halfDiagLine);

      }
    }
    Vector2 len=new Vector2(0.5f,10f);
    for(int count=0;count<meteoNumber;count++) {
      float valueRatio=(meteoSize.y-meteoSize.x)/(len.y-len.x);
      float randNum=MathUtils.random(len.x,len.y);
      float randRadius=(randNum-len.x)*valueRatio+meteoSize.x;

      float randX=MathUtils.random()*(mapSize.x+2*meteoSize.y)-meteoSize.y;
      float randY=MathUtils.random()*(mapSize.y+2*meteoSize.y)-meteoSize.y;

      for(int i=-(int)randRadius;i<=(int)randRadius;i++) for(int j=-(int)randRadius;j<=(int)randRadius;j++) {
        float x=i+randX;
        float y=j+randY;
        if(x<0||x>=mapSize.x||y<0||y>=mapSize.y||i*i+j*j>=Math.pow(randRadius,2)) continue;
        map[(int)x][(int)y]-=Math.sqrt(Math.pow(randRadius,2)-i*i-j*j);
      }
    }
  }
}

//    void Start () 
//    {
//        StartCoroutine(GenerateLand ());
//    }
//    //由两个协程组成,先初始化数据,再模拟陨石
//    IEnumerator GenerateLand()
//    {
//        yield return StartCoroutine(InitialLand ());
//        yield return StartCoroutine(MeteoriteCrash ());
//    }
//    //初始化数据
//    IEnumerator InitialLand()
//    {
//        map = new float[(int)mapSize.x][];
//        sprites = new SpriteRenderer[(int)mapSize.x][];
//        for (int i = 0; i < mapSize.x; i++) 
//        {
//            map [i] = new float[(int)mapSize.y];
//            sprites [i] = new SpriteRenderer[(int)mapSize.y];
//        }
//
//        float halfDiagLine = Math.sqrt (Math.pow (mapSize.x / 2, 2) + Math.pow (mapSize.y / 2, 2));
//
//        for (int i = 0; i < mapSize.x; i++)
//        {   for (int j = 0; j < mapSize.y; j++) 
//            {
//                map [i] [j] = landHeight;
//
//                //越靠近中间的地方越高
//                if (mainIsland == true)
//                    map [i] [j] += addHeight*(1-Math.sqrt (Math.pow (mapSize.x / 2 - i, 2) + Math.pow (mapSize.y / 2 - j, 2)) / halfDiagLine);
//
//                sprites [i] [j] = (Instantiate (landPrefab, new Vector2 (i, j), Quaternion.identity) as GameObject).GetComponent<SpriteRenderer> ();
//                //如果计算超过 5000 次,就等待
//                if ((i+1)*(j+1) % 5000 == 0)
//                    yield return null;
//            }   
//        }
//
//        yield return FlashMap (true);
//
//        yield return StartCoroutine(MeteoriteCrash ());
//    }
//    //模拟陨石撞击
//    IEnumerator MeteoriteCrash()
//    {
//        //越大的陨石出现的几率越低
//        //使用定义域为[0.5,10]的反比例函数来进行简单的概率分布
//        
//
//            yield return StartCoroutine (FlashMap (false,new Vector2 (randX - randRadius, randY + randRadius), new Vector2 (randX + randRadius, randY - randRadius)));
//        }
//
//    }
//    //根据地形高度,改变贴图,flashAll指全部刷新,否则刷新两个坐标中间的网格贴图
//    IEnumerator FlashMap(bool flashAll,Vector2 leftTop =  new Vector2(),Vector2 rightBottom = new Vector2())
//    {
//        //全部刷新
//        if (flashAll) 
//        {
//            leftTop = new Vector2 (0, mapSize.y-1);
//            rightBottom = new Vector2 (mapSize.x-1, 0);
//        }
//        //这里是小于等于,传入值的时候注意范围
//        for (int i = (int)leftTop.x; i <= (int)rightBottom.x; i++) 
//        {
//            for (int j = (int)rightBottom.y; j <= (int)leftTop.y; j++) 
//            {
//                if (i < 0 || j < 0 || i >= mapSize.x || j >= mapSize.y)
//                    continue;
//                if (map [i] [j] <= seaHeight)
//                    sprites [i] [j].sprite = water;
//                else
//                    sprites [i] [j].sprite = land;
//                //加深颜色,只改变了前两个维度,即只加深了蓝绿色(更像地球)
//                float blackValue = Math.Clamp((map [i] [j] - seaHeight) / 255+0.8f,0,1);
//                sprites [i] [j].color = new Color(blackValue,blackValue,1,1);
//                if ((i+1)*(j+1) % 5000 == 0)
//                    yield return null;
//            }
//        }
//        yield return null;
//    }
//	}
