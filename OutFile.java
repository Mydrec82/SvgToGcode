package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class OutFile {
  private File file;
   OutFile(){
       String place = System.getProperty("user.home");
       String name = "out";
       int a = 1;
       file = new File(place + "/Desktop/".concat(name).concat(".gcode"));
       while(file.exists()){
           file = new File(place + "/Desktop/".concat(name).concat(String.valueOf(a).concat(".gcode")));
           a++;
       }
       try {
           file.createNewFile();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
    void write(String str){
       try(FileWriter writer = new FileWriter(file, true)) {
           writer.write(str);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}
