package Exception;

public class InvalidOptionException extends Exception {
    public InvalidOptionException() {
        super("\uF0FB The option you introduced is not valid! Please try again! \uF0FB");
    }
}
