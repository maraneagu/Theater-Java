package Service;

import Theater.Stage;

import java.util.Map;
import java.util.Scanner;

public class StageService {
    private final TheaterService theaterService;

    public StageService() {
        theaterService = TheaterService.getInstance();
    }

    public void listStages() {
        System.out.println("\uF0B2 The theater's stages \uF0B2");

        Map<Integer, Stage> stages = theaterService.getStages();

        PrintService printService = new PrintService();
        printService.printStages(stages);
    }
    
    public void listStageDetails() {
        System.out.println("\uF0B2 The theater's stages \uF0B2");

        int stageId;
        Map<Integer, Stage> stages = theaterService.getStages();

        for (Map.Entry<Integer, Stage> stage : stages.entrySet())
            System.out.println(stage.getKey() + ". " +
                    stage.getValue().getName());
        System.out.println();

        while (true)
        {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter the number of the stage " +
                    "for which you want to list the information: ");
            stageId = Integer.parseInt(in.nextLine().trim());

            if (stageId >= 1 && stageId <= stages.size())
                break;
            System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
        }

        System.out.println();
        System.out.println(stages.get(stageId));
    }
}
