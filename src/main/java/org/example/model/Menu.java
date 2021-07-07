package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Menu {

    public Menu(){

    }
    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String menuId;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Set<MenuItem> menuItems;

//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
//    @JoinColumn(name = "restaurantId")
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
