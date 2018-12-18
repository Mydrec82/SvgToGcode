package com.company;

import java.util.List;

public interface Construct {
    double PixelMM = 3.937;
    double getX(double pixel);
    double getY(double pixel);
    double pixelTomm(double pixel);
    void factory(String type, List<String> coordinate);
    OutFile getOutFile();
}
