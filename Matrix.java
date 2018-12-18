package com.company;

import java.util.ArrayList;
import java.util.List;

class Matrix {

   private List<Double>coord;

    Matrix(List<String> coord){
        List<Double> midl = new ArrayList<>();
        for(String s : coord){
            midl.add(Double.parseDouble(s));
        }
        this.coord = midl;
    }

    String getMatrixX(){
        double mir = coord.get(1) * 2 + coord.get(3);
        return "1, 0, 0, -1, 0, " + mir;
    }

    String getMatrixY(){
        double mir = coord.get(0) * 2 + coord.get(2);
        return "-1, 0, 0, 1, " + mir + ", 0";
    }

    String getMatrixXY(){
        double x = coord.get(0) * 2 + coord.get(2);
        double y = coord.get(1) * 2 + coord.get(3);
        return "-1, 0, 0, -1, "+ x + ", " + y;
    }
}
