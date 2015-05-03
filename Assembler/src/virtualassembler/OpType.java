/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualassembler;

public class OpType {
    private String label;
    private String operation;
    private String operator1;
    private String operator2;
    
    /*The constructor method
    */
    public OpType(String label,String operation,String operator1,String operator2){
        this.label=label;
        this.operation=operation;
        this.operator1=operator1;
        this.operator2=operator2;
    }
    public String getLabel(){ return label;}
    public String getOperation(){ return operation;}
    public String getOperand1(){ return operator1;}
    public String getOperand2(){ return operator2;}
    public void setLabel(String lb){label=lb;}
    public void setOperation(String op){operation=op;}
    public void setOperand1(String op){operator1=op;}
    public void setOperand2(String op){operator2=op;}
    public String toString(){
        return (label+"\t"+operation+"\t"+operator1+"\t"+operator2+"\n");
    }
}
