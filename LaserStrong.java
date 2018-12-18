package com.company;

 class LaserStrong {
     int getLaserPower() {
        return laserPower;
    }

    void setLaserPower(int laserPower) {
        if(laserPower > 255)laserPower = 255;
         this.laserPower = laserPower;
    }

    private int laserPower;

    private static LaserStrong laserStrong = new LaserStrong();
    private LaserStrong(){}

    public static LaserStrong getInstance(){
        return laserStrong;
    }
}
