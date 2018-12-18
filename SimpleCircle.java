package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

//создает множество точек на окружности и сдвигает начало на angle градусов по часовой
// create manyes points from circle and turn start in to angle
class SimpleCircle {
    private double xc;
    private double yc;
    private double r;
    private int sdvig;

    SimpleCircle(double xc, double yc, double r, int sdvig){
        this.sdvig = sdvig;
        this.xc = xc;
        this.yc = yc;
        if(r < 10)this.r = r;
        else {
            System.out.println(getClass()+ " r >= 10 its wrong\n default r < 9 Now 5.");
            this.r = 5;
        }
    }
    ArrayList<PointLine> getPoints(){
        ArrayList<PointLine> points = new ArrayList<>();

         double angle = 180 / ((r * 2) / Steep.getSteep());
         int sdvigLocal = 0;
         for (int a = 0; a < 360; a += angle) {
             double x = r * StrictMath.cos(StrictMath.toRadians(a)) + xc;
             x = new BigDecimal(x).setScale(3, RoundingMode.HALF_UP).doubleValue();
             double y = r * StrictMath.sin(StrictMath.toRadians(a)) + yc;
             y = new BigDecimal(y).setScale(3, RoundingMode.HALF_UP).doubleValue();
             points.add(new PointLine(x, y));
             if(a < sdvig){
                 sdvigLocal++;
             }
         }
         Collections.rotate(points, sdvigLocal);

         return points;
    }
}
