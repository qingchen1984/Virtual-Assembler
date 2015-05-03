/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualassembler;

import java.util.ArrayList;

public class Indicator {
   private boolean bool;
   private int integer;

    public Indicator(){
         bool=false;
         integer=0;
    }

    public Indicator(boolean bool,int integer){
        this.bool=bool;
        this.integer=integer;
         
    }
    public int getAddr(){
        return integer;
    }
    public boolean getInd(){
        return bool;
    }
    public void setInd(boolean bool){
        this.bool=bool;
    }
    public void setAddr(int intg){integer=intg;}
    public String toString(){
        return (bool+"\t"+integer);
    }
}
