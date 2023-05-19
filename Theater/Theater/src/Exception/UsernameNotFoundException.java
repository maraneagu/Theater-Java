package Exception;

public class UsernameNotFoundException extends Exception {
    public UsernameNotFoundException() {
        super("\uF0FB The username you introduced doesn't exist! \uF0FB\n");
    }
}
