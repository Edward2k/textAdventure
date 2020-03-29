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

    public List<String> getItems() {
        return items;
    }

    public String toString() {
        StringBuilder result = new StringBuilder(action);
        for (String item: items) {
            if (item == null) {break;}
            result.append(" ").append(item);
        }
        return result.toString();
    }

}
