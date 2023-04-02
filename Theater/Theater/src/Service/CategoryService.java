package Service;

import Theater.Category;

import java.util.List;
import java.util.Map;

public class CategoryService {
    private final TheaterService theaterService;

    public CategoryService() {
        theaterService = TheaterService.getInstance();
    }

    public void listCategories() {
        System.out.println("\uF0B2 The theater's categories \uF0B2");

        Map<Integer, Category> categories = theaterService.getCategories();

        for (Map.Entry<Integer, Category> category : categories.entrySet())
            System.out.println(category.getValue());
        System.out.println();
    }
}
