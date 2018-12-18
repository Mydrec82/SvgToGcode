package com.company;


import java.math.BigDecimal;
import java.math.RoundingMode;

@Deprecated

public class Rectangle implements Figure {
    private double startX;
    private double startY;
    private double width;
    private double height;
    private int speedWork = LaserSpeed.getInstance().getLaserWork();
    private int speedTravel = LaserSpeed.getInstance().getLaserTravel();
    private double widthInner;
    private double heightInner;
    private double steep = Steep.getSteep();
    private int laserPower = LaserStrong.getInstance().getLaserPower();



    public Rectangle(double startX, double startY, double width, double height) {
        this.startX = threePoint(startX);
        this.startY = threePoint(startY);
        this.width = width;
        this.height = height;
    }
    public Rectangle(double startX, double startY, double width, double height, double widthInner, double heightInner) {
        this.startX = threePoint(startX);
        this.startY = threePoint(startY);
        this.width = width;
        this.height = height;
        this.widthInner = widthInner;
        this.heightInner = heightInner;
    }


    @Override
    public String create() {
        StringBuilder out = new StringBuilder();

        out.append("\nG1 X").append(startX).append(" Y").append(startY).append(" F").append(speedTravel);
        out.append("\nM106 ").append("S").append(laserPower);
        out.append("\nG1 X").append(startX).append(" Y").append(startY + height).append(" F").append(speedWork);
        out.append("\nG1 X").append(startX + width).append(" Y").append(startY + height).append(" F").append(speedWork);
        out.append("\nG1 X").append(startX + width).append(" Y").append(startY).append(" F").append(speedWork);
        out.append("\nG1 X").append(startX).append(" Y").append(startY).append(" F").append(speedWork);
        out.append("\nM107");

        return out.toString();
    }

    @Override
    public String fill() {
        StringBuilder out = new StringBuilder();
        while(width >= widthInner | height >= heightInner) {
            out.append(new Rectangle(startX, startY, width, height).create());
            startX += steep;
            startY += steep;
            width -= (2*steep);
            height -= (2*steep);
        }
        return out.toString();
    }
    private double threePoint(double val){
        val = new BigDecimal(val).setScale(3, RoundingMode.HALF_UP).doubleValue();
        return val;
    }
}
