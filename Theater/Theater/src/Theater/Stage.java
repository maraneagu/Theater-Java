package Theater;

import java.util.Scanner;

public class Stage {
    private int id;
    private String name;
    private int numberOfRows;
    private int numberOfSeatsPerRow;

    public Stage() {}
    public Stage(String name, int numberOfRows, int numberOfSeatsPerRow) {
        this.name = name;
        this.numberOfRows = numberOfRows;
        this.numberOfSeatsPerRow = numberOfSeatsPerRow;
    }

    @Override
    public String toString() {
        return "\uF0B2 Stage \uF0B2" + '\n' +
                "The name of the stage: " + name + '\n' +
                "The number of rows: " + numberOfRows + '\n' +
                "The number of seats per row: " + numberOfSeatsPerRow + '\n';
    }

    public String getName() {
        return name;
    }
    public int getNumberOfRows() { return numberOfRows; }
    public int getNumberOfSeatsPerRow() {
        return numberOfSeatsPerRow;
    }
}
