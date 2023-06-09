package Theater;

import Service.EventService;
import Theater.Spectacle.Spectacle;
import Service.TheaterService;

import java.text.*;
import java.util.*;

public class Event implements Comparable<Event>{
    private Spectacle spectacle;
    private Stage stage;
    private String date;
    private String beginTime;
    private String endTime;
    private double price;
    private List<List<Boolean>> seats;
    private TheaterService theaterService;

    public Event() {
        this.stage = new Stage();
        this.seats = new ArrayList<>();
    }
    public Event(Spectacle spectacle, Stage stage, String date, String beginTime, String endTime, Double price) {
        this.spectacle = spectacle;
        this.stage = stage;
        this.seats = new ArrayList<>();
        for (int i = 0; i < stage.getNumberOfRows(); i++)
        {
            seats.add(new ArrayList<>());
            for (int j = 0; j < stage.getNumberOfSeatsPerRow(); j++)
                seats.get(i).add(true);
        }
        this.date = date;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.price = price;
    }

    @Override
    public String toString(){
        return "\uF0B2 Event \uF0B2" + '\n' +
                "The name of the spectacle: " + '"' + spectacle.getName() + '"' + '\n' +
                "The date of the event: " + date + '\n' +
                "The timeframe: " + beginTime + " to " + endTime + '\n' +
                "The stage the spectacle is performed at: " + stage.getName() + " stage\n" +
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
            try { comparator = compareTime(beginTime, event.beginTime); }
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
    public String getBeginTime() {
        return beginTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public String getDate() {
        return date;
    }
    public Stage getStage() {
        return stage;
    }
    public Double getPrice() {return price;}
    public List<List<Boolean>> getSeats() {
        return seats;
    }
}
