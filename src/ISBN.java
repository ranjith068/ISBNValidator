public class ISBN {
    private static String ISBN;
    private static String formattedISBN;
    private static int len;
    private static boolean valid = false;

    public ISBN(String ISBN) {
        this.ISBN = ISBN;
        this.len = ISBN.length();
        validate();
        this.formattedISBN = format(this.ISBN);
    }

    public Boolean isValid() {
        return this.valid;
    }

    public int getLen() {
        return this.len;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
        this.len = ISBN.length();
    }

    public String getISBN() {
        return this.ISBN;
    }

    public static void validate() {
        if (len == 10) {
            validateISBN_10();
        }
        else if (len == 13)
            validateISBN_13();
        else
            System.out.println("Not 10 or 13 digits long.");
    }

    public static String format(String isbn) {
         if (isbn.length() == 10) {
             isbn = String.format("%s-%s-%s-%s", isbn.substring(0, 1), isbn.substring(1,3), isbn.substring(3,9), isbn.substring(9,10));
         }
        else if (isbn.length() == 13) {
             isbn = String.format("%s-%s-%s-%s-%s", isbn.substring(0,3), isbn.substring(3,4), isbn.substring(4,6), isbn.substring(6,12), isbn.substring(12, 13));
        }
        return isbn;
    }

    public static void validateISBN_10() {
        int multiplier = 10;
        int total = 0;

        for (char c : ISBN.toCharArray()) {
            total += multiplier * Character.getNumericValue(c);
            multiplier--;
            if (multiplier < 0) {
                valid = false;
                break;
            }
        }

        if (total % 11 == 0)
            valid = true;
        else
            valid = false;
    }

    public static void validateISBN_13() {
        int multiplier = 1;
        int total = 0;
        for (int i=0; i<ISBN.length() - 1; i++)  {
            char c = ISBN.charAt(i);
            total += multiplier * Character.getNumericValue(c);
            multiplier = multiplier == 3 ? 1 : 3;
        }
        int checkDigit = 10-(total%10);
        int finalDigit = Character.getNumericValue(ISBN.charAt(ISBN.length()-1));
        if (checkDigit == finalDigit)
            valid = true;
        else
            valid = false;
    }
}
