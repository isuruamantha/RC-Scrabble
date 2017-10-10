package rapticon.tk.scrabble.model;

/**
 * Created by Isuru Amantha on 10/10/2017.
 */

public class DataModel {

    int id;
    String value;

    public DataModel() {

    }

    public DataModel(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
