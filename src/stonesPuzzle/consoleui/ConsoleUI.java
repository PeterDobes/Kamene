package stonesPuzzle.consoleui;

import stonesPuzzle.BestTimes;
import stonesPuzzle.StonesPuzzle;
import stonesPuzzle.UserInterface;
import stonesPuzzle.engine.Field;
import stonesPuzzle.engine.Space;
import stonesPuzzle.engine.Stone;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI implements UserInterface {
    private Field field;
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void newGame(Field field) {
        this.field = field;
        StonesPuzzle.getInstance().restartMillis();
        field.setTime(0);
        do {
            update();
            processInput();
            if (field.isSolved()) {
                update();
                System.out.println("Solved! Enter your name:");
                field.getBestTimes().addPlayerTime(readLine(), (int) field.getTime());
                field.scramble();
                field.setTime(0);
                saveField();
                System.exit(0);
            }
        } while(true);
    }

    public void update() {
        System.out.println((StonesPuzzle.getInstance().getPlayingTime() + field.getTime())+" seconds");
        System.out.println();
        for (int i = 0; i < field.getRowCount(); i++) {
            for (int j = 0; j < field.getColumnCount(); j++) {
                if (field.getStone(i,j).getValue() < field.getStoneCount()) {
                    int value = field.getStone(i,j).getValue();
                    if (value < 10) {
                        System.out.print("  " + value + " ");
                    } else {
                        System.out.print(" " + value + " ");
                    }
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println();
        }
    }

    private void processInput() {
        System.out.println();
        System.out.println("Command: Move~<W, A, S, D>; Exit~<exit>; Scramble~<New>");
        try {
            handleInput(readLine());
        } catch (WrongFormatException e) {
            System.err.println(e.getMessage());
        }
    }

    private void handleInput(String input) throws WrongFormatException {
        boolean inNok = true;
        while (inNok) {
            Pattern p = Pattern.compile("(?i)(exit)?(new)?([wasd]?)");
            Matcher m = p.matcher(input.toLowerCase());
            int sRow = field.getSpace().getRow();
            int sCol = field.getSpace().getCol();
            if (m.matches()) {
                inNok = false;
                switch (m.group(0)) {
                    case "exit":
                        field.setTime(StonesPuzzle.getInstance().getPlayingTime() + field.getTime());
                        saveField();
                        System.exit(0);
                        break;
                    case "new":
                        field.scramble();
                        newGame(field);
                        break;
                    case "w":
                        move(sRow, sCol, sRow +1, sCol);
                        break;
                    case "a":
                        move(sRow, sCol, sRow, sCol +1);
                        break;
                    case "s":
                        move(sRow, sCol, sRow -1, sCol);
                        break;
                    case "d":
                        move(sRow, sCol, sRow, sCol -1);
                    default:
                        break;
                }
            } else {
                throw new WrongFormatException("Wrong input");
            }
        }
    }

    private void saveField() {
        try (FileOutputStream os = new FileOutputStream(field.getFILENAME());
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(field);
            System.out.println("Game saved successfully");
        } catch (IOException e) {
            System.err.println(e.getMessage()+"Saving failed");
        }
    }

    private void move(int sRow, int sCol, int mRow, int mCol) {
        if (mRow > -1 && mRow < field.getRowCount() && mCol > -1 && mCol < field.getColumnCount()) {
            Stone space = field.getStone(sRow, sCol);
            int moved = field.getStone(mRow, mCol).getValue();
            field.getStone(mRow, mCol).setValue(field.getStoneCount());
            space.setValue(moved);
            field.setSpace(new Space(mRow, mCol));
        }
    }
}
