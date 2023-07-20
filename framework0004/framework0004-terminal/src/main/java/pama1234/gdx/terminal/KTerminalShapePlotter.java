package pama1234.gdx.terminal;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import pama1234.util.function.ExecuteFunction;

public final class KTerminalShapePlotter{
   @NotNull
   public static final KTerminalShapePlotter INSTANCE;
   @NotNull
   public final List plotRect(int x,int y,int width,int height) {
      List output=(List)(new ArrayList());
      int i=y;
      for(int var7=y+height;i<var7;++i) {
         int j=x;
         for(int var9=x+width;j<var9;++j) {
            if(i==y||i==y+height-1||j==x||j==x+width-1) {
               output.add(new Pair(j,i));
            }
         }
      }
      return output;
   }
   @NotNull
   public final List plotLine(int startX,int startY,int endX,int endY) {
      final List output=Arrays.asList(new Pair[] {new Pair(startX,startY),new Pair(endX,endY)});
      var $fun$plot$1=new WithObjectFunction() {
         // $FF: synthetic method
         // $FF: bridge method
         public Object invoke(Object var1,Object var2,Object var3,Object var4,Object var5) {
            this.invoke(((Number)var1).intValue(),((Number)var2).intValue(),((Number)var3).intValue(),((Number)var4).intValue(),(Boolean)var5);
            return Unit.INSTANCE;
         }
         public final void invoke(int x0,int y0,int x1,int y1,final boolean isHigh) {
            int dx=x1-x0;
            int dy=y1-y0;
            var $fun$decideDirection$1=null.INSTANCE;
            int var10001;
            if(isHigh) {
               var10001=dx;
            }else {
               if(isHigh) {
                  throw new NoWhenBranchMatchedException();
               }
               var10001=dy;
            }
            final int direction=$fun$decideDirection$1.invoke(var10001);
            if(isHigh) {
               dx*=direction;
            }else if(!isHigh) {
               dy*=direction;
            }
            var $fun$getDifference$2=new WithObjectFunction() {
               // $FF: synthetic method
               // $FF: bridge method
               public Object invoke(Object var1,Object var2) {
                  return this.invoke(((Number)var1).intValue(),((Number)var2).intValue());
               }
               public final int invoke(int x,int y) {
                  boolean var3=isHigh;
                  int var10000;
                  if(var3) {
                     var10000=2*x-y;
                  }else {
                     if(var3) {
                        // throw new NoWhenBranchMatchedException();
                     }
                     var10000=2*y-x;
                  }
                  return var10000;
               }
            };
            // final Ref.IntRef difference=new Ref.IntRef();
            var difference=$fun$getDifference$2.invoke(dx,dy);
            // final Ref.IntRef temp=new Ref.IntRef();
            if(isHigh) {
               var10001=x0;
            }else {
               if(isHigh) {
                  // throw new NoWhenBranchMatchedException();
               }
               var10001=y0;
            }
            var temp=var10001;
            var $fun$offset$3=new ExecuteFunction() {
               // $FF: synthetic method
               // $FF: bridge method
               public Object invoke(Object var1) {
                  this.invoke(((Number)var1).intValue());
                  return Unit.INSTANCE;
               }
               public final void invoke(int value) {
                  if(difference.element>0) {
                     Ref.IntRef var10000=temp;
                     var10000.element+=direction;
                     var10000=difference;
                     var10000.element-=2*value;
                  }
               }
            };
            int x;
            int var16;
            if(isHigh) {
               x=y0;
               for(var16=y1;x<var16;++x) {
                  output.add(new Pair(temp.element,x));
                  $fun$offset$3.invoke(dy);
                  difference.element+=2*dx;
               }
            }else if(!isHigh) {
               x=x0;
               for(var16=x1;x<var16;++x) {
                  output.add(new Pair(x,temp.element));
                  $fun$offset$3.invoke(dx);
                  difference.element+=2*dy;
               }
            }
         }
      };
      if(Math.abs(endY-startY)<Math.abs(endX-startX)) {
         if(startX>endX) {
            $fun$plot$1.invoke(endX,endY,startX,startY,false);
         }else {
            $fun$plot$1.invoke(startX,startY,endX,endY,false);
         }
      }else if(startY>endY) {
         $fun$plot$1.invoke(endX,endY,startX,startY,true);
      }else {
         $fun$plot$1.invoke(startX,startY,endX,endY,true);
      }
      return CollectionsKt.toList((Iterable)output);
   }
   @NotNull
   public final List plotEllipse(int startX,int startY,int endX,int endY) {
      List output=(List)(new ArrayList());
      int x0=startX-1;
      int y0=startY-1;
      int x1=endX-2;
      int y1=endY-2;
      int b=x1-x0;
      int a=Math.abs(b);
      int b1=y1-y0;
      b=Math.abs(b1);
      b1=b&1;
      long dx=(long)(4*(1-a)*b*b);
      long dy=(long)(4*(b1+1)*a*a);
      long err=dx+dy+(long)(b1*a*a);
      long e2=0L;
      if(x0>x1) {
         x0=x1;
         x1+=a;
      }
      if(y0>y1) {
         y0=y1;
      }
      y0+=(b+1)/2;
      y1=y0-b1;
      a*=8*a;
      b1=8*b*b;
      do {
         output.add(new Pair(x1,y0));
         output.add(new Pair(x0,y0));
         output.add(new Pair(x0,y1));
         output.add(new Pair(x1,y1));
         e2=(long)2*err;
         if(e2<=dy) {
            ++y0;
            --y1;
            dy+=(long)a;
            err+=dy;
         }
         if(e2>=dx||(long)2*err>dy) {
            ++x0;
            --x1;
            dx+=(long)b1;
            err+=dx;
         }
      }while(x0<=x1);
      while(y0-y1<b) {
         output.add(new Pair(x0-1,y0));
         Integer var10003=x1+1;
         Integer var10004=y0;
         ++y0;
         output.add(new Pair(var10003,var10004));
         output.add(new Pair(x0-1,y1));
         var10003=x1+1;
         var10004=y1;
         --y1;
         output.add(new Pair(var10003,var10004));
      }
      return output;
   }
   @NotNull
   public final List plotCircle(int centerX,int centerY,int radius) {
      List output=(List)(new ArrayList());
      int r=radius-1;
      int x=-r;
      int y=0;
      int err=2-2*r;
      do {
         output.add(new Pair(centerX-x-1,centerY+y-1));
         output.add(new Pair(centerX-y-1,centerY-x-1));
         output.add(new Pair(centerX+x-1,centerY-y-1));
         output.add(new Pair(centerX+y-1,centerY+x-1));
         if(err<=y) {
            ++y;
            err+=y*2+1;
         }
         if(err>x||err>y) {
            ++x;
            err+=x*2+1;
         }
      }while(x<0);
      return Arrays.asList((Iterable)output);
   }
   @NotNull
   public final List plotTriangle(int topX,int topY,int leftX,int leftY,int rightX,int rightY) {
      List output=(List)(new ArrayList());
      output.addAll((Collection)this.plotLine(topX,topY,leftX,leftY));
      output.addAll((Collection)this.plotLine(leftX,leftY,rightX,rightY));
      output.addAll((Collection)this.plotLine(rightX,rightY,topX,topY));
      return Arrays.asList((Iterable)output);
   }
   private KTerminalShapePlotter() {}
   static {
      KTerminalShapePlotter var0=new KTerminalShapePlotter();
      INSTANCE=var0;
   }
}
