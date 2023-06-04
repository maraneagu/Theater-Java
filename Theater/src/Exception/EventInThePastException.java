package Exception;

public class EventInThePastException extends Exception {
    public EventInThePastException() {
        super("\uF0FB You can not add an event in the past! Please try again! \uF0FB");
    }
}
