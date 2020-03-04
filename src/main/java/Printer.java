public class Printer {

    private static final int CHARS_PER_LINE = 100;
    void output(String output) {
        boolean newLine = false;
        for (int i = 0; i < output.length(); i++) {
            if (i % CHARS_PER_LINE == 0 && i != 0) {newLine = true;}
            if (newLine && output.charAt(i) == ' ') {
                System.out.println();
                i++;
                newLine = false;
            }
            System.out.print(output.charAt(i));
        }
        System.out.println();
    }

}
