package Service;

import Theater.Director;
import java.util.List;

public class DirectorService {
    private final TheaterService theaterService;

    public DirectorService() {
        theaterService = TheaterService.getInstance();
    }

    public void listDirectors() {
        System.out.println("\uF0B2 The theater's directors \uF0B2");

        List<Director> directors = theaterService.getDirectors();

        if (directors.size() == 0)
            System.out.println("There are no directors as of yet." + '\n');
        else
        {
            for (Director director : directors)
                System.out.println(director);
            System.out.println();
        }
    }
}
