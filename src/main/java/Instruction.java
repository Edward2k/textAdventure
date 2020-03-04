import java.util.Arrays;
import java.util.List;

public class Instruction {
    private String action;
    private List<String> items;

    Instruction(String act, String[] itemList) {
        this.action = act;
        this.items = Arrays.asList(itemList);
    }

    public String getAction() {
        return this.action;
    }

    public String getItemNumber(int n) {
        if (n > items.size() || n < 0) {return "NaI";}
        return items.get(n);
    }

}
