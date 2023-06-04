package Exception;

public class InvalidNumberException extends Exception {
    public InvalidNumberException() {
        super("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
    }
}
