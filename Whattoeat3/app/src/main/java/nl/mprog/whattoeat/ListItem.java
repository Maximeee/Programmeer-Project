package nl.mprog.whattoeat;

/**
 * Created by maximeweekhout on 16-01-17.
 */

public class ListItem {
    private int id;
    private String value;

    ListItem(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int GetId() {
        return id;
    }

    public String GetValue() {
        return value;
    }
}
