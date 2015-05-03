
package virtualassembler;

/**
 *This is a custom assembler exception class
 * @author ms
 */
public class AssemblerException extends Exception {
    String message;
    public AssemblerException(String message){
        super(message);
    }
}
