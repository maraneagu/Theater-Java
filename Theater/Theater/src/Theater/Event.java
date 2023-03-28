package Theater;

import Theater.Spectacle.Spectacle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Event implements Comparable<Event>{
    private int id;
    private Spectacle spectacle;
    private String time;
    private String date;
    private Stage stage;
    private List<List<Boolean>> seats;
    private double price;

    public Event() {
        this.stage = new Stage();
        this.seats = new ArrayList<>();
    }
    public Event(Spectacle spectacle, Stage stage) {
        this.spectacle = spectacle;
        this.stage = stage;
        this.seats = new ArrayList<>();
        for (int i = 0; i < stage.getNumberOfRows(); i++)
        {
            seats.add(new ArrayList<>());
            for (int j = 0; j < stage.getNumberOfSeatsPerRow(); j++)
                seats.get(i).add(true);
        }
    }

    public void toRead() {
        Scanner in = new Scanner(System.in);

        System.out.println("The date of the event! The format: dd.mm.yyyyy!");
        System.out.print("Enter the date of the event: ");
        this.date = in.nextLine().trim();

        while (true)
        {
            System.out.println("The time of the event! It should be between 11 AM and 11 PM, with the format: hh:mm!");
            System.out.print("Enter the time of the event: ");
            String time = in.nextLine().trim();

            String[] splitTime = time.split(":");
            int hour = Integer.parseInt(splitTime[0]);
            int minutes = Integer.parseInt(splitTime[1]);

            if (hour < 11 || hour > 22 || minutes > 59)
                System.out.println("\uF0FB The time you introduced is not valid! Please try again! \uF0FB\n");
            else {
                this.time = time;
                break;
            }
        }

        System.out.println("The price of the event! It should have two digits, with the format: number:dd!");
        System.out.print("Enter the price of the event: ");
        this.price = Double.parseDouble(in.nextLine().trim());

        System.out.println();
    }
    @Override
    public String toString(){
        return "\uF0B2 Event \uF0B2" + '\n' +
                "The name of the spectacle: " + '"' + spectacle.getName() + '"' + '\n' +
                "The date of the event: " + date + '\n' +
                "The time the event starts: " + time + '\n' +
                "The stage the spectacle is performed at: " + stage.getName() + '\n' +
                "The price of the event: " + price + " lei";
    }

    public int compareDates(String string1, String string2) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date1 = simpleDateFormat.parse(string1);
        Date date2 = simpleDateFormat.parse(string2);

        return date2.compareTo(date1);
    }

    public int compareTime(String string1, String string2) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date time1 = simpleDateFormat.parse(string1);
        Date time2 = simpleDateFormat.parse(string2);

        if (time2.after(time1)) return 1;
        else if (time2.before(time1)) return -1;
        return 0;
    }

    @Override
    public int compareTo(Event event) {
        int comparator;

        try { comparator = compareDates(date, event.date); }
        catch(ParseException parseException) { throw new RuntimeException(parseException); }

        if (comparator == 1) return 1;
        else if (comparator == 0)
        {
            try { comparator = compareTime(time, event.time); }
            catch(ParseException parseException) { throw new RuntimeException(parseException); }

            if (comparator == 1) return 1;
            else if (comparator == 0) return 0;
            return -1;
        }
        return -1;
    }

    public Spectacle getSpectacle() {
        return spectacle;
    }
    public void setSpectacle(Spectacle spectacle) {
        this.spectacle = spectacle;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public List<List<Boolean>> getSeats() {
        return seats;
    }
    public void setSeats(List<List<Boolean>> seats) {
        this.seats = seats;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
