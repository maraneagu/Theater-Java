package Theater;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket implements Comparable<Ticket> {
    private String username;
    private Event event;
    private int row;
    private int seat;

    public Ticket() {
        this.event = new Event();
    }
    public Ticket(String username, Event event, int row, int seat) {
        this.username = username;
        this.event = event;
        this.row = row;
        this.seat = seat;
    }

    @Override
    public String toString() {
        return "\uF0B2 Ticket \uF0B2" + '\n' +
                "The name of the spectacle: " + '"' + event.getSpectacle().getName() + '"' + '\n' +
                "The date of the event: " + event.getDate() + '\n' +
                "The time the event starts: " + event.getBeginTime() + '\n' +
                "The stage the spectacle is performed at: " + event.getStage().getName() + '\n' +
                "Your seat: row " + row + " , seat: " + seat + '\n';
    }

    public int compareDates(String string1, String string2) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date1 = simpleDateFormat.parse(string1);
        Date date2 = simpleDateFormat.parse(string2);

        return date1.compareTo(date2);
    }

    public int compareTime(String string1, String string2) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date time1 = simpleDateFormat.parse(string1);
        Date time2 = simpleDateFormat.parse(string2);

        if (time1.after(time2)) return 1;
        else if (time1.before(time2)) return -1;
        return 0;
    }

    @Override
    public int compareTo(Ticket ticket) {
        int comparator;

        try { comparator = compareDates(event.getDate(), ticket.event.getDate()); }
        catch(ParseException parseException) { throw new RuntimeException(parseException); }

        if (comparator == 1) return 1;
        else if (comparator == 0)
        {
            try { comparator = compareTime(event.getBeginTime(), ticket.event.getBeginTime()); }
            catch(ParseException parseException) { throw new RuntimeException(parseException); }

            if (comparator == 1) return 1;
            else if (comparator == 0) return 0;
            return -1;
        }
        return -1;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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
