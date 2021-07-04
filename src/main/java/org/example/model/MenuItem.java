package org.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int menuItemId;
    private String itemName;
    private float price;
    private String description;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Menu menu;


//    public int getMenuItemId() {
//        return menuItemId;
//    }
//
//    public void setMenuItemId(int menuItemId) {
//        this.menuItemId = menuItemId;
//    }
//
//    public String getItemName() {
//        return itemName;
//    }
//
//    public void setItemName(String itemName) {
//        this.itemName = itemName;
//    }
//
//    public float getPrice() {
//        return price;
//    }
//
//    public void setPrice(float price) {
//        this.price = price;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

}
