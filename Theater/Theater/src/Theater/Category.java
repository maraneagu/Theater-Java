package Theater;

import java.util.Scanner;

public class Category {
    private int id;
    private String name;

    public Category() {}
    public Category(String name) {
        this.name = name;
    }

    public void toRead() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the name of the category: ");
        this.name = in.nextLine();
    }
    @Override
    public String toString(){
        return "\uF09F " + name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
