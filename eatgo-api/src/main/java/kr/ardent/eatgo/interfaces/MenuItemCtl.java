package kr.ardent.eatgo.interfaces;

import kr.ardent.eatgo.application.MenuItemService;
import kr.ardent.eatgo.domain.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MenuItemCtl {

    @Autowired
    private MenuItemService menuItemService;

    @PatchMapping("/restaurants/{restaurantId}/menuItems")
    public String bulkUpdate(@RequestBody List<MenuItem> menuItems, @PathVariable("restaurantId") Long restaurantId){
//        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        menuItemService.bulkUpdate(restaurantId, menuItems);
        return "";
    }
}
