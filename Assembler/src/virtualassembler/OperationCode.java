package virtualassembler;




import java.util.ArrayList;
import java.util.List;

/**
 * This is a OperationCode class ; contains a Symbol Table used by the assembler
 */
public class OperationCode {

	String symbol;
	int number;
	int length;
	int operationNumber;
	static 	List<OperationCode> symbolTable;

	public OperationCode(){
                symbolTable=new ArrayList<OperationCode>();
		
	}
	public OperationCode(String arg1,int arg2,int arg3,int arg4){
		symbol=arg1;
		number=arg2;
		length=arg3;
		operationNumber=arg4;
	}

	/**
	 *  fillTable() intialises all the assembler Table
	 */
	public void fillTable(){
                
		symbolTable.add(new OperationCode("ADD",2,2,1));
		symbolTable.add(new OperationCode("BR",0,2,1));
		symbolTable.add(new OperationCode("BRNEG",5,2,1));
		symbolTable.add(new OperationCode("BRZERO",4,2,1));
		symbolTable.add(new OperationCode("BRPOS",01,2,1));
		symbolTable.add(new OperationCode("COPY",13,3,2));
		symbolTable.add(new OperationCode("DIVIDE",10,2,1));
		symbolTable.add(new OperationCode("LOAD",3,2,1));
		symbolTable.add(new OperationCode("MULT",14,2,1));
		symbolTable.add(new OperationCode("READ",12,2,1));
		symbolTable.add(new OperationCode("STOP",11,1,0));
		symbolTable.add(new OperationCode("SUB",6,2,1));
		symbolTable.add(new OperationCode("STORE",7,2,1));
		symbolTable.add(new OperationCode("WRITE",8,2,1));	
	}
	public String getSymbol(){
		return symbol;
	}
	public int getNumber(){
		return number;
	}
	public int getLength(){
		return length;
	}
	public int getOperationNumber(){
		return operationNumber;
	}
}
