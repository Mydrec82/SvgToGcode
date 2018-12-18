package com.company;


import java.io.File;

public class InFile {
    File file;
  static private InFile in = new InFile();
   private InFile(){
    }

   static InFile getInstance(){
       return in;
   }

    void setFile(File file){
       this.file = file;
    }
   File getFile(){
       return file;
   }
}
