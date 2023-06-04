package Service;

import Audit.Audit;
import Theater.Artist.Actor;
import Theater.Stage;
import Exception.InvalidNumberException;

import java.util.Map;
import java.util.Scanner;

public class StageService {
    private final TheaterService theaterService;

    public StageService() {
        theaterService = TheaterService.getInstance();
    }

    public void listStages()
    {
        Map<Integer, Stage> stages = theaterService.getStages();

        System.out.println("\uF0B2 The theater's stages \uF0B2");

        PrintService printService = new PrintService();
        printService.printStages(stages);

        Audit audit = Audit.getInstance();
        audit.writeToFile("The theater's stages were listed!", "./theaterStages.cvs");
    }

    public void listStageDetails()
    {
        String stageId;
        Map<Integer, Stage> stages = theaterService.getStages();

        System.out.println("\uF0B2 The theater's stages \uF0B2");

        for (Map.Entry<Integer, Stage> stage : stages.entrySet())
            System.out.println(stage.getKey() + ". " +
                    stage.getValue().getName());
        System.out.println();

        while (true)
        {
            try
            {
                Scanner in = new Scanner(System.in);
                System.out.print("Enter the number of the stage " +
                        "for which you want to list the information: ");
                stageId = in.nextLine().trim();

                if (stageId.compareTo("1") >= 0 && stageId.compareTo(Integer.toString(stages.size())) <= 0)
                    break;
                else throw new InvalidNumberException();
            }
            catch (InvalidNumberException exception)
            {
                System.out.println(exception.getMessage());
            }
        }

        System.out.println("\n" + stages.get(Integer.parseInt(stageId)));

        Audit audit = Audit.getInstance();
        audit.writeToFile("The details about a stage were listed!", "./theaterStages.cvs");
    }
}
