package com.company;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class controller for XML GUI
 */
public class Control {
   public TextField speedWork;
   public TextField speedTravel;
   public TextField powerLaser;
   public TextField corectX;
   public TextField corectY;
   public Button selectFile;
   public Button border;
   public Label path;
   public CheckBox mirrorX;
   public CheckBox mirrorY;

   @FXML
    void setSpeedWork (){
       int speed = parseInp(speedWork.getText());
       if(speed <= 2000 & speed > 0){
           LaserSpeed.getInstance().setLaserWork(speed);
           speedWork.setOnKeyPressed(event -> {
               if(event.getCode().equals(KeyCode.ENTER)){
                   try {
                       new Robot().keyPress(KeyEvent.VK_TAB);
                   } catch (AWTException e) {
                       e.printStackTrace();
                   }
               }
           });
       }else speedWork.setText("");

    }

    @FXML
    void setSpeedTravel (){
        int speed = parseInp(speedTravel.getText());
        if(speed <= 4000 & speed > 0){
            LaserSpeed.getInstance().setLaserTravel(speed);
            speedTravel.setOnKeyPressed(event -> {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    try {
                        new Robot().keyPress(KeyEvent.VK_TAB);
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else speedTravel.setText("");
    }

    @FXML
    void setPowerLaser (){
        int power = parseInp(powerLaser.getText());
        if(power <= 255 & power > 0){
            LaserStrong.getInstance().setLaserPower(power);
            powerLaser.setOnKeyPressed(event -> {
                if(event.getCode().equals(KeyCode.ENTER)){
                    try {
                        new Robot().keyPress(KeyEvent.VK_TAB);
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else powerLaser.setText("");
    }

    @FXML
    void selectFile(){
       FileChooser file = new FileChooser();

       try{
           File files  = file.showOpenDialog(new Stage());
           path.setText(files.getAbsolutePath());
           if(files.getAbsolutePath().endsWith(".svg")) InFile.getInstance().setFile(files);
          else path.setText("Error file");
       }catch (NullPointerException e){
           path.setText("Please check file");
       }
    }

   // correct start coordinate
    private void setCorrection(){
       int x = parseInp(corectX.getText());
       int y = parseInp(corectY.getText());
       if(x >= 0 && x <= 150 && y >= 0 && y <= 150){
          new Corection(x, y);
       }else {
          new Corection(0, 0);
           corectX.setText("0");
           corectY.setText("0");
       }
    }

   @FXML
   void createBorder(){
       setCorrection();
       setSpeedWork ();
       setSpeedTravel ();
       setPowerLaser ();
       try{
           File fil = InFile.getInstance().getFile();
           new ReadBorder().read(mirrorX.isSelected(), mirrorY.isSelected());
       }
       catch (IllegalArgumentException e){
           path.setText("Choise file");
       }
   }


   @FXML
    void create(){
       setCorrection();
       setSpeedWork ();
       setSpeedTravel ();
       setPowerLaser ();
       try{
           File fil = InFile.getInstance().getFile();
           new Read().read(mirrorX.isSelected(), mirrorY.isSelected());
       }
       catch (IllegalArgumentException e){
           path.setText("Choise file");
       }
    }


  private int parseInp(String inp){
       int a = 0;
      Pattern pattern = Pattern.compile("\\d+");
      Matcher matcher = pattern.matcher(inp);
      while(matcher.find()){
          a = Integer.parseInt(matcher.group());
      }
      return a;
  }
}
