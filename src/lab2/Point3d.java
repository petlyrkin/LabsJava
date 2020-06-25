package lab2;

public class Point3d  extends Point2d{
    private double zCoord; // координата z

    public Point3d(double x, double y, double z) {  //Конструктор инициализации
        xCoord=x;
        yCoord=y;
        zCoord=z;
    }

    public Point3d() { //Конструктор по умолчанию .
        this(0, 0, 0);
    }


    public double getZ() { // возвращение координаты z
        return zCoord;
    }


    public void setZ(double val) { //Установка значения координаты Z
        zCoord = val;
    }


    public boolean eq(Point3d m) {
        return (xCoord==m.xCoord & yCoord==m.yCoord & zCoord==m.zCoord);
    }
    /** вычисление расстояниие между точками */
    public static double distanceTo(Point3d m, Point3d n) {
        return Math.sqrt(Math.pow((m.xCoord - n.xCoord), 2) + Math.pow((m.yCoord - n.yCoord), 2) + Math.pow((m.zCoord - n.zCoord), 2));
    }
    /** вычисление площади треугольника */
    public static double computeArea(Point3d a,Point3d b, Point3d c){
        double p1 = Point3d.distanceTo(a,b);
        double p2 = Point3d.distanceTo(b,c);
        double p3 = Point3d.distanceTo(a,c);
        double Pp = (p1+p2+p3)/2;
        return Math.sqrt(Pp*(Pp-p1)*(Pp-p2)*(Pp-p3));
    }
}