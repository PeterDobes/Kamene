package stonesPuzzle.engine;

import java.io.Serializable;

public class Stone implements Serializable {
    private int value;

    public Stone(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
