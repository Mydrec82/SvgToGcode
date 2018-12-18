package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class UniversalRectangle implements Figure {
    private List<PointLine> points;
    private double r;

    /**
     *
     * @param points input String List four point rectangle
     * @param r inner radius hole when will be not fill
     *
     */

    UniversalRectangle(List<PointLine> points, double r){
        points.add(points.size(), points.get(0));
        this.points = points;
        this.r = r;
    }
    @Override
    public String create() {
        StringBuilder out = new StringBuilder();
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserTravel());
        out.append("\nG1 X").append(points.get(0).getX()).append(" Y").append(points.get(0).getY());
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserWork());
        out.append("\nM106 S").append(LaserStrong.getInstance().getLaserPower());
        for (PointLine point : points) {
            out.append("\nG1 X").append(point.getX()).append(" Y").append(point.getY());
        }
        out.append("\nM107");
        return out.toString();
    }

    @Override
    public String fill() {
        StringBuilder out = new StringBuilder();
        out.append(create());

        double s;
        double nx = Math.abs(points.get(0).getX() - points.get(1).getX());
        double ny = Math.abs(points.get(0).getY() - points.get(1).getY());
        double px = Math.abs(points.get(1).getX() - points.get(2).getX());
        double py = Math.abs(points.get(1).getY() - points.get(2).getY());

        if(nx > 0){
            if(px > 0) s = Math.min(nx, px);
            else s = Math.min(nx, py);
        }
        else{
            if(px > 0) s = Math.min(ny, px);
            else s = Math.min(ny, py);
        }


        while(s > (2 * r)){
            getInnerPoint();
            out.append(create());
            s -= Steep.getSteep() * 2;
            s = threPoint(s);
        }
     return out.toString();
    }




    private void getInnerPoint(){
        PointLine line,finalPoint;
        String fist = new Direction(points.get(0), points.get(1)).getDirection();
        String sn, s;

        for(int a = 0; a < points.size() - 1 ; a++){
            s = new Direction(points.get(a), points.get(a + 1)).getDirection();

            try {
               sn = new Direction(points.get(a + 1), points.get(a + 2)).getDirection();
            }catch (IndexOutOfBoundsException e){
               sn = fist;
            }

            line  =  setXorY(s, a);
            finalPoint = setXorY(sn, a);
            if(line.getX() == 0) line.setX(finalPoint.getX());
            else line.setY(finalPoint.getY());

            points.set(a, line);
        }
        points.set(points.size() - 1, points.get(0));
    }




    private PointLine setXorY(String direction, int a){
        PointLine line = new PointLine(0, 0);
        switch(direction){
            case"right":{
                line.setX(threPoint(points.get(a).getX() + Steep.getSteep()));
            }break;
            case"left":{
                line.setX(threPoint(points.get(a).getX() - Steep.getSteep()));
            }break;
            case"up":{
                line.setY(threPoint(points.get(a).getY() + Steep.getSteep()));
            }break;
            case"down":{
                line.setY(threPoint(points.get(a).getY() - Steep.getSteep()));
            }break;

            default: System.out.println("Error in switch " + getClass());
        }
        return line;

    }

    private double threPoint(double a){
        return new BigDecimal(a).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }
}


