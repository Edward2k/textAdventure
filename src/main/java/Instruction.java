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

    public String[] getStringArrayItems() {
        return items.toArray(new String[0]); //makes string array out of list
    }

    public String getItemByNumber(int i) {return items.get(i);}

    public List<String> getItems() {
        return items;
    }

}
