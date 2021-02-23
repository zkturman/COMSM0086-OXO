package OXOExceptions;

public class OutsideCellRangeException extends CellDoesNotExistException{
    private int position;
    private RowOrColumn rowOrCol;

    public OutsideCellRangeException(int position, RowOrColumn rowOrCol){
        this.position = position;
        this.rowOrCol = rowOrCol;
    }

}
