package com.example.webpos.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {

    private List<Item> items = new ArrayList<>();

    private void cleanItems() {
        items.removeIf(item -> item.getQuantity() == 0);
    }

    private boolean addItemImpl(Item item) {
        for (int i = 0; i < items.size(); i++) {
            Item itemInList = items.get(i);
            if (itemInList.getProduct().getId().equals(item.getProduct().getId())) {
                int amout = itemInList.getQuantity() + item.getQuantity();
                itemInList.setQuantity(amout);
                return true;
            }
        }
        return items.add(item);
    }

    private boolean modifyItemImpl(Item item) {
        for (int i = 0; i < items.size(); i++) {
            Item itemInList = items.get(i);
            if (itemInList.getProduct().getId().equals(item.getProduct().getId())) {
                itemInList.setQuantity(item.getQuantity());
                return true;
            }
        }
        items.add(item);
        return true;
    }

    public boolean addItem(Item item) {
        boolean ret = addItemImpl(item);
        cleanItems();
        return ret;
    }

    public boolean modifyItem(Item item) {
        boolean ret = modifyItemImpl(item);
        cleanItems();
        return ret;
    }

    public boolean remove(String productId) {
        return items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public boolean reduce(String productId) {
        boolean ret = reduceImpl(productId);
        cleanItems();
        return ret;
    }

    private boolean reduceImpl(String productId) {
        for (Item item : items) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() - 1);
                return true;
            }
        }
        return false;
    }

    public double total() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getQuantity() * items.get(i).getProduct().getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        if (items.size() ==0){
            return "Empty Cart";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cart -----------------\n"  );

        for (int i = 0; i < items.size(); i++) {
            stringBuilder.append(items.get(i).toString()).append("\n");
        }
        stringBuilder.append("----------------------\n"  );

        stringBuilder.append("Total...\t\t\t" + total());

        return stringBuilder.toString();
    }
}
