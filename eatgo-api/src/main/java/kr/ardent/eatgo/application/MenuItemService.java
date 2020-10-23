package kr.ardent.eatgo.application;

import kr.ardent.eatgo.domain.MenuItem;
import kr.ardent.eatgo.domain.MenuItemRepository;
import kr.ardent.eatgo.domain.ResRepository;
import kr.ardent.eatgo.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRespository;

    public MenuItemService(MenuItemRepository menuItemRespository) {
        this.menuItemRespository = menuItemRespository;
    }

    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems){
        for(MenuItem menuItem:menuItems){
            update(restaurantId, menuItem);
        }
    }

    private void update(Long restaurantId, MenuItem menuItem) {
        if(menuItem.isDestroy()){
            menuItemRespository.deleteById(menuItem.getId());
            return;
        }
        menuItem.setRestaurantId(restaurantId);
        menuItemRespository.save(menuItem);
    }
}
