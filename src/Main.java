    import java.util.Scanner;

    public class Main {
        //String ISBN = "0596510047";
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter ISBN-10: ");
            String strISBN = scanner.nextLine();
            strISBN = strISBN.replaceAll("[^0-9]+", ""); //strip all non-digits

            ISBN isbn = new ISBN(strISBN);
            if (isbn.isValid()) {
                System.out.printf("Good ISBN-%d. Querying Google Books API...\n", isbn.getLen());
                GBooksAPIQuery api = new GBooksAPIQuery(isbn);
                api.queryAPI();
            }
        }
    }