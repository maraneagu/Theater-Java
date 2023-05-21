package Service;

import Audit.Audit;
import Repository.EventRepository;
import Theater.Event;
import Theater.Spectacle.Musical;
import Theater.Spectacle.Opera;
import Theater.Spectacle.Play;
import Theater.Spectacle.Spectacle;
import Theater.Stage;
import Exception.*;

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
            try
            {
                System.out.print("Enter the number of the stage " +
                        "where the event will take place: ");
                sStageId = in.nextLine().trim();

                if (sStageId.compareTo("1") >= 0 && sStageId.compareTo(Integer.toString(stages.size())) <= 0)
                    break;
                else throw new InvalidNumberException();
            }
            catch (InvalidNumberException exception)
            {
                System.out.println(exception.getMessage());
            }
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
                    throw new EventInThePastException();
                else if (year == currentYear)
                {
                    if (month < currentMonth)
                        throw new EventInThePastException();
                    else if (month == currentMonth)
                        if (day < currentDay)
                            throw new EventInThePastException();
                }
                break;
            }
            catch (ParseException parseException)
            {
                System.out.println("\uF0FB The date format you introduced is not valid! Please try again! \uF0FB");
            }
            catch (EventInThePastException exception)
            {
                System.out.println(exception.getMessage());
            }
        }

        System.out.println("\n\uF0B2 The events performed at " + stages.get(stageId).getName() + " on " + date + " \uF0B2");

        if (events.isEmpty())
            System.out.println("There are not events as of yet.");
        else
        {
            Collections.sort(events);
            Collections.reverse(events);

            for (Event e : events)
            {
                if (e.getStage().getName().equals(stages.get(stageId).getName()) && e.getDate().equals(date))
                {
                    dateStageEvents.add(e);
                    System.out.println("\uF09F " + '"' + e.getSpectacle().getName() + '"' + " from " + e.getBeginTime() + " to " +
                            toTime(e.getBeginTime(), e.getSpectacle().getDuration()));
                }
            }
            if (dateStageEvents.isEmpty()) System.out.println("There are no events as of yet.");
        }
        System.out.println();

        while (true)
        {
            System.out.println("The time of the event! It should be between 11 O'Clock and 23 O'Clock, with the format: hh:mm!");
            System.out.print("Enter the time of the event: ");
            beginTime = in.nextLine().trim();

            try
            {
                timeFormat.parse(beginTime);

                String[] splitTime = beginTime.split(":");
                int hour = Integer.parseInt(splitTime[0]);
                int minutes = Integer.parseInt(splitTime[1]);

                if (hour < 11 || hour > 22 || minutes > 59)
                    System.out.println("\uF0FB The time you introduced is not valid! Please try again! \uF0FB\n");

                endTime = toTime(beginTime, spectacle.getDuration());

                if (!checkTime(beginTime, endTime, dateStageEvents))
                {
                    System.out.println("\n\uF0FB The event would end at " + endTime + ", thus we are not able to find an appropriate time frame for the event! \uF0FB\n");

                    System.out.println("Here are your options: ");
                    System.out.println("1. Try entering another time frame for this event.");
                    System.out.println("2. Change the stage or the date of this event.");

                    while (true)
                    {
                        try
                        {
                            System.out.print("\nWhich option do you choose? Option: ");

                            String optionChoice = in.nextLine().trim();

                            if (optionChoice.equalsIgnoreCase("1"))
                            {
                                System.out.println();
                                break;
                            }
                            else if (optionChoice.equalsIgnoreCase("2"))
                                return readEvent(spectacle);
                            else throw new InvalidOptionException();
                        }
                        catch (InvalidOptionException exception)
                        {
                            System.out.println(exception.getMessage());
                        }
                    }
                }
                else break;
            }
            catch (ParseException parseException)
            {
                System.out.println("\uF0FB The time format you introduced is not valid! Please try again! \uF0FB\n");
            }
        }

        System.out.println("\nThe price of the event! It should have two digits, with the format: number.dd!");
        System.out.print("Enter the price of the event: ");
        Double price = Double.parseDouble(in.nextLine().trim());

        Event event = new Event(spectacle, stages.get(stageId), date, beginTime, endTime, price);
        return event;
    }

    public void addEvent()
    {
        List<Spectacle> spectacles = theaterService.getSpectacles();

        System.out.println("\uF0B2 The theater's spectacles \uF0B2");

        if (spectacles.isEmpty())
            System.out.println("There are no spectacles as of yet.");
        else
        {
            Scanner in = new Scanner(System.in);
            List<Event> events = theaterService.getEvents();

            String sSpectacleId;
            int spectacleId = 1;
            String spectacleType;

            for (Spectacle spectacle : spectacles)
            {
                if (spectacle instanceof Play)
                    spectacleType = "play";
                else if (spectacle instanceof Opera)
                    spectacleType = "opera";
                else if (spectacle instanceof Musical)
                    spectacleType = "musical";
                else spectacleType = "ballet";

                System.out.println(spectacleId + ". " +
                        '"' + spectacle.getName() + '"' + " \uF09E " + spectacleType);
                spectacleId += 1;
            }

            System.out.println();

            while (true)
            {
                try
                {
                    System.out.print("Enter the number of the spectacle " +
                            "that you want to add to the event: ");
                    sSpectacleId = in.nextLine().trim();

                    if (sSpectacleId.compareTo("1") >= 0 && sSpectacleId.compareTo(Integer.toString(spectacles.size())) <= 0)
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            Event event;
            event = readEvent(spectacles.get(Integer.parseInt(sSpectacleId) - 1));
            events.add(event);

            EventRepository eventRepository = EventRepository.getInstance();
            eventRepository.insertEvent(event);

            Audit audit = Audit.getInstance();
            audit.writeToFile("A new event was added to the theater's events list: " + '"' + event.getSpectacle().getName() + '"' +
                    " performed at " + event.getStage().getName() + " on " + event.getDate() + ", starting at " + event.getBeginTime(), "./theaterEvents.cvs");
            System.out.println("\n\uF0B2 A new event was added to the theater's events list! \n");
        }
    }

    public void removeEvent()
    {
        List<Event> events = theaterService.getEvents();

        System.out.println("\uF0B2 The theater's events \uF0B2");

        if (events.isEmpty())
            System.out.println("There are no events as of yet.\n");
        else
        {
            int eventId = 1;
            String sEventId;

            for (Event event : events)
            {
                System.out.println(eventId + ". " + '"' + event.getSpectacle().getName() +
                        '"' + " performed at the " + event.getStage().getName() + " on " + event.getDate() + " at " + event.getBeginTime());
                eventId += 1;
            }
            System.out.println();

            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);
                    System.out.print("Enter the number of the event " +
                            "that you want to remove: ");
                    sEventId = in.nextLine().trim();

                    if (sEventId.compareTo("1") >= 0 && sEventId.compareTo(Integer.toString(events.size())) <= 0)
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            eventId = Integer.parseInt(sEventId);
            TicketService ticketShopService = TicketService.getInstance();
            ticketShopService.removeTicketByEvent(events.get(eventId - 1));

            System.out.println("\n\uF04A The event " + '"' + events.get(eventId - 1).getSpectacle().getName() +
                    '"' + " performed at the " + events.get(eventId - 1).getStage().getName() + "" + " on " + events.get(eventId - 1).getDate() +
                    " that starts at " + events.get(eventId - 1).getBeginTime() +
                    " was removed from the theater's events list! \n");

            Audit audit = Audit.getInstance();
            audit.writeToFile("The event " + '"' + events.get(eventId - 1).getSpectacle().getName() +
            '"' + " performed at the " + events.get(eventId - 1).getStage().getName() + "" + " on " + events.get(eventId - 1).getDate() +
                    " that starts at " + events.get(eventId - 1).getBeginTime() +
                    " was removed from the theater's events list!", "./theaterEvents.cvs");

            EventRepository eventRepository = EventRepository.getInstance();
            eventRepository.deleteEvent(events.get(eventId - 1));
            events.remove(eventId - 1);
        }
    }

    public void removeEventBySpectacle(Spectacle spectacle)
    {
        List<Event> events = theaterService.getEvents();
        TicketService ticketShopService = TicketService.getInstance();

        for (Event event : events)
            if (event.getSpectacle() == spectacle)
                ticketShopService.removeTicketByEvent(event);

        events.removeIf(event -> event.getSpectacle() == spectacle);
    }

    public void listEvents()
    {
        List<Event> events = theaterService.getEvents();

        System.out.println("\uF0B2 The theater's events \uF0B2");

        if (events.isEmpty())
            System.out.println("There are no events as of yet.\n");
        else
        {
            Collections.sort(events);
            for (Event event : events)
                System.out.println("\uF09F " + '"' + event.getSpectacle().getName() +
                        '"' + " that starts at " + event.getBeginTime() + " on " + event.getDate() +
                        " performed at the " + event.getStage().getName() + " stage");
            System.out.println();
        }

        Audit audit = Audit.getInstance();
        audit.writeToFile("The theater's events were listed!", "./theaterEvents.cvs");
    }

    public void listEventDetails()
    {
        List<Event> events = theaterService.getEvents();

        System.out.println("\uF0B2 The theater's events \uF0B2");

        if (events.isEmpty())
            System.out.println("There are no events as of yet.\n");
        else
        {
            int eventId = 1;
            String sEventId;

            for (Event event: events)
            {
                System.out.println(eventId + ". " + '"' + event.getSpectacle().getName() + '"' + " on " + event.getDate());
                eventId += 1;
            }

            System.out.println();

            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);

                    System.out.print("Enter the number of the event " +
                            "for which you want to list the information: ");
                    sEventId = in.nextLine().trim();

                    if (sEventId.compareTo("1") >= 0 && sEventId.compareTo(Integer.toString(events.size())) <= 0)
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            Audit audit = Audit.getInstance();
            audit.writeToFile("The details about an event were listed!", "./theaterEvents.cvs");
            System.out.println("\n" + events.get(Integer.parseInt(sEventId) - 1) + "\n");
        }
    }

    public String toTime(String fromTime, String duration)
    {
        String[] time = fromTime.split(":");
        int fromHour = Integer.parseInt(time[0]);
        int fromMinutes = Integer.parseInt(time[1]);

        time = duration.split(":");
        int durationHour = Integer.parseInt(time[0]);
        int durationMinutes = Integer.parseInt(time[1]);

        int toMinutes = (fromMinutes + durationMinutes) % 60;
        int toHour = fromHour + durationHour + (fromMinutes + durationMinutes) / 60;

        if (toMinutes == 0)
            return toHour + ":" + toMinutes + '0';
        return toHour + ":" + toMinutes;
    }

    public boolean checkTime(String beginTime, String endTime, List<Event> events)
    {
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