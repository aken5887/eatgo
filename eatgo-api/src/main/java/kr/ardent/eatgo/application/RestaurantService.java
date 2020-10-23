package kr.ardent.eatgo.application;

import kr.ardent.eatgo.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

//@Component
@Service
public class RestaurantService {

    private ResRepository resRepository;

    private MenuItemRepository menuItemRepository;

    public RestaurantService(ResRepository resRepository, MenuItemRepository menuItemRespository) {
        this.resRepository = resRepository;
        this.menuItemRepository = menuItemRespository;
    }

    public List<Restaurant> getRestaurants() {
        return resRepository.findAll();
    }

    public Restaurant getRestaurant(Long id){
        Restaurant restaurant = resRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        restaurant.setMenuItems(menuItems);

        return restaurant;
    }


    public Restaurant addRestaurants(Restaurant restaurant) {
        return resRepository.save(restaurant);
    }

    @Transactional
    public Restaurant updateRestaurant(Long id, String name, String address) {
        // TODO : update Restaurants
        Restaurant restaurant = resRepository.findById(id).orElse(null);
       // Restaurant restaurant = new Restaurant(id, name, address);
        restaurant.updateInformation(name, address);
        return restaurant;
    }
}
