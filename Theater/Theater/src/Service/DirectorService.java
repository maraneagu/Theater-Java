package Service;

import Audit.Audit;
import Theater.Artist.Actor;
import Theater.Director;
import java.util.Map;

public class DirectorService {
    private final TheaterService theaterService;

    public DirectorService() {
        theaterService = TheaterService.getInstance();
    }

    public void listDirectors()
    {
        System.out.println("\uF0B2 The theater's directors \uF0B2");

        Map<Integer, Director> directors = theaterService.getDirectors();

        if (directors.isEmpty())
            System.out.println("There are no directors as of yet." + '\n');
        else
        {
            for (Map.Entry<Integer, Director> director : directors.entrySet())
                System.out.println(director.getValue());
            System.out.println();
        }

        Audit audit = Audit.getInstance();
        audit.writeToFile("The theater's directors list was listed!", "./theaterDirectors.cvs");
    }
}
