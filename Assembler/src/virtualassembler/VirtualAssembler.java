/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualassembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 */
public class VirtualAssembler {

    private ArrayList<Indicator> indArray;
    private TreeMap<String,ArrayList<Indicator>> map;
    private final String EMPTY="";
    private String[] KEYWORDS={"START","END","CONST","SPACE"};
    private ArrayList<Integer> CO;
    private int REGCO;
    private ArrayList<OpType> result;
    static boolean ERROR=false;
    private File OUT=new File("src/virtualassembler/dest.txt");//Change Path for Windows
    private File IN=new File("src/virtualassembler/source.txt");//Change Path for Windows

    public VirtualAssembler() throws AssemblerException{
       
       map=new TreeMap<String,ArrayList<Indicator>>();
       CO=new ArrayList<Integer>();
       Source source=new Source();
        //Incase loading source from List
       //source.load();
       OperationCode op=new OperationCode();
       op.fillTable();
       
    }
    public ArrayList addE(boolean bool,int integer){
       indArray =new ArrayList<Indicator>();
       indArray.add(new Indicator(bool,integer));
       return indArray;
    }

    public int symNUM(String args){
        int number=0;
        for(int sym=0;sym<OperationCode.symbolTable.size();sym++){
            if(args.equals(OperationCode.symbolTable.get(sym).symbol)){
                number+=OperationCode.symbolTable.get(sym).getLength();
            }
        }
        return number;
    }

    public String symVAL(String args){
        String temp="";
        for(int sym=0;sym<OperationCode.symbolTable.size();sym++){
            if(args.equals(OperationCode.symbolTable.get(sym).symbol)){
                temp=Integer.toString(OperationCode.symbolTable.get(sym).getNumber());
            }
        }
        return temp;
    }

    public String symVpassTAB(String args){
       String temp="";
       temp=Integer.toString(map.get(args).get(0).getAddr());
        return temp;
    }

    public boolean checkIND(String string){
        boolean bool=true;
        if(!(map.get(string).get(0).getInd())){
            bool=false;
        }
        return bool;
    }

    public void displayH(){
           System.out.println("-----------------------------------------------");
           System.out.println("----------------FIRST PASS---------------------");
           System.out.println("-----------------------------------------------");
           System.out.println("SYMBOL"+"\t"+"Ind"+"\t"+"@");
           for(Map.Entry<String,ArrayList<Indicator>> entry: map.entrySet()){
               System.out.println(entry.getKey()+ "\t"+
                       entry.getValue().toString());
           }
           System.out.println("-----------------------------------------------");
           System.out.println("-----------------------------------------------");
           System.out.println("*****************************************************");
           System.out.println("\n");
           
        
    }

