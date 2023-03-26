package Service;

import Theater.Category;

import java.util.List;

public class CategoryService {
    private final TheaterService theaterService;

    public CategoryService() {
        theaterService = TheaterService.getInstance();
    }

    public void listCategories() {
        System.out.println("\uF0B2 The theater's categories \uF0B2");

        List<Category> categories = theaterService.getCategories();

        if (categories.size() == 0)
            System.out.println("There are no categories as of yet." + '\n');
        else
        {
            for (Category category : categories)
                System.out.println(category);
            System.out.println();
        }
    }
}
