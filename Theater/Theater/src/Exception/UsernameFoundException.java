package Exception;

public class UsernameFoundException extends Exception {
    public UsernameFoundException() {
        super("\uF0FB The username you introduced already exists! Please try again! \uF0FB\n");
    }
}
