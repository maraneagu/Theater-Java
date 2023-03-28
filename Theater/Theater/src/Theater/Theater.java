package Theater;

import Service.*;
import Service.ArtistService.ActorService;
import Service.ArtistService.DancerService;
import Service.ArtistService.SingerService;

import java.util.Scanner;

public class Theater {
    private static Theater singleInstance = null;

    private Theater() {}

    public static synchronized Theater getInstance() {
        if (singleInstance == null)
            singleInstance = new Theater();
        return singleInstance;
    }

    private void listGeneralTheaterActions() {
        System.out.println("\n\uF0B2 Signed as Mara \uF0B2\n");
        System.out.println("Your actions: ");
        System.out.println("\uF0B2");

        System.out.println("1). Visit the theater.");
        System.out.println("2). Visit the theater's ticket shop.");

        System.out.print("\nWhich action do you choose? Action: ");
    }
    private void generalTheaterActions() {
        Scanner in = new Scanner(System.in);

        while (true) {
            this.listGeneralTheaterActions();
            String choiceOfAction = in.nextLine().trim();

            if (choiceOfAction.equals("1")) {
                System.out.println("\uF046 The action you chose: visit the theater. \n");
                this.theaterActions();
            }
            else if (choiceOfAction.equals("2")) {
                System.out.println("\uF046 The action you chose: visit the theater's ticket shop. \n");
                this.ticketShopActions();
            }
            else {
                System.out.println("\n\uF0FB The action you introduced is not valid! Please try again! \uF0FB");
                this.generalTheaterActions();
                break;
            }

            System.out.println("Do you want to visit another part of the theater? " + "yes / no");
            String choiceToContinue = in.nextLine().trim();

            if (!choiceToContinue.equalsIgnoreCase("yes")) {
                System.out.println("\uF0AB Thank you for paying us a visit! " +
                        "We hope to see you soon! \uF0AB");
                break;
            }
        }
    }
    private void listTheaterActions() {
        System.out.println("\uF0B2 Signed as Mara \uF0B2\n");
        System.out.println("Your actions: ");
        System.out.println("\uF0B2");
        System.out.println("1). Add a new spectacle to the theater's spectacles list.");
        System.out.println("2). Remove a spectacle from the theater's spectacles list.");
        System.out.println("3). List the spectacles from the theater's spectacles list.");
        System.out.println("4). List the information about a spectacle from the theater's spectacles list.");
        System.out.println("5). List the spectacles from the theater's spectacles by their category.");

        System.out.println("\n\uF0B2");
        System.out.println("6). List the categories from the theater's categories list.");

        System.out.println("\n\uF0B2");
        System.out.println("7). List the directors from the theater's directors list.");

        System.out.println("\n\uF0B2");
        System.out.println("8). Add a new actor to a play from the theater's spectacles list.");
        System.out.println("9). Remove an actor from a play from the theater's spectacles list.");
        System.out.println("10). List the actors from a play from the theater's spectacles list.");

        System.out.println("\n\uF0B2");
        System.out.println("11). Add a new singer to an opera or musical from the theater's spectacles list.");
        System.out.println("12). Remove a singer from an opera or musical from the theater's spectacles list.");
        System.out.println("13). List the singers from an opera or musical from the theater's spectacles list.");

        System.out.println("\n\uF0B2");
        System.out.println("14). Add a new dancer to a musical or ballet from the theater's dancers list.");
        System.out.println("15). Remove an dancer from a musical or ballet from the theater's dancers list.");
        System.out.println("16). List the dancers from a musical or ballet from the theater's dancers list.");

        System.out.println("\n\uF0B2");
        System.out.println("17). List the stages from the theater's stages list.");
        System.out.println("18). List the information about a stage from the theater's stages list.");

        System.out.println("\n\uF0B2");
        System.out.println("19). Add an event to the theater's events list.");
        System.out.println("20). Remove an event from the theater's events list.");
        System.out.println("21). List the events from the theater's events list.");
        System.out.println("22). List the information about an event from the theater's events list.");
        System.out.println("23). Change the information about an event from the theater's events list.");

        System.out.print("\nWhich action do you choose? Action: ");
    }

