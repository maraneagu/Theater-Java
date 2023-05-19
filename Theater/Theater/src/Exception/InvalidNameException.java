package Exception;

public class InvalidNameException extends Exception {
    public InvalidNameException() {
        super("\uF0FB The name you introduced is not valid! Please try again! \uF0FB");
    }
}
