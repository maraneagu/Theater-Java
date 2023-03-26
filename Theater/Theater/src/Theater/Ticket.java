package Theater;

public class Ticket {
    private int id;
    private String name;
    private Event event;
    private int row;
    private int seat;

    public Ticket() {
        this.event = new Event();
    }
    public Ticket(String name, Event event, int row, int seat) {
        this.name = name;
        this.event = event;
        this.row = row;
        this.seat = seat;
    }

    @Override
    public String toString() {
        return "\uF0B2 Ticket \uF0B2" + '\n' +
                "The name of the spectacle: " + '"' + event.getSpectacle().getName() + '"' + '\n' +
                "The date of the event: " + event.getDate() + '\n' +
                "The time the event starts: " + event.getTime() + '\n' +
                "The stage the spectacle is performed at: " + event.getStage().getName() + '\n' +
                "Your seat: row " + row + " , seat: " + seat + '\n';
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getSeat() {
        return seat;
    }
    public void setSeat(int seat) {
        this.seat = seat;
    }
}
