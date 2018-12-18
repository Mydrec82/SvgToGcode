package com.company;

/**
 * this class define direction Line and return String Direction
 */
 class Direction {
    private PointLine a;
    private PointLine b;
    StringBuilder out = new StringBuilder();

    Direction(PointLine a, PointLine b){
        this.a = a;
        this.b = b;
    }

    String getDirection(){
        double s = b.getX() - a.getX();
        double m = b.getY() - a.getY();
        if(s >= 0){
            if(s != 0) out.append("right");
        }else out.append("left");

        if(m >= 0){
            if(m != 0) out.append("up");
        }else out.append("down");
       return out.toString();
    }
 }
