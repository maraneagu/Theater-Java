package Main;

import Theater.Theater;

public class Main {
    public static void main(String[] args) {
        Theater theater = Theater.getInstance();
        theater.start();
    }
}