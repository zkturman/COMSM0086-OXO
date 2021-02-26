package OXOExceptions;

public class OutsideCellRangeException extends CellDoesNotExistException{
    private int position;
    private RowOrColumn rowOrCol;

    public OutsideCellRangeException(int position, RowOrColumn rowOrCol){
        super();
        this.position = position;
        this.rowOrCol = rowOrCol;
    }

    public String toString(){
        return "The " + rowOrCol + " " + (char) position + " is outside the current grid size.";
    }


}
