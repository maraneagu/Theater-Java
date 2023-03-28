package Service;

import Theater.Stage;

import java.util.Scanner;
import java.util.List;

public class StageService {
    private final TheaterService theaterService;

    public StageService() {
        theaterService = TheaterService.getInstance();
    }

    public void listStages() {
        System.out.println("\uF0B2 The theater's stages \uF0B2");

        List<Stage> stages = theaterService.getStages();

        PrintService printService = new PrintService();
        printService.printStages(stages);
    }
    public void listStageDetails() {
        System.out.println("\uF0B2 The theater's stages \uF0B2");

        int i, stageId;
        List<Stage> stages = theaterService.getStages();

        for (i = 0; i < stages.size(); i++)
            System.out.println(i + 1 + ". " +
                    stages.get(i).getName());
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
        System.out.println(stages.get(stageId - 1));
    }
}
