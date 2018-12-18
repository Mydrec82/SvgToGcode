package com.company;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class Circle implements Figure {
    private int laserWorkSpeed = LaserSpeed.getInstance().getLaserWork();

    private int laserPower = LaserStrong.getInstance().getLaserPower();
    private double radius;
    private double centerX;
    private double centerY;
    private double radiusInner;
    private double step = Steep.getSteep();

    /**
     * fist constructor
     * @param radius radius circle
     * @param centerX x coordinate center circle
     * @param centerY y coordinate center circle
     */
    Circle(double centerX, double centerY, double radius){
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;

    }

    /**
     * second constuctor if use function fill
     * @param radius radiusOut
     * @param centerX x coordinate centre circle
     * @param centerY y coordinate centre circle
     * @param radiusInner if 0 full fill
     */
    Circle(double centerX, double centerY, double radius, double radiusInner){
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radiusInner = radiusInner;
    }

    //create circle from line. All in mm
    public String create(){
        StringBuilder out = new StringBuilder();
        int angle;

        if(radius >= 10) angle = 1;
        else if (radius > 2 && radius < 10) angle = 15;
        else angle = 30;

            for (int a = 0; a <= 360; a += angle) {
                double x = radius * StrictMath.cos(StrictMath.toRadians(a)) + centerX;
                x = new BigDecimal(x).setScale(3, RoundingMode.HALF_UP).doubleValue(); //only 4 digits after point
                double y = radius * StrictMath.sin(StrictMath.toRadians(a)) + centerY;
                y = new BigDecimal(y).setScale(3, RoundingMode.HALF_UP).doubleValue(); //only 4 digits after point
                if (a == 0) {
                    out.append("\nG1 X").append(x).append(" Y").append(y).append(" F").append(LaserSpeed.getInstance().getLaserTravel());
                    out.append("\nM106 " + "S" + laserPower);
                    out.append("\nG1 F"+ laserWorkSpeed);
                }
                out.append("\nG1 X" + x + " Y" + y);
            }
            out.append("\nM107");
      return out.toString();

   }

    public String fill() {
        StringBuilder out = new StringBuilder();
        while (radius >= radiusInner) {
            out.append(new Circle(centerX, centerY, radius).create());
            radius -= step;
        }
        return out.toString();
    }
}