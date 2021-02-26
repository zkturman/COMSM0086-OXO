import OXOExceptions.*;

class OXOController
{
    OXOModel gameModel;
    private int turnNumber = 0;
    private boolean gameover = false;

    private static class Coordinate{
        int row;
        int column;

        public Coordinate (int row, int column){
            this.row = row;
            this.column = column;
        }
        public int getRow(){
            return row;
        }
        public int getCol(){
            return column;
        }
    }

    public OXOController(OXOModel model) {
        gameModel = model;
        model.setCurrentPlayer(gameModel.getPlayerByNumber(0));
    }

    public void handleIncomingCommand(String command) throws OXOMoveException {
        int row, col;
        if (!gameover) {
            validateCoords(command);
            Coordinate c = new Coordinate(charToInt(command.charAt(0)), digitToInt(command.charAt(1)));
            row = c.getRow();
            col = c.getCol();
            turnNumber++;
            gameModel.setCellOwner(row, col, gameModel.getCurrentPlayer());
            gameover = isGameover();
            gameModel.setCurrentPlayer(gameModel.getPlayerByNumber(turnNumber % gameModel.getNumberOfPlayers()));
        }
    }

    private void validateCoords(String coords) throws OXOMoveException{
        char row, col;
        if (coords.length() != 2) {
            throw new InvalidIdentifierLengthException(coords);
        }
        row = coords.charAt(0);
        col = coords.charAt(1);
        if (Character.toLowerCase(row) < 'a' || Character.toLowerCase(row) > 'z') {
            throw new InvalidIdentifierCharacterException(row, RowOrColumn.ROW);
        }
        if (col < '1' || col > '9') {
            throw new InvalidIdentifierCharacterException(col, RowOrColumn.COLUMN);
        }
        if (charToInt(row) > (gameModel.getNumberOfRows() - 1)) {
            throw new OutsideCellRangeException(row, RowOrColumn.ROW);
        }
        if (digitToInt(col) > (gameModel.getNumberOfColumns() - 1)) {
            throw new OutsideCellRangeException(col, RowOrColumn.COLUMN);
        }
        if (gameModel.getCellOwner(charToInt(row), digitToInt(col)) != null) {
            throw new CellAlreadyTakenException(row, col);
        }
    }

    private boolean isGameover(){
        if (isGameWon()){
            gameModel.setWinner(gameModel.getCurrentPlayer());
            return true;
        }
        else if (isGameDrawn()){
            gameModel.setGameDrawn();
            return true;
        }
        return false;
    }

    private boolean isGameWon(){
        Coordinate c;
        int numInARow;
        for (int i = 0; i < gameModel.getNumberOfRows(); i++){
            for (int j = 0; j < gameModel.getNumberOfColumns(); j++){
                c = new Coordinate(i, j);
                numInARow = checkWin(c, nextCell(c, SearchDirection.RIGHT), SearchDirection.RIGHT);
                if (numInARow >= gameModel.getWinThreshold()){
                    return true;
                }
                numInARow = checkWin(c, nextCell(c, SearchDirection.RDIAG), SearchDirection.RDIAG);
                if (numInARow >= gameModel.getWinThreshold()){
                    return true;
                }
                numInARow = checkWin(c, nextCell(c, SearchDirection.DOWN), SearchDirection.DOWN);
                if (numInARow >= gameModel.getWinThreshold()){
                    return true;
                }
                numInARow = checkWin(c, nextCell(c, SearchDirection.LDIAG), SearchDirection.LDIAG);
                if (numInARow >= gameModel.getWinThreshold()){
                    return true;
                }
            }
        }
        return false;
    }

    private int checkWin(Coordinate prevC, Coordinate nextC, SearchDirection direction){
        char prevLet, nextLet;
        if (prevC == null || nextC == null){
            return 1;
        }
        if (gameModel.getCellOwner(prevC.getRow(), prevC.getCol()) == null){
            return 1;
        }
        if (gameModel.getCellOwner((nextC.getRow()), nextC.getCol()) == null){
            return 1;
        }
        prevLet = gameModel.getCellOwner(prevC.getRow(), prevC.getCol()).getPlayingLetter();
        nextLet = gameModel.getCellOwner(nextC.getRow(), nextC.getCol()).getPlayingLetter();
        if (prevLet != nextLet){
            return 1;
        }
        return 1 + checkWin(nextC, nextCell(nextC, direction), direction);

    }

    private Coordinate nextCell(Coordinate c, SearchDirection direction){
        Coordinate newC;
        switch (direction){
            case RIGHT:
                newC = new Coordinate(c.getRow(), c.getCol() + 1);
                break;
            case RDIAG:
                newC = new Coordinate(c.getRow() + 1, c.getCol() + 1);
                break;
            case DOWN:
                newC = new Coordinate(c.getRow() + 1, c.getCol());
                break;
            case LDIAG:
                newC = new Coordinate(c.getRow() + 1, c.getCol() - 1);
                break;
            default:
                return null;
        }
        if (isInBounds(newC)){
            return newC;
        }
        return null;
    }

    private boolean isInBounds(Coordinate c){
        int row = c.getRow(), col = c.getCol();
        if (row < 0 || row >= gameModel.getNumberOfRows()){
            return false;
        }
        return col >= 0 && col < gameModel.getNumberOfColumns();
    }

    private boolean isGameDrawn(){
        int count = 0;
        for (int i = 0; i < gameModel.getNumberOfRows(); i++){
            for (int j = 0; j < gameModel.getNumberOfColumns(); j++){
                if (gameModel.getCellOwner(i, j)!= null){
                    count++;
                }
            }
        }
        return count == gameModel.getNumberOfRows() * gameModel.getNumberOfColumns();
    }

    private int charToInt(char c){
        int val = -1;
        if (Character.isAlphabetic((c))){
            c = Character.toTitleCase(c);
            val = (c - 'A');
        }
        return val;
    }

    private int digitToInt(char c){
        int val = - 1;
        if (Character.isDigit(c)){
            val = (c - '0') - 1;
        }
        return val;
    }

}
