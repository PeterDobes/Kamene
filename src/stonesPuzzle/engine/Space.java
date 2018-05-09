package stonesPuzzle.engine;

import java.io.Serializable;

public class Space implements Serializable {
    private int row;
    private int col;

    public Space(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
