package com.company;

public class LaserSpeed {
   private static LaserSpeed laserSpeed = new LaserSpeed();
    private LaserSpeed(){}
    public static LaserSpeed getInstance(){
        return laserSpeed;
    }

    private int laserWork;
    private int laserTravel ;


     int getLaserWork() {
        return laserWork;
    }

     void setLaserWork(int laserSpeedWork) {
        this.laserWork = laserSpeedWork;
    }

     int getLaserTravel() {
        return laserTravel;
    }

     void setLaserTravel(int laserSpeedTravel) {
        this.laserTravel = laserSpeedTravel;
    }


}
