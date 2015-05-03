/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualassembler;

import java.util.ArrayList;
import java.util.List;

public class Source {
    String Label;
    String Operation;
    String OperandOne;
    String OperandTwo;
    static List<Source> src;
   
    public Source(String Label,String Operation, String OperandOne, String OperandTwo){
        this.Label=Label;
        this.Operation=Operation;
        this.OperandOne=OperandOne;
        this.OperandTwo=OperandTwo;               
    }

    public Source(){src=new ArrayList<Source>();}
    /*public void load(){
        src=new ArrayList<>();
        src.add(new Source("","START","",""));
        src.add(new Source("","COPY","ZERO","OLDER"));
        src.add(new Source("","COPY","ONE","OLD"));
        src.add(new Source("","READ","LIMIT",""));
        src.add(new Source("","WRITE","OLD",""));
        src.add(new Source("FRONT","LOAD","OLDER",""));
        src.add(new Source("","ADD","OLD",""));
        src.add(new Source("","STORE","NEW",""));
        src.add(new Source("","SUB","LIMIT",""));
        src.add(new Source("","BRPOS","FINAL",""));
        src.add(new Source("","WRITE","NEW",""));
        src.add(new Source("","COPY","OLD","OLDER"));
        src.add(new Source("","COPY","NEW","OLD"));
        src.add(new Source("","BR","FRONT",""));
        src.add(new Source("FINAL","WRITE","LIMIT",""));
        src.add(new Source("","STOP","",""));
        src.add(new Source("ZERO","CONST","0",""));
        src.add(new Source("ONE","CONST","1",""));
        src.add(new Source("OLDER","SPACE","",""));
        src.add(new Source("OLD","SPACE","",""));
        src.add(new Source("NEW","SPACE","",""));
        src.add(new Source("LIMIT","SPACE","",""));
        src.add(new Source("","END","",""));
    }*/
    
    
    public String getLabel(){
		return Label;
	}
	public String getOp(){
		return Operation;
	}
	public String getOperandOne(){
		return OperandOne;
	}
	public String getOperandTwo(){
		return OperandTwo;
	}
        public String toString(){
        return (Label+"\t"+Operation+"\t"+OperandOne+"\t"+OperandTwo+"\n");
    }
}
