package OXOExceptions;

public class CellAlreadyTakenException extends OXOMoveException{
    public CellAlreadyTakenException(int row, int col){
        super(row, col);
    }
    public String toString(){
        return "The cell " + (char) super.getRow() + (char) super.getColumn() + " is already occupied.";
    }
}
