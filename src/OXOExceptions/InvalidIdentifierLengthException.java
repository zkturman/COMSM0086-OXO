package OXOExceptions;

public class InvalidIdentifierLengthException extends InvalidIdentifierException{
    private int length;
    private String command;

    public InvalidIdentifierLengthException(String command){
        super();
        this.command = command;
        length = command.length();
    }

    public String toString(){
        if (command.isBlank()){
            command = "(empty)";
        }
        return "The command, " + command + ", is " + length + " character(s). The command should be length 2.";
    }
}
