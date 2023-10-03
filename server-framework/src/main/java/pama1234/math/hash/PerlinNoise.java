package pama1234.math.hash;

public class PerlinNoise{
  public final class ImprovedNoise{
    public static float noise(float x,float y,float z) {
      int X=(int)Math.floor(x)&255, // FIND UNIT CUBE THAT
        Y=(int)Math.floor(y)&255, // CONTAINS POINT.
        Z=(int)Math.floor(z)&255;
      x-=Math.floor(x); // FIND RELATIVE X,Y,Z
      y-=Math.floor(y); // OF POINT IN CUBE.
      z-=Math.floor(z);
      float u=fade(x), // COMPUTE FADE CURVES
        v=fade(y), // FOR EACH OF X,Y,Z.
        w=fade(z);
      int A=p[X]+Y,AA=p[A]+Z,AB=p[A+1]+Z, // HASH COORDINATES OF
        B=p[X+1]+Y,BA=p[B]+Z,BB=p[B+1]+Z; // THE 8 CUBE CORNERS,
      return lerp(w,lerp(v,lerp(u,grad(p[AA],x,y,z), // AND ADD
        grad(p[BA],x-1,y,z)), // BLENDED
        lerp(u,grad(p[AB],x,y-1,z), // RESULTS
          grad(p[BB],x-1,y-1,z))),// FROM  8
        lerp(v,lerp(u,grad(p[AA+1],x,y,z-1), // CORNERS
          grad(p[BA+1],x-1,y,z-1)), // OF CUBE
          lerp(u,grad(p[AB+1],x,y-1,z-1),
            grad(p[BB+1],x-1,y-1,z-1))));
    }
    static float fade(float t) {
      return t*t*t*(t*(t*6-15)+10);
    }
    static float lerp(float t,float a,float b) {
      return a+t*(b-a);
    }
    static float grad(int hash,float x,float y,float z) {
      int h=hash&15; // CONVERT LO 4 BITS OF HASH CODE
      float u=h<8?x:y, // INTO 12 GRADIENT DIRECTIONS.
        v=h<4?y:h==12||h==14?x:z;
      return ((h&1)==0?u:-u)+((h&2)==0?v:-v);
    }
    static final int p[]=new int[512],permutation[]= {151,160,137,91,90,15,
      131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,
      190,6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
      88,237,149,56,87,174,20,125,136,171,168,68,175,74,165,71,134,139,48,27,166,
      77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
      102,143,54,65,25,63,161,1,216,80,73,209,76,132,187,208,89,18,169,200,196,
      135,130,116,188,159,86,164,100,109,198,173,186,3,64,52,217,226,250,124,123,
      5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
      223,183,170,213,119,248,152,2,44,154,163,70,221,153,101,155,167,43,172,9,
      129,22,39,253,19,98,108,110,79,113,224,232,178,185,112,104,218,246,97,228,
      251,34,242,193,238,210,144,12,191,179,162,241,81,51,145,235,249,14,239,107,
      49,192,214,31,181,199,106,157,184,84,204,176,115,121,50,45,127,4,150,254,
      138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180
    };
    static {
      for(int i=0;i<256;i++) p[256+i]=p[i]=permutation[i];
    }
  }
  // JAVA REFERENCE IMPLEMENTATION OF IMPROVED NOISE IN 4D - COPYRIGHT 2002 KEN PERLIN.
  public final class ImprovedNoise4D{
    public static float noise(float x,float y,float z,float w) {
      int X=(int)Math.floor(x)&255, // FIND UNIT HYPERCUBE
        Y=(int)Math.floor(y)&255, // THAT CONTAINS POINT.
        Z=(int)Math.floor(z)&255,
        W=(int)Math.floor(w)&255;
      x-=Math.floor(x); // FIND RELATIVE X,Y,Z,W
      y-=Math.floor(y); // OF POINT IN CUBE.
      z-=Math.floor(z);
      w-=Math.floor(w);
      float a=fade(x), // COMPUTE FADE CURVES
        b=fade(y), // FOR EACH OF X,Y,Z,W.
        c=fade(z),
        d=fade(w);
      int A=p[X]+Y,AA=p[A]+Z,AB=p[A+1]+Z, // HASH COORDINATES OF
        B=p[X+1]+Y,BA=p[B]+Z,BB=p[B+1]+Z, // THE 16 CORNERS OF
        AAA=p[AA]+W,AAB=p[AA+1]+W, // THE HYPERCUBE.
        ABA=p[AB]+W,ABB=p[AB+1]+W,
        BAA=p[BA]+W,BAB=p[BA+1]+W,
        BBA=p[BB]+W,BBB=p[BB+1]+W;
      return lerp(d, // INTERPOLATE DOWN.
        lerp(c,lerp(b,lerp(a,grad(p[AAA],x,y,z,w),
          grad(p[BAA],x-1,y,z,w)),
          lerp(a,grad(p[ABA],x,y-1,z,w),
            grad(p[BBA],x-1,y-1,z,w))),
          lerp(b,lerp(a,grad(p[AAB],x,y,z-1,w),
            grad(p[BAB],x-1,y,z-1,w)),
            lerp(a,grad(p[ABB],x,y-1,z-1,w),
              grad(p[BBB],x-1,y-1,z-1,w)))),
        lerp(c,lerp(b,lerp(a,grad(p[AAA+1],x,y,z,w-1),
          grad(p[BAA+1],x-1,y,z,w-1)),
          lerp(a,grad(p[ABA+1],x,y-1,z,w-1),
            grad(p[BBA+1],x-1,y-1,z,w-1))),
          lerp(b,lerp(a,grad(p[AAB+1],x,y,z-1,w-1),
            grad(p[BAB+1],x-1,y,z-1,w-1)),
            lerp(a,grad(p[ABB+1],x,y-1,z-1,w-1),
              grad(p[BBB+1],x-1,y-1,z-1,w-1)))));
    }
    static float fade(float t) {
      return t*t*t*(t*(t*6-15)+10);
    }
    static float lerp(float t,float a,float b) {
      return a+t*(b-a);
    }
    static float grad(int hash,float x,float y,float z,float w) {
      int h=hash&31; // CONVERT LO 5 BITS OF HASH TO 32 GRAD DIRECTIONS.
      float a=y,b=z,c=w; // X,Y,Z
      switch(h>>3) { // OR, DEPENDING ON HIGH ORDER 2 BITS:
        case 1:
          a=w;
          b=x;
          c=y;
          break; // W,X,Y
        case 2:
          a=z;
          b=w;
          c=x;
          break; // Z,W,X
        case 3:
          a=y;
          b=z;
          c=w;
          break; // Y,Z,W
      }
      return ((h&4)==0?-a:a)+((h&2)==0?-b:b)+((h&1)==0?-c:c);
    }
    static final int p[]=new int[512],permutation[]= {151,160,137,91,90,15,
      131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,
      190,6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
      88,237,149,56,87,174,20,125,136,171,168,68,175,74,165,71,134,139,48,27,166,
      77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
      102,143,54,65,25,63,161,1,216,80,73,209,76,132,187,208,89,18,169,200,196,
      135,130,116,188,159,86,164,100,109,198,173,186,3,64,52,217,226,250,124,123,
      5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
      223,183,170,213,119,248,152,2,44,154,163,70,221,153,101,155,167,43,172,9,
      129,22,39,253,19,98,108,110,79,113,224,232,178,185,112,104,218,246,97,228,
      251,34,242,193,238,210,144,12,191,179,162,241,81,51,145,235,249,14,239,107,
      49,192,214,31,181,199,106,157,184,84,204,176,115,121,50,45,127,4,150,254,
      138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180
    };
    static {
      for(int i=0;i<256;i++) p[256+i]=p[i]=permutation[i];
    }
  }
}
