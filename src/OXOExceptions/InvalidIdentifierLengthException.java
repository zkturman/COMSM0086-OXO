package OXOExceptions;

public class InvalidIdentifierLengthException extends InvalidIdentifierException{
    int length;

    public InvalidIdentifierLengthException(int stringLen){
        length = stringLen;
    }
}
