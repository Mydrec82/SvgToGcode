package com.company;

import java.util.List;

public class OvalHorizontal implements Figure {
   private List<PointLine> leftOut;
   private List<PointLine> rightOut;
   private List<PointLine> leftIn;
   private List<PointLine> rightIn;

   OvalHorizontal(PointLine pointA, PointLine pointB,int angle, double stroke, double cx, double cy, double r){
       r *= 2;
       if(angle == 0 ) {
           leftOut = new PointsPoluOval().getLeftPoint(pointA, stroke);
           rightOut = new PointsPoluOval().getRightPoint(pointB, stroke);
           leftIn = new PointsPoluOval().getLeftPoint(new PointLine(cx, cy), r);
           rightIn = new PointsPoluOval().getRightPoint(new PointLine(cx, cy), r);
       }else{
           leftOut = new PointsPoluOval().getRightPoint(pointA, stroke);
           rightOut = new PointsPoluOval().getLeftPoint(pointB, stroke);
           leftIn = new PointsPoluOval().getRightPoint(new PointLine(cx, cy), r);
           rightIn = new PointsPoluOval().getLeftPoint(new PointLine(cx, cy), r);
       }

   }

    @Override
    public String create() {
        StringBuilder out = new StringBuilder();
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserTravel());
        out.append("\nG1 X").append(leftOut.get(0).getX()).append(" Y").append(leftOut.get(1).getY());
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserWork());
        out.append("\nM106 S").append(LaserStrong.getInstance().getLaserPower());

        for(PointLine p : leftOut){
             out.append("\nG1 X").append(p.getX()).append(" Y").append(p.getY());
        }for(int p = rightOut.size()- 1; p >= 0; p--){
             out.append("\nG1 X").append(rightOut.get(p).getX()).append(" Y").append(rightOut.get(p).getY());
        }
        out.append("\nG1 X").append(leftOut.get(0).getX()).append(" Y").append(leftOut.get(1).getY());
        out.append("\nM107");

        return out.toString();
    }

    @Override
    public String fill() {
       int s = 0;
       int t = 0;
       StringBuilder out = new StringBuilder();
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserTravel());
        out.append("\nG1 X").append(leftOut.get(0).getX()).append(" Y").append(leftOut.get(1).getY());
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserWork());
        out.append("\nM106 S").append(LaserStrong.getInstance().getLaserPower());
        for(int a = 0; a < leftOut.size(); a++){
            out.append("\nG1 X").append(leftOut.get(a).getX()).append(" Y").append(leftOut.get(a).getY());
            if(leftOut.get(a).getY() < leftIn.get(0).getY() && leftOut.get(a).getY() > leftIn.get(leftIn.size() - 1).getY()){
                out.append("\nG1 X").append(leftIn.get(s).getX()).append(" Y").append(leftIn.get(s).getY());
                s++;
                t = a;
            }else  out.append("\nG1 X").append(rightOut.get(a).getX()).append(" Y").append(rightOut.get(a).getY());
        }
        for(int a = rightIn.size() - 1; a >= 0; a-- ){
            out.append("\nG1 X").append(rightIn.get(a).getX()).append(" Y").append(rightIn.get(a).getY());
            out.append("\nG1 X").append(rightOut.get(t).getX()).append(" Y").append(rightOut.get(t).getY());
            t--;
        }
        out.append(create());
        return out.toString();
    }

}
