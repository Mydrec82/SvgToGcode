package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *this class set Zero in mm and getX, getY create coordinate for all elements
 * methol factory create out block(string) for classes Figure
 */
public class Constructor implements Construct {
    private double x;
    private double y;
@Override
    public OutFile getOutFile() {
        return out;
    }

    private OutFile out = new OutFile();

    Constructor(List<String>out){
        this.x = Double.parseDouble(out.get(0));
        this.y = Double.parseDouble(out.get(1));
        System.out.println("x = "+ x + " y = " + y);

    }
    @Override
    public double getX(double pixel) {
        pixel -= x;
        pixel = pixelTomm(pixel);
        return pixel + Corection.getX();
    }

    @Override
    public double getY(double pixel) {
        pixel -= y;
        pixel = pixelTomm(pixel);
        return pixel + Corection.getY();
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
            case"RECT": {
                List<Double> point;
                double r;
                point = parseReGx(coordinate.get(0));
                for (int a = 0; a < point.size(); a += 2) {
                    point.set(a, getX(point.get(a)));
                    point.set(a + 1, getY(point.get(a + 1)));
                }
                r = pixelTomm(Double.parseDouble(coordinate.get(1)));
                List<PointLine> points = new ArrayList<>();
                for (int a = 0; a < point.size(); a += 2) {
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
                System.out.println(point+ " " + getClass());
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

            default :System.out.println("Error in switch " + getClass());

        }

    }

    //parse point into Double from String
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
