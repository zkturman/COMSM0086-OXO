package OXOExceptions;

public class InvalidIdentifierCharacterException extends InvalidIdentifierException{
    private char character;
    private RowOrColumn rowOrCol;

    public InvalidIdentifierCharacterException(char c, RowOrColumn rOrC){
        super();
        character = c;
        rowOrCol = rOrC;
    }

    public String toString(){
        return "The " + rowOrCol + " " + character +
                    " is an invalid character. Please enter a-i for rows and 1-9 for columns.";
    }
}
