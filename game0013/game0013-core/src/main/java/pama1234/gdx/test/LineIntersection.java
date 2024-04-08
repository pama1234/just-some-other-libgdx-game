package pama1234.gdx.test;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class LineIntersection{
  public static void main(String[] args) {
    // 定义三个vec3f坐标
    Vector3f A=new Vector3f(1.0f,0.0f,0.0f); // 直线上的第一个点
    Vector3f B=new Vector3f(0.0f,1.0f,0.0f); // 直线上的第二个点
    Vector3f P=new Vector3f(2.0f,2.0f,0.0f); // 直线外的点

    // 计算AB的方向
    Vector3f AB=new Vector3f();
    AB.sub(B,A);
    AB.normalize();

    // 计算AP的方向
    Vector3f AP=new Vector3f();
    AP.sub(P,A);

    // 计算AP在AB上的投影长度（即最短距离）
    float projectionLength=AP.dot(AB);

    // 计算垂足点Q
    Vector3f Q=new Vector3f();
    Q.scaleAdd(projectionLength,AB,A);

    // 计算垂直于AB的直线方向
    Vector3f perpendicular=new Vector3f();
    perpendicular.cross(AP,AB);
    perpendicular.normalize();

    // 构造通过点P且垂直于AB的直线方程
    // Q + t * perpendicular
    // 由于AB的直线方程是 A + s * AB，我们需要找到t和s使得两个方程的x, y, z分别相等

    // 这是一个线性方程组，可以解出t和s
    // 但在这个特殊情况下，我们只需要找到交点，而不需要知道t和s的具体值
    // 因为我们已知Q和P在垂直于AB的直线上，所以交点就是Q

    // 输出结果
    // 0.5,0.5,0
    System.out.println("The intersection point is: "+Q);
  }
  public static class PointPosition{
    public static void main(String[] args) {
      // 定义线段的起点和终点
      Point3d startPoint=new Point3d(0,0,0);
      Point3d endPoint=new Point3d(0,0,1);

      // 定义待判断的点
      Point3d pointToCheck=new Point3d(1,0,0);

      // 计算线段向量
      Vector3d lineVector=new Vector3d();
      lineVector.sub(endPoint,startPoint);

      // 计算线段起点到待判断点的向量
      Vector3d pointVector=new Vector3d();
      pointVector.sub(pointToCheck,startPoint);

      // 计算线段向量与点向量的叉乘
      Vector3d crossProduct=new Vector3d();
      crossProduct.cross(lineVector,pointVector);

      // 判断点在线段的左侧还是右侧
      if(crossProduct.z>0) {
        System.out.println("Point is on the left side of the line segment.");
      }else if(crossProduct.z<0) {
        System.out.println("Point is on the right side of the line segment.");
      }else {
        System.out.println("Point is on the line segment.");
      }
    }
  }
}