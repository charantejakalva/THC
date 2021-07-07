package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.MenuItem;
import org.example.model.Restaurant;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
public class MenuDTO {
    private String menuId;

    private Set<MenuItem> menuItems;
//    private Restaurant restaurant;

//    public int getMenuId() {
//        return menuId;
//    }
//
//    public void setMenuId(int menuId) {
//        this.menuId = menuId;
//    }
//
//    public Set<MenuItem> getMenuItems() {
//        return menuItems;
//    }
//
//    public void setMenuItems(Set<MenuItem> menuItems) {
//        this.menuItems = menuItems;
//    }
}