    public void passTwo(){
    //Add the Label values automatically
        result=new ArrayList<OpType>();
    int pos=0;
    while(pos<CO.size()-1)
    {
     String temp=Integer.toString(CO.get(pos));
     result.add(new OpType(temp+"r","","",""));
     pos++;
    }
    /************************************/   
    int position=1;
    while(position<Source.src.size()-1)
    {      //If the Operation is "CONST"
           if(Source.src.get(position).getOp().equals(KEYWORDS[2])){
             result.get(position-1).setOperation(Source.src.get(position).getOperandOne()+"a");
           //If the Operation is "SPACE"
           }else if(Source.src.get(position).getOp().equals(KEYWORDS[3])){
             result.get(position-1).setOperation("XXa");
           }else{
        
            //Without Pseudo KEYWORDS FOUND
            switch(symNUM(Source.src.get(position).getOp()))
            {
            case 1:
                    result.get(position-1).setOperation(symVAL(Source.src.get(position).getOp())+"a");
                    break;
            case 2:
                    result.get(position-1).setOperation(symVAL(Source.src.get(position).getOp())+"a");
                    result.get(position-1).setOperand1(symVpassTAB(Source.src.get(position).getOperandOne())+"r");
                    break;
            case 3:
                    result.get(position-1).setOperation(symVAL(Source.src.get(position).getOp())+"a");
                    result.get(position-1).setOperand1(symVpassTAB(Source.src.get(position).getOperandOne())+"r");
                    result.get(position-1).setOperand2(symVpassTAB(Source.src.get(position).getOperandTwo())+"r");
                    break;
         }
       }
        position++; 
    }
}
    public void displayR(){
           System.out.println("------------------------------------------------------");
           System.out.println("----------------SECOND PASS---------------------------");
           System.out.println("------------------------------------------------------");
           System.out.println("LABEL"+"\t"+"OPERATION"+"\t"+"OPERAND1"+"\t"+"OPERAND2");
            for(int x=0;x<result.size();x++){
            System.out.print(result.get(x).getLabel()+"\t"+"  ");
            System.out.print(result.get(x).getOperation()+"\t"+"\t");
            System.out.print(result.get(x).getOperand1()+"\t"+"\t");
            System.out.print(result.get(x).getOperand2()+"\n");
            }
           System.out.println("------------------------------------------------------");
           System.out.println("------------------------------------------------------");
    }
    public void LoadDest() throws IOException{
		PrintWriter pw=new PrintWriter(OUT);
		//Write to File Dest: GoodLuck
		for(int x=0;x<result.size();x++){
			pw.print(result.get(x).getLabel()+"\t");
                        pw.print(result.get(x).getOperation()+"\t");
                        pw.print(result.get(x).getOperand1()+"\t");
                        pw.print(result.get(x).getOperand2());
			pw.println();
		}
		pw.close();
		
	}
    public void clearFile() throws FileNotFoundException{
		PrintWriter writer=new PrintWriter(OUT);
		writer.print("");
		writer.close();
	}
    public void loadFile() throws Exception,AssemblerException{
        try
        {
            
            BufferedReader bufferR=new BufferedReader(new FileReader(IN));
            ArrayList<String> list=new ArrayList<String>();
            String line;
            /*******/
            
            
            /*******/
            int co=0; 
            //Adding lines to List
            while((line=bufferR.readLine())!=null){
            list.add(line.trim());
            co++;
            }
            
            int j=0;
            while(j<list.size())
            {
                String[] sourceFile=list.get(j).trim().split("\\s+");
                
               
                if(sourceFile.length>4){
                    throw new AssemblerException("Illegal characters in File: "+IN.getName() +
                                " at line : "+ j);
                }else {
                
                switch(sourceFile.length)
                {
                    case 1:
                        if (sourceFile[0].equals(EMPTY)){
                                throw new Exception("START/END IS MISSING at Line :"+ j);
                        }else{
                        Source.src.add(new Source("",sourceFile[0].trim(),"",""));
                        }
                        break;
                    case 2:
                        if(sourceFile[1].equals(KEYWORDS[3])||sourceFile[1].equals(KEYWORDS[2])) {
                            Source.src.add(new Source(sourceFile[0].trim(),sourceFile[1].trim(),"",""));
                         } else {
                            Source.src.add(new Source("",sourceFile[0].trim(),sourceFile[1].trim(),""));
                         }
                        break;
                    case 3:
                         if(sourceFile[0].equals("COPY")) {
                            Source.src.add(new Source("",sourceFile[0].trim(),sourceFile[1].trim(),sourceFile[2].trim()));
                         } else if(sourceFile[1].equals(KEYWORDS[2])) {
                            Source.src.add(new Source(sourceFile[0].trim(),sourceFile[1].trim(),sourceFile[2].trim(),""));
                         }else{
                             Source.src.add(new Source(sourceFile[0].trim(),sourceFile[1].trim(),sourceFile[2].trim(),""));
                         }
                        break;
                    case 4:
                        Source.src.add(new Source(sourceFile[0].trim(),sourceFile[1].trim(),sourceFile[2].trim(),sourceFile[3].trim()));
                        break;
                    case 5:
                        throw new AssemblerException("Illegal characters in File: "+IN.getName() +
                                " at line : "+ j);
                }
                }
                j++;
            }
            System.out.println("PROMPT :: <<  "+IN.getName().toUpperCase()+ " >> Loaded with SUCCESS ");
            bufferR.close();
        }catch(AssemblerException exp){
            exp.getMessage();
            exp.printStackTrace();
        }   
        catch(FileNotFoundException exp)
        {
          exp.printStackTrace();
          exp.getMessage();
        }
    }//END OF METHOD BODY
    public void displayFile(){
       for(int y=0;y<Source.src.size();y++){
            System.out.println(Source.src.get(y).toString());
        }

    }
    public boolean exist(String x){
       ArrayList<Indicator> numb = map.get(x);
       boolean bool=false;
           if (numb == null){
               bool=true;
           }
           return bool;
    }
    public boolean labelCheck(String arg){
        boolean boolvalue=false;
        for(int pos=0;pos<Source.src.size();pos++){
            if(arg.equals(Source.src.get(pos).getLabel())){
                boolvalue=true;
                break;
            }
        }
        return boolvalue;
    }
    public void passOne() throws AssemblerException,Exception
    {
        /*Check for KeyWord Missing Errors*/
        ErrorCheck();
        LabelError();
        int pos=0;
       while(pos<Source.src.size())
        {
            if(!(Source.src.get(pos).getLabel().equals(EMPTY))){
                 map.put(Source.src.get(pos).getLabel(),addE(false,0));
                //map.put("",addE(false,0));
            }
            pos++;
        }
        
        
        /*--------------------------------------------------------------------*/
        int position=0;
       while(position<Source.src.size())
        {   
            int caseNum;
        
            if(Source.src.get(position).getLabel().equals(EMPTY))
            {caseNum=0;}else{ caseNum=1;}
            switch(caseNum){
                //If label is Empty
                case 0:
                    //If the Operation is "START"
                    if(Source.src.get(position).getOp().equals(KEYWORDS[0])){
                        REGCO=0; 
                        CO.add(REGCO);
                    //If the Operation is "END"
                    }else if(Source.src.get(position).getOp().equals(KEYWORDS[1])){
                                passTwo(); 
                     //If the Operation is "CONST"
                    }else if(Source.src.get(position).getOp().equals(KEYWORDS[2])){
                        REGCO+=1;
                        CO.add(REGCO);
                        ConstError(position);
                      //If the Operation is "SPACE"
                    }else if((Source.src.get(position).getOp().equals(KEYWORDS[3]))){
                        REGCO+=1;
                        CO.add(REGCO);
                        SpaceError(position);
                    }else
                    {
                        switch(symNUM(Source.src.get(position).getOp()))
                        {
                        case 1:
                                REGCO+=1;
                                CO.add(REGCO);
                                if(!Source.src.get(position).getOperandOne().isEmpty()||!Source.src.get(position).getOperandTwo().isEmpty()){
                                    throw new AssemblerException("ERROR:!!!!! ILLEGAL OPERANDS AT LINE "+position);
                                }
                                break;
                        case 2:
                                REGCO+=2;
                                CO.add(REGCO);
                                if(Source.src.get(position).getOperandOne().isEmpty()||!Source.src.get(position).getOperandTwo().isEmpty()){
                                    throw new AssemblerException("ERROR: MISSING/ILLEGAL OPERANDS AT LINE "+position);
                                }
                               if(exist(Source.src.get(position).getOperandOne())){
                                   map.put(Source.src.get(position).getOperandOne(),addE(false,0));
                                }else if(!(map.get(Source.src.get(position).getOperandOne()).get(0).getInd())){
                                
                                    map.get(Source.src.get(position).getOperandOne()).get(0).setAddr(REGCO);
                                
                                     if(labelCheck(Source.src.get(position).getOperandOne())){
                                     map.get(Source.src.get(position).getOperandOne()).get(0).setInd(true);
                                     map.put(Source.src.get(position).getOperandOne(),addE(true,REGCO));
                                     }
                                    
                                }
                                break;
                        case 3:
                                REGCO+=3;
                                CO.add(REGCO);
                                //Check for Errors
                                if(Source.src.get(position).getOperandOne().isEmpty()||Source.src.get(position).getOperandTwo().isEmpty()){
                                    throw new AssemblerException("ERROR: MISSING OPERANDS AT LINE "+position);
                                }
                                //
                                if(exist(Source.src.get(position).getOperandOne())){
                                    map.put(Source.src.get(position).getOperandOne(),addE(false,0));
                                }else if(!(map.get(Source.src.get(position).getOperandOne()).get(0).getInd())){
                                    if(labelCheck(Source.src.get(position).getOperandOne()))
                                    map.get(Source.src.get(position).getOperandOne()).get(0).setInd(true);
                                    
                                    map.get(Source.src.get(position).getOperandOne()).get(0).setAddr(REGCO);
                                }
                                if(exist(Source.src.get(position).getOperandTwo())){
                                    map.put(Source.src.get(position).getOperandTwo(),addE(false,0));
                                }else if(!(map.get(Source.src.get(position).getOperandTwo()).get(0).getInd())){
                                    if(labelCheck(Source.src.get(position).getOperandOne()))
                                    map.get(Source.src.get(position).getOperandTwo()).get(0).setInd(true);
                                    
                                    map.get(Source.src.get(position).getOperandTwo()).get(0).setAddr(REGCO);
                                }
                                
                            
                                break;
                        }
                    }
                    break;
                //If label has a value
               case 1:
                        if(!(exist(Source.src.get(position).getLabel()))){
                                    map.get(Source.src.get(position).getLabel()).get(0).setInd(true);
                                    map.get(Source.src.get(position).getLabel()).get(0).setAddr(REGCO);
                        }
                        
                                    
                        if(Source.src.get(position).getOp().equals(KEYWORDS[0])){
                        REGCO=0; 
                        CO.add(REGCO);
                         //If the Operation is "END"
                        }else if(Source.src.get(position).getOp().equals(KEYWORDS[1])){
                                passTwo(); 
                         //If the Operation is "CONST"
                        }else if(Source.src.get(position).getOp().equals(KEYWORDS[2])){
                        REGCO+=1;
                        CO.add(REGCO);
                        ConstError(position);
                         //If the Operation is "SPACE"
                        }else if((Source.src.get(position).getOp().equals(KEYWORDS[3]))){
                        REGCO+=1;
                        CO.add(REGCO);
                        SpaceError(position);
                        }else{
                        switch(symNUM(Source.src.get(position).getOp()))
                        {
                            case 1:
                                REGCO+=1;
                                CO.add(REGCO);
                                if(!Source.src.get(position).getOperandOne().isEmpty()||!Source.src.get(position).getOperandTwo().isEmpty()){
                                    throw new AssemblerException("ERROR:!!!!! ILLEGAL OPERANDS AT LINE "+position);
                                }
                                break;
                            case 2:
                                REGCO+=2;
                                CO.add(REGCO);
                                if(Source.src.get(position).getOperandOne().isEmpty()||!Source.src.get(position).getOperandTwo().isEmpty()){
                                    throw new AssemblerException("ERROR: MISSING/ILLEGAL OPERANDS AT LINE "+position);
                                }
                                if(exist(Source.src.get(position).getOperandOne())){
                                    map.put(Source.src.get(position).getOperandOne(),addE(false,REGCO));
                                }else if(!(map.get(Source.src.get(position).getOperandOne()).get(0).getInd())){
                                    if(labelCheck(Source.src.get(position).getOperandOne()))
                                    map.get(Source.src.get(position).getOperandOne()).get(0).setInd(true);
                                    
                                    map.get(Source.src.get(position).getOperandOne()).get(0).setAddr(REGCO);
                                }
                                
                                break;
                            case 3:
                                REGCO+=3;
                                CO.add(REGCO);
                                if(Source.src.get(position).getOperandOne().isEmpty()||Source.src.get(position).getOperandTwo().isEmpty()){
                                    throw new AssemblerException("ERROR: MISSING OPERANDS AT LINE "+position);
                                }
                                if(exist(Source.src.get(position).getOperandOne())){
                                    map.put(Source.src.get(position).getOperandOne(),addE(false,REGCO));
                                }else if(!(map.get(Source.src.get(position).getOperandOne()).get(0).getInd())){
                                    if(labelCheck(Source.src.get(position).getOperandOne()))
                                    map.get(Source.src.get(position).getOperandOne()).get(0).setInd(true);
                                    
                                    map.get(Source.src.get(position).getOperandOne()).get(0).setAddr(REGCO);
                                }
                                if(exist(Source.src.get(position).getOperandTwo())){
                                    map.put(Source.src.get(position).getOperandTwo(),addE(false,REGCO));
                                }else if(!(map.get(Source.src.get(position).getOperandTwo()).get(0).getInd())){
                                    if(labelCheck(Source.src.get(position).getOperandOne()))
                                    map.get(Source.src.get(position).getOperandTwo()).get(0).setInd(true);
                                    map.get(Source.src.get(position).getOperandTwo()).get(0).setAddr(REGCO);
                                }
                                
                            
                                break;
                        }
                    }
                    
                        break;
            }    
            
            position++;
        }
    }
    //Error Methods
    public boolean CheckStop(){
        int pos=1;
        boolean result=false;
        String CHECK="STOP";
        loop: while(pos<Source.src.size()-1){
            if(Source.src.get(pos).getOp().equals(CHECK)){
               result=true;
               break loop;
            }
            pos++;
        }
        return result;
    }
    public void ErrorCheck() throws AssemblerException{
        int pos=0;
        main:     while(pos<Source.src.size()){
                    if((Source.src.get(pos).getOp().equals(""))&& pos==0){
                    ERROR=true;
                    throw new AssemblerException("-START- is Missing at Line 1: FORCED STOP");
                    }else if((Source.src.get(pos).getOp().equals(""))&&pos==Source.src.size()-1){
                    ERROR=true;
                    throw new AssemblerException("-END- is Missing at Line "+ (Source.src.size()-1));
                    }else if((Source.src.get(pos).getOp().equals(""))){
                        ERROR=true;
                        throw new AssemblerException("-OPERATION CODE- is Missing at Line  <<"+ (pos)+">> : EMPTY CODE LINE___" );
                    }else if(CheckStop()==false){
                        ERROR=true;
                        throw new AssemblerException("-STOP- is Missing in SOURCE");
                    }
                    pos++;
                  }
    }
    public void ConstError(int i) throws AssemblerException{
        if(Source.src.get(i).getOperandOne().isEmpty()){
            ERROR=true;
            throw new AssemblerException("Error: CAN NOT HAVE EMPTY OPERAND at LINE "+ (i+1));
        }else if (!(Source.src.get(i).getOperandTwo().isEmpty())){
            ERROR=true;
            throw new AssemblerException("ERROR: UNAUTHORIZED OPERAND at LINE "+ (i+1));
        }
    }
    public void SpaceError(int i) throws AssemblerException{
        if(!(Source.src.get(i).getOperandOne().isEmpty())){
            ERROR=true;
            throw new AssemblerException("Error: UNAUTHORIZED OPERAND at LINE "+ (i+1));
        }else if (!(Source.src.get(i).getOperandTwo().isEmpty())){
            ERROR=true;
            throw new AssemblerException("ERROR: UNAUTHORIZED OPERAND at LINE "+ (i+1));
        }
    }
    public ArrayList getR(){
        return result;
    }
    public void LabelError() throws AssemblerException{
        int i=0;
        while(i<=Source.src.size()){
            for(int j=i+1;j<Source.src.size();j++){
                if(!Source.src.get(i).getLabel().equals("")){
                    if(Source.src.get(i).getLabel().equals(Source.src.get(j).getLabel())){
                        ERROR=true;
                        throw new AssemblerException("DUPLICATE LABEL_ERROR : "+Source.src.get(j).getLabel());
                        
                    }
                }
            }
            i++;
        }
    }
            
    
}//END OF CLASS