    import java.util.Scanner;

    public class Main {
        //String ISBN = "0596510047";
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter ISBN-10: ");
            String strISBN = scanner.nextLine();
            ISBN isbn = new ISBN(strISBN, 10);
            if (isbn.isValid()) {
                System.out.printf("Good ISBN-%d. Querying Google Books API...\n", isbn.getLen());
                GBooksAPIQuery api = new GBooksAPIQuery();
                api.queryAPI(strISBN);
            }
        }
    }