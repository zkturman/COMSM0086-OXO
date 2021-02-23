package OXOExceptions;

public class InvalidIdentifierCharacterException extends InvalidIdentifierException{
    private char character;
    private RowOrColumn rowOrCol;

    public InvalidIdentifierCharacterException(char c, RowOrColumn rOrC){
        character = c;
        rowOrCol = rOrC;
    }
}
