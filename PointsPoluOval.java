package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class PointsPoluOval {

    public List getLeftPoint(PointLine pointLine, double stroke){
        List<PointLine> list = new ArrayList<>();
        int n = 0;

        while(n <= stroke/ Steep.getSteep()) {
            double rStroke = stroke/2;
            rStroke = new BigDecimal(rStroke).setScale(3, RoundingMode.HALF_UP).doubleValue();
            double y = (pointLine.getY() + rStroke) - (Steep.getSteep() * n);
            double x = pointLine.getX() - Math.sqrt(Math.pow(rStroke, 2) - (Math.pow((rStroke - Steep.getSteep() * n), 2)));
            x = new BigDecimal(x).setScale(3, RoundingMode.HALF_UP).doubleValue();
            y = new BigDecimal(y).setScale(3, RoundingMode.HALF_UP).doubleValue();
            list.add(new PointLine(x, y));
            n++;
        }
        return list;
    }
    public List getRightPoint(PointLine pointLine, double stroke){
        List<PointLine> list = new ArrayList<>();
        int n = 0;

        while(n <= stroke/ Steep.getSteep()) {
            double rStroke = stroke/2;
            rStroke = new BigDecimal(rStroke).setScale(3, RoundingMode.HALF_UP).doubleValue();
            double y = (pointLine.getY() + rStroke) - (Steep.getSteep() * n);
            double x = pointLine.getX() + Math.sqrt(Math.pow(rStroke, 2) - (Math.pow((rStroke - Steep.getSteep() * n), 2)));
            x = new BigDecimal(x).setScale(3, RoundingMode.HALF_UP).doubleValue();
            y = new BigDecimal(y).setScale(3, RoundingMode.HALF_UP).doubleValue();
            list.add(new PointLine(x, y));
            n++;
        }
        return list;
    }
    public List getDownPoint(PointLine pointLine, double stroke){
        List <PointLine>list = new ArrayList<>();
        int n = 0;

        while(n <= stroke/ Steep.getSteep()) {
            double rStroke = stroke/2;
            rStroke = new BigDecimal(rStroke).setScale(3, RoundingMode.HALF_UP).doubleValue();
            double x = (pointLine.getX() - rStroke) + (Steep.getSteep() * n);
            double y = pointLine.getY() - Math.sqrt(Math.pow(rStroke, 2) - (Math.pow((rStroke - Steep.getSteep() * n), 2)));
            x = new BigDecimal(x).setScale(3, RoundingMode.HALF_UP).doubleValue();
            y = new BigDecimal(y).setScale(3, RoundingMode.HALF_UP).doubleValue();
            list.add(new PointLine(x, y));
            n++;
        }
        return list;
    }
    public List getUpPoint(PointLine pointLine, double stroke){
        List<PointLine> list = new ArrayList<>();
        int n = 0;

        while(n <= stroke/ Steep.getSteep()) {
            double rStroke = stroke/2;
            rStroke = new BigDecimal(rStroke).setScale(3, RoundingMode.HALF_UP).doubleValue();
            double x = (pointLine.getX() - rStroke) + (Steep.getSteep() * n);
            double y = pointLine.getY() + Math.sqrt(Math.pow(rStroke, 2) - (Math.pow((rStroke - Steep.getSteep() * n), 2)));
            x = new BigDecimal(x).setScale(3, RoundingMode.HALF_UP).doubleValue();
            y = new BigDecimal(y).setScale(3, RoundingMode.HALF_UP).doubleValue();
            list.add(new PointLine(x, y));
            n++;
        }
        return list;

    }
}
