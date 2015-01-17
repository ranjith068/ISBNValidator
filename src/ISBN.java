public class ISBN {
    private static String ISBN;
    private static int len;
    private static boolean valid = false;

    public ISBN(String ISBN, int len) {
        this.ISBN = ISBN;
        this.len = len;
        validate();
    }

    public Boolean isValid() {
        return this.valid;
    }

    public int getLen() {
        return this.len;
    }

    public void setISBN(String ISBN, int len) {
        this.ISBN = ISBN;
        this.len = len;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public static void validate() {
        if (len == 10) {
            validateISBN_10();
        }
        else if (len == 13)
            System.out.println("NYI");
    }

    public static void validateISBN_10() {
        int multiplier = 10;
        int total = 0;
        for (char c : ISBN.toCharArray()) {
            if (Character.isDigit(c)) {
                total += multiplier * Character.getNumericValue(c);
                multiplier--;
                if (multiplier < 0) {
                    valid = false;
                    break;
                }
            }
        }
        if (total % 11 == 0)
            valid = true;
        else
            valid = false;
    }
}
