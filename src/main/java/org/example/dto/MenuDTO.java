package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.MenuItem;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
public class MenuDTO {
    private int menuId;

    private Set<MenuItem> menuItems;

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
