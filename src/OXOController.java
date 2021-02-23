import OXOExceptions.*;

import java.sql.SQLOutput;

class OXOController
{
    OXOModel gameModel;
    private int numPlayers = 2;
    private int turnNumber = 0;

    public OXOController(OXOModel model)
    {
        gameModel = model;
        System.out.println("total rows: " + model.getNumberOfRows() + " total cols: " + model.getNumberOfColumns());
        model.setCurrentPlayer(gameModel.getPlayerByNumber(0));
    }

    public void handleIncomingCommand(String command) throws OXOMoveException
    {
        int row = charToInt(command.charAt(0));
        int col = digitToInt(command.charAt(1));
        //error checking can happen here
        System.out.println("row: " + row + " col: " + col);

        System.out.println("current player: " + gameModel.getCurrentPlayer().getPlayingLetter());
        System.out.println("turn: " + turnNumber);
        if (gameModel.getCellOwner(row, col) == null){
            turnNumber++;
            gameModel.setCellOwner(row, col, gameModel.getCurrentPlayer());
            gameModel.setCurrentPlayer(gameModel.getPlayerByNumber(turnNumber % numPlayers));
        }
        else{
            System.out.println("This spot is already filled......");
        }

    }

    private int charToInt(char c){
        int val = -1;
        if (Character.isAlphabetic((c)) == true){
            c = Character.toTitleCase(c);
            val = (int)(c - 'A');
        }
        return val;
    }

    private int digitToInt(char c){
        int val = - 1;
        if (Character.isDigit(c) == true){
            val = (int) (c - '0') - 1;
        }
        return val;
    }

    private boolean checkIsOutOfBounds(int row, int col){
        if (row == 0 || row == gameModel.getNumberOfRows() - 1){
            return false;
        }
        if (col == 0 || col == gameModel.getNumberOfColumns() - 1){
            return false;
        }
        return true;
    }
}
