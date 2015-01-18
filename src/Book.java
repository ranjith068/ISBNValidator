/**
 * Created by Sean on 1/17/2015.
 */
public class Book {
    String ISBN10, ISBN13, title, subtitle, description;
    String[] authors;
    int pageCount;
    double rating;

    public Book(String[] authors, String title, String subtitle, String description, String ISBN10, String ISBN13, int pageCount, double rating) {
        this.authors = authors;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.ISBN10 = ISBN10;
        this.ISBN13 = ISBN13;
        this.pageCount = pageCount;
        this.rating = rating;
    }

    public void print() {
        System.out.printf("%s - %s by ", title, subtitle);
        for (int i = 0; i < authors.length; i++) {
            System.out.printf("%s", authors[i]);
            if (i < authors.length - 1)
                System.out.print(", ");
            else
                System.out.print(" ");
        }
        System.out.println();
        System.out.println(description);
        System.out.printf("ISBN-10: %s\n", ISBN10);
        System.out.printf("ISBN-13: %s\n", ISBN13);
        System.out.printf("Rating: %f\n", rating);
        System.out.printf("Page count: %d\n", pageCount);
    }
}
