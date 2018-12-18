package com.company;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// matrix(a, b, c, d, e, f)
//Xnewcord = XAprevcord + CYprevcord + e
//Ynewcord = YBprevcord + DYprevcord + f

public class Mirror implements Construct {
    private double x;
    private  double y;
    private List<Double>matrix;
    private OutFile out = new OutFile();

    Mirror(List<String>out, String matrix){
        x = Double.parseDouble(out.get(0));
        y = Double.parseDouble(out.get(1));
        this.matrix = parseReGx(matrix);
        System.out.println(matrix);
        System.out.println("mirror x = "+ x + " y = " + y);
    }

    @Override
    public OutFile getOutFile() {
        return out;
    }

    @Override
    public double getX(double pixel) {
       double xMirror =  pixel * matrix.get(0) + matrix.get(4);
       xMirror -= x;
       xMirror = pixelTomm(xMirror);
       return xMirror + Corection.getX();
    }

    @Override
    public double getY(double pixel) {
        double yMirror =  pixel * matrix.get(3) + matrix.get(5);
        yMirror -= y;
        yMirror = pixelTomm(yMirror);
        return yMirror + Corection.getY();
    }

    @Override
    public double pixelTomm(double pixel) {
        pixel /= PixelMM;
        pixel = new BigDecimal(pixel).setScale(3, RoundingMode.HALF_UP).doubleValue();
        return pixel;
    }

    public void factory(String figures, List<String>coordinate){
        Figure figure;
        switch(figures){
            case"ELLIPSE": {
                List<Double> cord = new ArrayList<>();
                for(String s : coordinate)cord.add(Double.parseDouble(s));

                figure = new Circle(getX(cord.get(0)), getY(cord.get(1)), pixelTomm(cord.get(2)), pixelTomm(cord.get(4)));
                out.write(figure.fill());
            }
            break;
            case"RECT":{
                List<Double> point;
                double  r;
                point = parseReGx(coordinate.get(0));
                for(int a = 0; a < point.size(); a += 2){
                    point.set(a, getX(point.get(a)));
                    point.set(a + 1, getY(point.get(a+1)));
                }
                r = pixelTomm(Double.parseDouble(coordinate.get(1)));
                List<PointLine>points = new ArrayList<>();
                for(int a = 0; a < point.size(); a += 2){
                    points.add(new PointLine(point.get(a), point.get(a + 1)));
                }
                figure = new UniversalRectangle(points, r);
                out.write(figure.fill());
            }
            break;

            case"polyline":{
                double stroke = pixelTomm(Double.parseDouble(coordinate.get(1)));
                List<Double> point;
                point = parseReGx(coordinate.get(0));
                for(int a = 0; a < point.size(); a += 2){
                    point.set(a, getX(point.get(a)));
                    point.set(a + 1, getY(point.get(a + 1)));
                }
                System.out.println(point + " " + getClass());
                figure = new Line(point, stroke);
                out.write(figure.fill());
            }
            break;

            case"OVAL":{
                List<Double> list = parseReGx(coordinate.get(0));
                PointLine pointA = new PointLine(getX(list.get(0)), getY(list.get(1)));
                PointLine pointB = new PointLine(getX(list.get(2)), getY(list.get(3)));
                double stroke = pixelTomm(Double.parseDouble(coordinate.get(1)));
                int angle = Integer.parseInt(coordinate.get(2));
                double cx = getX(Double.parseDouble(coordinate.get(3)));
                double cy = getY(Double.parseDouble(coordinate.get(4)));
                double r = pixelTomm(Double.parseDouble(coordinate.get(5)));
                if(angle == 0 || angle == 180) {
                    figure = new OvalHorizontal(pointA, pointB, angle, stroke, cx, cy, r);
                }else figure = new OvalVertical(pointA, pointB, angle, stroke, cx, cy, r);
                out.write(figure.fill());

            }
            break;

            default: System.out.println("Error in switch " + getClass());

        }

    }

    private List<Double> parseReGx(String s){
        List<Double> dob  = new ArrayList<>();
        Pattern p = Pattern.compile("-?\\d+\\.?\\d*");
        Matcher mat = p.matcher(s);
        while(mat.find()){
            dob.add(Double.parseDouble(mat.group()));
        }
        return dob;
    }
}
