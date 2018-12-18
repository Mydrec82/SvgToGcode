package com.company;

import java.util.List;

public class Line implements Figure {
    List<Double> point;
    private double stroke;

    Line(List<Double> point, double stroke){
        this.point = point;
        this.stroke = stroke;
    }
    @Override
    public String create() {
        StringBuilder out = new StringBuilder();
        out.append("\nG1 X").append(point.get(0)).append(" Y").append(point.get(1)).append(" F").append(LaserSpeed.getInstance().getLaserTravel());
        out.append("\nM106").append(" S").append(LaserStrong.getInstance().getLaserPower());
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserWork());
        for(int a = 2; a < point.size(); a += 2){
            out.append("\nG1 X").append(point.get(a)).append(" Y").append(point.get(a + 1));
        }
        out.append("\nM107");
        return out.toString();
    }

    @Override
    public String fill() {
        StringBuilder out = new StringBuilder();
        out.append("\nG1 X").append(point.get(0)).append(" Y").append(point.get(1)).append(" F").append(LaserSpeed.getInstance().getLaserTravel());
        out.append("\nM106").append(" S").append(LaserStrong.getInstance().getLaserPower());
        out.append("\nG1 F").append(LaserSpeed.getInstance().getLaserWork());

        for(int a = 0; a < point.size()-2; a += 2) {
            PointLine pointLineA = new PointLine(point.get(a), point.get(a + 1));
            PointLine pointLineB = new PointLine(point.get(a + 2), point.get(a + 3));
            String direction = new Direction(pointLineA, pointLineB).getDirection();
            switch (direction){
                case"left": out.append(new Oval(pointLineA, pointLineB, stroke, 270).fill()); break;
                case"leftdown":out.append(new Oval(pointLineA, pointLineB, stroke, 315).fill()); break;
                case"down":out.append(new Oval(pointLineA, pointLineB, stroke, 0).fill()); break;
                case"rightdown":out.append(new Oval(pointLineA, pointLineB, stroke, 45).fill()); break;
                case"right":out.append(new Oval(pointLineA, pointLineB, stroke, 90).fill()); break;
                case"rightup":out.append(new Oval(pointLineA, pointLineB, stroke, 135).fill()); break;
                case"up":out.append(new Oval(pointLineA, pointLineB, stroke, 180).fill()); break;
                case"leftup":out.append(new Oval(pointLineA, pointLineB, stroke, 225).fill()); break;
                default: System.out.println("Error in switch block in the " + getClass());
            }
        }
        return out.toString();
    }
}
