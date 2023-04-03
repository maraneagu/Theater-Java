package Service;

import Theater.Event;
import Theater.Spectacle.Spectacle;
import Theater.Stage;
import Exception.TheaterException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventService {
    private final TheaterService theaterService;

    public EventService() {
        theaterService = TheaterService.getInstance();
    }

    public Event readEvent(Spectacle spectacle)
    {
        Scanner in = new Scanner(System.in);

        String sStageId;
        int stageId;
        Map<Integer, Stage> stages = theaterService.getStages();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String date, beginTime, endTime;

        List<Event> events = theaterService.getEvents();
        List<Event> dateStageEvents = new ArrayList<>();

        System.out.println("\n\uF0B2 The theater's stages \uF0B2");
        for (Map.Entry<Integer, Stage> stage : stages.entrySet())
            System.out.println(stage.getKey() + ". " + stage.getValue().getName());
        System.out.println();

        while (true)
        {
            System.out.print("Enter the number of the stage " +
                    "where the event will take place: ");
            sStageId = in.nextLine().trim();

            if (sStageId.compareTo("1") >= 0 && sStageId.compareTo(Integer.toString(stages.size())) <= 0)
                break;
            System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
        }
        stageId = Integer.parseInt(sStageId);

        while (true)
        {
            System.out.println("\nThe date of the event! The format: dd.mm.yyyyy!");
            System.out.print("Enter the date of the event: ");
            date = in.nextLine().trim();

            try
            {
                dateFormat.parse(date);

                String[] splitDate = date.split("\\.");
                int day = Integer.parseInt(splitDate[0]);
                int month = Integer.parseInt(splitDate[1]);
                int year = Integer.parseInt(splitDate[2]);

                if (year < currentYear)
                    throw new TheaterException("\uF0FB You can not add an event in the past! Please try again! \uF0FB");
                else if (year == currentYear)
                {
                    if (month < currentMonth)
                        throw new TheaterException("\uF0FB You can not add an event in the past! Please try again! \uF0FB");
                    else if (month == currentMonth)
                        if (day < currentDay)
                            throw new TheaterException("\uF0FB You can not add an event in the past! Please try again! \uF0FB");
                }
                break;
            }
            catch (ParseException parseException)
            {
                System.out.println("\uF0FB The date format you introduced is not valid! Please try again! \uF0FB");
            }
            catch (TheaterException dateException)
            {
                System.out.println(dateException.getMessage());
            }
        }

        System.out.println("\n\uF0B2 The events performed at " + stages.get(stageId).getName() + " on " + date + " \uF0B2");

        Collections.sort(events);
        Collections.reverse(events);

        for (Event e : events) {
            if (e.getStage().equals(stages.get(stageId)) && e.getDate().equals(date)) {
                dateStageEvents.add(e);
                System.out.println("\uF09F " + '"' + e.getSpectacle().getName() + '"' + " from " + e.getBeginTime() + " to " +
                        toTime(e.getBeginTime(), e.getSpectacle().getDuration()));
            }
        }
        if (dateStageEvents.isEmpty()) System.out.println("There are not events as of yet.");
        System.out.println();

        while (true) {
            System.out.println("The time of the event! It should be between 11 O'Clock and 23 O'Clock, with the format: hh:mm!");
            System.out.print("Enter the time of the event: ");
            beginTime = in.nextLine().trim();

            try {
                timeFormat.parse(beginTime);

                String[] splitTime = beginTime.split(":");
                int hour = Integer.parseInt(splitTime[0]);
                int minutes = Integer.parseInt(splitTime[1]);

                if (hour < 11 || hour > 22 || minutes > 59)
                    throw new TheaterException("\uF0FB The time you introduced is not valid! Please try again! \uF0FB\n");

                endTime = toTime(beginTime, spectacle.getDuration());
                if (!checkTime(beginTime, endTime, dateStageEvents))
                {
                    String choiceToContinue;

                    System.out.println("\nThe event would end at " + endTime + ", thus we are not able to find an appropriate time frame for the event!");
                    System.out.println("Do you want to try entering another time frame? yes / no");
                    choiceToContinue = in.nextLine().trim();

                    if (!choiceToContinue.equalsIgnoreCase("yes"))
                    {
                        System.out.println("Do you want to change the stage or the date the event is performed at? yes / no");
                        choiceToContinue = in.nextLine().trim();
                        if (choiceToContinue.equalsIgnoreCase("yes"))
                        {
                            readEvent(spectacle);
                            return null;
                        }
                        return null;
                    }
                    else throw new TheaterException("");
                }
                break;
            }
            catch (ParseException parseException)
            {
                System.out.println("\uF0FB The time format you introduced is not valid! Please try again! \uF0FB\n");
            }
            catch (TheaterException timeException)
            {
                System.out.println(timeException.getMessage());
            }
        }

        System.out.println("\nThe price of the event! It should have two digits, with the format: number.dd!");
        System.out.print("Enter the price of the event: ");
        Double price = Double.parseDouble(in.nextLine().trim());

        Event event = new Event(spectacle, stages.get(stageId), date, beginTime, endTime, price);
        return event;
    }

    public void addEvent() {
        System.out.println("\uF0B2 The theater's spectacles \uF0B2");

        Map<Integer, Spectacle> spectacles = theaterService.getSpectacles();

        if (spectacles.isEmpty())
            System.out.println("There are no spectacles as of yet.");
        else {
            Scanner in = new Scanner(System.in);
            List<Event> events = theaterService.getEvents();

            String spectacleId;
            for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                System.out.println(spectacle.getKey() + ". " + '"' + spectacle.getValue().getName() + '"');
            System.out.println();

            while (true) {
                System.out.print("Enter the number of the spectacle " +
                        "that you want to add to the event: ");
                spectacleId = in.nextLine().trim();

                if (spectacleId.compareTo("1") >= 0 && spectacleId.compareTo(Integer.toString(spectacles.size())) <= 0)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            Event event = new Event();
            event = readEvent(spectacles.get(Integer.parseInt(spectacleId)));
            events.add(event);

            System.out.println("\n\uF0B2 A new event was added to theater's events list! \n");
        }
    }

    public void removeEvent() {
        System.out.println("\uF0B2 The theater's events \uF0B2");

        List<Event> events = theaterService.getEvents();

        if (events.isEmpty())
            System.out.println("There are no events as of yet.\n");
        else {
            int i = 0, eventId;
            String sEventId;

            for (Event event : events) {
                i++;
                System.out.println(i + ". " + '"' + event.getSpectacle().getName() +
                        '"' + " performed at the " + event.getStage().getName());
            }
            System.out.println();

            while (true) {
                Scanner in = new Scanner(System.in);
                System.out.print("Enter the number of the event " +
                        "that you want to remove: ");
                sEventId = in.nextLine().trim();

                if (sEventId.compareTo("1") >= 0 && sEventId.compareTo(Integer.toString(events.size())) <= 0)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            eventId = Integer.parseInt(sEventId);
            TicketShopService ticketShopService = TicketShopService.getInstance();
            ticketShopService.removeTicketByEvent(events.get(eventId - 1));

            System.out.println("\uF0B2 The event " + '"' + events.get(eventId - 1).getSpectacle().getName() +
                    '"' + " that starts at " + events.get(eventId - 1).getBeginTime() +
                    " performed at the " + events.get(eventId - 1).getStage().getName() + "" +
                    " was removed from the theater's events list! \n");
            events.remove(eventId - 1);
        }
    }

    public void removeEventBySpectacle(Spectacle spectacle) {
        List<Event> events = theaterService.getEvents();
        TicketShopService ticketShopService = TicketShopService.getInstance();

        for (Event event : events)
            if (event.getSpectacle() == spectacle)
                ticketShopService.removeTicketByEvent(event);

        events.removeIf(event -> event.getSpectacle() == spectacle);
    }

    public void listEvents() {
        System.out.println("\uF0B2 The theater's events \uF0B2");

        List<Event> events = theaterService.getEvents();

        if (events.isEmpty())
            System.out.println("\nThere are no events as of yet.\n");
        else {
            Collections.sort(events);
            for (Event event : events)
                System.out.println("\uF09F " + '"' + event.getSpectacle().getName() +
                        '"' + " that starts at " + event.getBeginTime() + "on " + event.getDate() +
                        " performed at the " + event.getStage().getName() + " stage");
            System.out.println();
        }
    }

    public void listEventDetails() {
        System.out.println("\uF0B2 The theater's events \uF0B2");

        List<Event> events = theaterService.getEvents();

        if (events.isEmpty())
            System.out.println("There are no events as of yet.\n");
        else
        {
            int i;
            String eventId;

            for (i = 0; i < events.size(); i++)
                System.out.println(i + 1 + ". " + '"' + events.get(i).getSpectacle().getName() + '"');
            System.out.println();

            while (true) {
                Scanner in = new Scanner(System.in);
                System.out.print("Enter the number of the event " +
                        "for which you want to list the information: ");
                eventId = in.nextLine().trim();

                if (eventId.compareTo("1") >= 0 && eventId.compareTo(Integer.toString(events.size())) <= 0)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            System.out.println();
            System.out.println(events.get(Integer.parseInt(eventId) - 1));
            System.out.println();
        }
    }

    public String toTime(String fromTime, String duration) {
        String[] date = fromTime.split(":");
        int fromHour = Integer.parseInt(date[0]);
        int fromMinutes = Integer.parseInt(date[1]);

        date = duration.split(":");
        int durationHour = Integer.parseInt(date[0]);
        int durationMinutes = Integer.parseInt(date[1]);

        int toMinutes = (fromMinutes + durationMinutes) % 60;
        int toHour = fromHour + durationHour + (fromMinutes + durationMinutes) / 60;

        return toHour + ":" + toMinutes;
    }

    public boolean checkTime(String beginTime, String endTime, List<Event> events) {
        if (events.isEmpty())
            return true;

        if (endTime.compareTo(events.get(0).getBeginTime()) <= 0)
            return true;

        for (int i = 0; i < events.size() - 1; i++)
            if (beginTime.compareTo(events.get(i).getEndTime()) >= 0)
                if (endTime.compareTo(events.get(i + 1).getBeginTime()) <= 0)
                    return true;

        if (beginTime.compareTo(events.get(events.size() - 1).getEndTime()) >= 0)
            return true;

        return false;
    }
}