package lab2;

import java.util.Scanner;

public class Lab1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Point3d[] Points = new Point3d[3];
        for (int i=0;i<3;i++){
            while (true) {
                int num = i+1;
                System.out.println("Введите координату " + num + "-й точки:");
                int z=0;
                Points[i] = new Point3d();
                System.out.print("x: ");
                Points[i].setX(sc.nextDouble());
                System.out.print("y: ");
                Points[i].setY(sc.nextDouble());
                System.out.print("z: ");
                Points[i].setZ(sc.nextDouble());
                for (int n=0;n<i;n++){
                    if (Points[i].eq(Points[n])){
                        z=1;
                    }
                }
                if (z==1){
                    System.out.println("ОШИБКА (точка уже существует)");
                    System.out.println("ВВЕДИТЕ КООРДИНАТУ ТОЧКИ ПОВТОРНО");
                } else {
                    break;
                }
            }
        }
        for (int i=0;i<3;i++) {
            int numer = i+1;
            System.out.println("Координата точки " + numer + ": (" + Points[i].getX() + "; " + Points[i].getY() + "; " + Points[i].getZ() + ")");
        }
        if (Point3d.computeArea(Points[0],Points[1],Points[2])>0){
            System.out.format("Площадь данного треугольнка = %.2f", Point3d.computeArea(Points[0],Points[1],Points[2]));
        } else {
            System.out.println("Данного треугольника не существует");
        }
    }
}