    private void theaterActions() {
        Scanner in = new Scanner(System.in);

        SpectacleService spectacleService = new SpectacleService();
        CategoryService categoryService = new CategoryService();
        DirectorService directorService = new DirectorService();
        ActorService actorService = new ActorService();
        SingerService singerService = new SingerService();
        DancerService dancerService = new DancerService();
        StageService stageService = new StageService();
        EventService eventService = new EventService();

        while (true) {
            this.listTheaterActions();
            String choiceOfAction = in.nextLine().trim();

            if (choiceOfAction.equals("1")) {
                System.out.println("\uF046 The action you chose: add a spectacle to the theater's spectacles list. \n");
                spectacleService.addSpectacle();
            }
            else if (choiceOfAction.equals("2")) {
                System.out.println("\uF046 The action you chose: remove a spectacle from the theater's spectacles list. \n");
                spectacleService.removeSpectacle();
            }
            else if (choiceOfAction.equals("3")) {
                System.out.println("\uF046 The action you chose: list the spectacles from the theater's spectacles list. \n");
                spectacleService.listSpectacles();
            }
            else if (choiceOfAction.equals("4")) {
                System.out.println("\uF046 The action you chose: list the information about a spectacle from the theater's spectacles list. \n");
                spectacleService.listSpectacleDetails();
            }
            else if (choiceOfAction.equals("5")) {
                System.out.println("\uF046 The action you chose: list the the spectacles from the theater's spectacles list by their category. \n");
                spectacleService.listSpectacleByCategory();
            }
            else if (choiceOfAction.equals("6")) {
                System.out.println("\uF046 The action you chose: list the categories from the theater's categories list. \n");
                categoryService.listCategories();
            }
            else if (choiceOfAction.equals("7")) {
                System.out.println("\uF046 The action you chose: list the directors from the theater's directors list. \n");
                directorService.listDirectors();
            }
            else if (choiceOfAction.equals("8")) {
                System.out.println("\uF046 The action you chose: add an actor to a play from the theater's spectacles list. \n");
                actorService.addActor();
            }
            else if (choiceOfAction.equals("9")) {
                System.out.println("\uF046 The action you chose: remove an actor from a play from the theater's spectacles list. \n");
                actorService.removeActor();
            }
            else if (choiceOfAction.equals("10")) {
                System.out.println("\uF046 The action you chose: list the actors from a play from the theater's spectacles list. \n");
                actorService.listActors();
            }
            else if (choiceOfAction.equals("11")) {
                System.out.println("\uF046 The action you chose: add a new singer to an opera or musical from the theater's spectacles list. \n");
                singerService.addSinger();
            }
            else if (choiceOfAction.equals("12")) {
                System.out.println("\uF046 The action you chose: remove a singer from an opera or musical from the theater's spectacles list. \n");
                singerService.removeSinger();
            }
            else if (choiceOfAction.equals("13")) {
                System.out.println("\uF046 The action you chose: list the singers from an opera or musical from the theater's spectacles list. \n");
                singerService.listSingers();
            }
            else if (choiceOfAction.equals("14")) {
                System.out.println("\uF046 The action you chose: add a new dancer to a musical or ballet from the theater's spectacles list. \n");
                dancerService.addDancer();
            }
            else if (choiceOfAction.equals("15")) {
                System.out.println("\uF046 The action you chose: remove a dancer from a musical or ballet the theater's spectacles list. \n");
                dancerService.removeDancer();
            }
            else if (choiceOfAction.equals("16")) {
                System.out.println("\uF046 The action you chose: list the dancers from a musical or ballet from the theater's spectacles list. \n");
                dancerService.listDancers();
            }
            else if (choiceOfAction.equals("17")) {
                System.out.println("\uF046 The action you chose: list the stages from the theater's stages list. \n");
                stageService.listStages();
            }
            else if (choiceOfAction.equals("18")) {
                System.out.println("\uF046 The action you chose: list the information about a stage from the theater's stages list. \n");
                stageService.listStageDetails();
            }
            else if (choiceOfAction.equals("19")) {
                System.out.println("\uF046 The action you chose: add an event to the theater's events list. \n");
                eventService.addEvent();
            }
            else if (choiceOfAction.equals("20")) {
                System.out.println("\uF046 The action you chose: remove an event from the theater's events list. \n");
                eventService.removeEvent();
            }
            else if (choiceOfAction.equals("21")) {
                System.out.println("\uF046 The action you chose: list the events from the theater's events list. \n");
                eventService.listEvents();
            }
            else if (choiceOfAction.equals("22")) {
                System.out.println("\uF046 The action you chose: list the information about an event from the theater's events list. \n");
                eventService.listEventDetails();
            }
            else if (choiceOfAction.equals("23")) {
                System.out.println("\uF046 The action you chose: change the information about an event from the theater's events list. \n");
                eventService.changeEventDetails();
            }
            else {
                System.out.println("\n\uF0FB The action you introduced is not valid! Please try again! \uF0FB \n");
                this.theaterActions();
                break;
            }

            System.out.println("Do you want to make another action to the theater? " +
                    "yes / no");
            String choiceToContinue = in.nextLine().trim();

            if (!choiceToContinue.equalsIgnoreCase("yes")) {
                System.out.println("\uF0AB Thank you for paying the theater a visit! " +
                        "We hope to see you soon! \uF0AB \n");
                break;
            }
        }
    }

    private void listTicketShopActions() {
        System.out.println("\uF0B2 Signed as Mara \uF0B2\n");
        System.out.println("Your actions: ");
        System.out.println("\uF0B2");

        System.out.println("1). Buy a ticket from the theater's ticket shop.");
        System.out.println("2). Cancel a ticket that you bought from the theater's ticket shop.");
        System.out.println("3). List the tickets for an event from the theater's events list.");

        System.out.print("\nWhich action do you choose? Action: ");
    }

    private void ticketShopActions() {
        Scanner in = new Scanner(System.in);

        while (true) {
            this.listTicketShopActions();
            String choiceOfAction = in.nextLine().trim();

            TicketShopService ticketShopService = TicketShopService.getInstance();

            if (choiceOfAction.equals("1")) {
                System.out.println("\uF046 The action you chose: buy a ticket from the theater's ticket shop. \n");
                ticketShopService.buyTicket();
            }
            else if (choiceOfAction.equals("2")) {
                System.out.println("\uF046 The action you chose: cancel a ticket that you bought from the theater's ticket shop. \n");
                ticketShopService.cancelTicket();
            }
            else if (choiceOfAction.equals("3")) {
                System.out.println("\uF046 The action you chose: list the tickets for an event from the theater's events list. \n");
                ticketShopService.listTickets();
            }
            else {
                System.out.println("\n\uF0FB The action you introduced is not valid! Please try again! \uF0FB\n");
                this.ticketShopActions();
                break;
            }

            System.out.println("Do you want to make another action to the theater's ticket shop? " +
                    "yes / no");
            String choiceToContinue = in.nextLine().trim();

            if (!choiceToContinue.equalsIgnoreCase("yes")) {
                System.out.println("\uF0AB Thank you for paying the ticket shop a visit! " +
                        "We hope to see you soon! \uF0AB \n");
                break;
            }
        }
    }
    public void start() {
        this.generalTheaterActions();
    }
}
