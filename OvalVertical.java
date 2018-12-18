package com.company;

import java.util.List;

public class OvalVertical implements Figure {
    private List<PointLine> downOut;
    private List<PointLine> upOut;
    private List<PointLine> downIn;
    private List<PointLine> upIn;

    OvalVertical(PointLine pointA, PointLine pointB, int angle, double stroke, double cx, double cy, double r){
        r *= 2;
        if(angle == 90 ) {
            downOut = new PointsPoluOval().getDownPoint(pointA, stroke);
            upOut = new PointsPoluOval().getUpPoint(pointB, stroke);
            downIn = new PointsPoluOval().getDownPoint(new PointLine(cx, cy), r);
            upIn = new PointsPoluOval().getUpPoint(new PointLine(cx, cy), r);
        }else{
            upOut = new PointsPoluOval().getDownPoint(pointA, stroke);
            downOut = new PointsPoluOval().getUpPoint(pointB, stroke);
            upIn = new PointsPoluOval().getDownPoint(new PointLine(cx, cy), r);
            downIn = new PointsPoluOval().getUpPoint(new PointLine(cx, cy), r);
        }


    }
    @Override
    public String create() {
        StringBuilder out = new StringBuilder();
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserTravel());
        out.append("\nG1 X").append(downOut.get(0).getX()).append(" Y").append(downOut.get(1).getY());
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserWork());
        out.append("\nM106 S").append(LaserStrong.getInstance().getLaserPower());
        for(PointLine p : downOut){
            out.append("\nG1 X").append(p.getX()).append(" Y").append(p.getY());
        }for(int p = upOut.size()- 1; p >= 0; p--){
            out.append("\nG1 X").append(upOut.get(p).getX()).append(" Y").append(upOut.get(p).getY());
        }
        out.append("\nG1 X").append(downOut.get(0).getX()).append(" Y").append(downOut.get(1).getY());
        out.append("\nM107");
        return  out.toString();
    }

    @Override
    public String fill() {
        StringBuilder out = new StringBuilder();
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserTravel());
        out.append("\nG1 X").append(downOut.get(0).getX()).append(" Y").append(downOut.get(1).getY());
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserWork());
        out.append("\nM106 S").append(LaserStrong.getInstance().getLaserPower());

        int s = 0;
        int t = 0;
        for(int a = 0; a < downOut.size(); a++){
            out.append("\nG1 X").append(downOut.get(a).getX()).append(" Y").append(downOut.get(a).getY());
            if(downOut.get(a).getX() > downIn.get(0).getX() && downOut.get(a).getX() < downIn.get(downIn.size()- 1).getX()){
                out.append("\nG1 X").append(downIn.get(s).getX()).append(" Y").append(downIn.get(s).getY());
                s++;
                t = a;
            }else out.append("\nG1 X").append(upOut.get(a).getX()).append(" Y").append(upOut.get(a).getY());
        }
        for(int a = upIn.size() - 1; a >= 0; a-- ){
            out.append("\nG1 X").append(upIn.get(a).getX()).append(" Y").append(upIn.get(a).getY());
            out.append("\nG1 X").append(upOut.get(t).getX()).append(" Y").append(upOut.get(t).getY());
            t--;
        }
        out.append(create());

        return out.toString();
    }
}
