/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualassembler;
import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Main  {
    private static final Scanner in=new Scanner(System.in);

    public static void clear() throws IOException{
        System.out.print("\u001b[2J"); //Clear console
        System.out.flush();

    }
    public static void main(String[] args) throws AssemblerException, Exception {
        // TODO code application logic here
        
        /*System.out.println("#####################################################");
        System.out.println("#                                                   #");
        System.out.println("#                                                   #");
        System.out.println("#                                                   #");
        System.out.println("#  |     | |-- |   |***  |___|    | |    |****      #");
        System.out.println("#   | | |  |-- |   |    |     |  | - |   |****      #");
        System.out.println("#    | |   |-- |__ |***  |___|  |     |  |****      #");
        System.out.println("#                                                   #");
        System.out.println("#                                                   #");
        System.out.println("# PROGRAM  : ASSEMBLER                              #");
        System.out.println("# Developer: CHIKONTWE PHILIP                       #");
        System.out.println("#####################################################");
        boolean state=true;
        while(state){ 
    System.out.println("#########################################################");
    System.out.println("#                     HOME                              #");
    System.out.println("#  Select the action that you want to perform:          #");
    System.out.println("#   1.  Load Source Code                                #");
    System.out.println("#   2.  Execute Assembler                               #");
    System.out.println("#   3.  Show First Pass Result                          #");
    System.out.println("#   4.  Show Second Pass Result                         #");
    System.out.println("#   5.  Show Object File                                #");
    System.out.println("#   6.  EXIT                                            #");
    System.out.println("#       Enter action number (1-6):                      #");
    System.out.println("#########################################################");
    System.out.println("Enter action number (1-6):  ");
    int command1;
    if (in.hasNextInt()) {
       command1 = in.nextInt();
       in.nextLine();
    }
    else {
       System.out.println("\nILLEGAL RESPONSE.  YOU MUST ENTER A NUMBER.");
       in.nextLine();
       continue;
    }
    switch(command1) {
    case 1:
        clear();
        boolean change1=true;
        while(change1){
        if(!VirtualAssembler.ERROR){
        ass.loadFile();
        }else{
            System.out.println("\n\n::::::: ERRORS ENCOUNTERED IN SOURCE CODE :::::::\n\n");
        }
        System.out.println("#==========================#");
        System.out.println("---BACK TO HOME---(ENTER 0)");
        if (in.hasNextInt()) {
        command1 = in.nextInt();
        in.nextLine();
        if(command1==0)
            change1=false;
        }
        else {
        System.out.println("\nILLEGAL RESPONSE.  YOU MUST ENTER A NUMBER.");
        in.nextLine();
        continue;
        }
        }
        break;
    case 2:
        clear();
        
        try{
        ass.passTest();    
        ass.clearFile();
	ass.LoadDest();	
	}catch(AssemblerException e){
        e.getMessage();
	e.printStackTrace();
	}
        System.out.println("---ASSEMBLER EXECUTION DONE -----");
        break;
    case 3:
        clear();
        if(!VirtualAssembler.ERROR){
        ass.displayH();
        }else{
            System.out.println("\n\n::::::: ERRORS ENCOUNTERED IN SOURCE CODE :::::::\n\n");
        }
        break;
    case 4:
        clear();
       
        if(!VirtualAssembler.ERROR){
        ass.displayR();
        }else{
            System.out.println("\n\n::::::: ERRORS ENCOUNTERED IN SOURCE CODE :::::::\n\n");
        }
        
        break;
     case 5:
       clear();
       
       System.out.println("\n\n\n");
        for(int y=0;y<ass.getR().size();y++){
            System.out.println(ass.getR().get(y).toString());
            }
        
       break;
    case 6:
       System.out.println("\f");
       //System.out.println("\nEXITING PROGRAM.");
        
       state=false;
       System.exit(-1);
       break;
    default:
       System.out.println("\nILLEGAL ACTION NUMBER.");
    }
    }*/
        /**********************************************************************/
        long START=System.nanoTime();
        
        VirtualAssembler ass=new VirtualAssembler();
        try{
            
            ass.loadFile();
            ass.passOne();
            
            System.out.println("-----------------");
        }catch(AssemblerException e){
            e.getMessage();
            e.printStackTrace();
        }
        if(!VirtualAssembler.ERROR){
           
           ass.displayH();
           ass.displayR();
        try{
        ass.clearFile();
	ass.LoadDest();	
	}catch(Exception e){
	e.printStackTrace();
	}
        }else{
            System.out.println("\n\n::::::: ERRORS ENCOUNTERED IN SOURCE CODE :::::::\n\n");
        }
        //ass.displayFile();
        
        long END=System.nanoTime();
        System.out.println("\n\nBUILD TIME : "+(END-START));
        
}
}
//End of Class 

