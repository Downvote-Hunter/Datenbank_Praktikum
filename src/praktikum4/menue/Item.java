package menue;

public class Item {

    private String message;
    private int id;

    public Item(String message, Item lastItem) {
        this.message = message;
        if (lastItem == null) {
            this.id = 1;
        } else {
            this.id = lastItem.getID() + 1;
        }

    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
