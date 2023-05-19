package Exception;

public class InvalidDurationException extends Exception {
    public InvalidDurationException() {
        super("\uF0FB The duration you introduced is not valid! Please try again! \uF0FB");
    }
}
