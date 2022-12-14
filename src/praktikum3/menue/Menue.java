package praktikum3.menue;

import praktikum3.exception.InputNotInRange;
import praktikum3.util.ReadUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Menue {


    private final HashMap<Item, Menue> linkedItemMenues = new HashMap<>();
    private final HashMap<Item, Action> linkedItemActions = new HashMap<>();
    private Menue previousMenue;
    private int lastSelectedID;
    private final List<Item> menueItems = new ArrayList<>();

    public Menue getPreviousMenue() {
        return previousMenue;
    }

    public void setPreviousMenue(Menue previousMenue) {
        this.previousMenue = previousMenue;
    }

    public int getLastSelectedID() {
        return lastSelectedID;
    }

    public void setLastSelectedID(int lastSelectedID) {
        this.lastSelectedID = lastSelectedID;
    }

    public List<Item> getMenueItems() {
        return menueItems;
    }

    public void addMenueItem(Item item) {

        menueItems.add(item);
    }

    public void addMenueItem(String message) {
        Item newItem = new Item(message, getLastMenueItem());
        addMenueItem(newItem);
    }

    private Item getLastMenueItem() {
        if (menueItems.isEmpty()) {
            return null;
        }
        return menueItems.get(menueItems.size() - 1);
    }

    public void linkMenueItem(Menue menue, Item item) {
        linkedItemMenues.put(item, menue);
        menue.setPreviousMenue(this);
    }

    public void showMenue() throws InputNotInRange {
        for (Item item : menueItems) {
            System.out.println(item.getMessage());
        }

        int firstID = menueItems.get(0).getID();
        int lastID = menueItems.get(menueItems.size() - 1).getID();

        int input = ReadUtil.readInt(firstID, lastID);
        selectMenueItem(input);
    }

    public void selectMenueItem(int input) throws InputNotInRange {
        for (Item item : menueItems) {
            if (item.getID() == input) {
                setLastSelectedID(input);

                if (linkedItemMenues.containsKey(item)) {
                    linkedItemMenues.get(item).showMenue();
                } else if (linkedItemActions.containsKey(item)) {
                    linkedItemActions.get(item).execute();
                    this.showMenue();
                }
            }
        }

    }

    public void linkActionItem(Item item, Action action) {
        linkedItemActions.put(item, action);
    }
}
