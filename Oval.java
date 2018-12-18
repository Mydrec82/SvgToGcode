package com.company;
import java.util.List;

/*
this class create oval
 */
public class Oval implements Figure {
   private List<PointLine>left;
   private List<PointLine>right;

    Oval(PointLine pointA, PointLine pointB, double stroke, int angle){
       getAllPoints(pointA, pointB, stroke, angle);
    }


    @Override
    public String create() {
        StringBuilder out = new StringBuilder();
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserTravel());
        out.append("\nG1 X").append(left.get(0).getX()).append(" Y").append(left.get(1).getY());
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserWork());
        out.append("\nM106 S").append(LaserStrong.getInstance().getLaserPower());

        int t = right.size() - 1;
        int b = t;

        for (int a = 0; a <= b; a++) {
//            PointLine pointA = new PointLine(left.get(a).getX(), left.get(a).getY());
            out.append("\nG1 X").append(left.get(a).getX()).append(" Y").append(left.get(a).getY());
            b =  t - a;
        }
        for(;b < right.size(); b++){
//            PointLine pointB = new PointLine(right.get(b).getX(), right.get(b).getY());
            out.append("\nG1 X").append(right.get(b).getX()).append(" Y").append(right.get(b).getY());
        }
        out.append("\nG1 X").append(left.get(0).getX()).append(" Y").append(left.get(1).getY());
        out.append("\nM107");
        return out.toString();
    }

    @Override
    public String fill() {
        StringBuilder out = new StringBuilder();
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserTravel());
        out.append("\nG1 X").append(left.get(0).getX()).append(" Y").append(left.get(1).getY());
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserWork());
        out.append("\nM106 S").append(LaserStrong.getInstance().getLaserPower());
        int s = 0;
        int t = right.size() - 1;
        int m = t;

        for (int a = 0; a < m; a++) {
            m = t - a;
//            PointLine pointA = new PointLine(left.get(a).getX(), left.get(a).getY());
            PointLine pointA = left.get(a);
//            PointLine pointB = new PointLine(right.get(m).getX(), right.get(m).getY());
            PointLine pointB =  right.get(m);
            out.append("\nG1 X").append(pointA.getX()).append(" Y").append(pointA.getY());
            out.append("\nG1 X").append(pointB.getX()).append(" Y").append(pointB.getY());
        }

        out.append("\nM107");
        return out.toString();
    }



    private void getAllPoints (PointLine pointA, PointLine pointB, double stroke, int angle){
        double rs = stroke / 2;
        left = new SimpleCircle(pointA.getX(), pointA.getY(), rs, angle).getPoints();
        right = new SimpleCircle(pointB.getX(), pointB.getY(), rs, angle).getPoints();


    }
}
