package kr.ardent.eatgo.interfaces;

import kr.ardent.eatgo.application.RestaurantService;
import kr.ardent.eatgo.domain.MenuItemRepository;
import kr.ardent.eatgo.domain.ResRepository;
import kr.ardent.eatgo.domain.Restaurant;
import kr.ardent.eatgo.domain.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * UI Layer
 */
@CrossOrigin
@RestController
public class RestaurantCtl {

//    private ResRepository repository = new ResRepository();

    private ResRepository resRepository;

    private MenuItemRepository menuItemRepository;
    @Autowired
    private RestaurantService resService;

    @GetMapping("/restaurants")
    public List<Restaurant> list(){
       /* List<Restaurant> resList = new ArrayList<>();
        resList.add(new Restaurant(1004L,"Bob zip", "Seoul"));
        resList.add(new Restaurant(200L, "Cyber food", "Seoul"));*/

        //List<Restaurant> restaurants = resRepository.findAll();
        List<Restaurant> restaurants = resService.getRestaurants();
        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id){

       /* List<Restaurant> resList = new ArrayList<>();

        resList.add(new Restaurant(100L,"Bob zip","Seoul"));
        resList.add(new Restaurant(200L, "Cyber food", "Seoul"));
        */

       /* List<Restaurant> restaurants = repository.findAll();
        Restaurant res = restaurants.stream().filter(r->r.getId() == id)
                .findFirst()
                .orElse(null);*/
        /*Restaurant restaurant = resRepository.findById(id);
        List<MenuItem> menuItems = menuItemRepository.findByAllByRestaurantId(id);
        restaurant.setMenuItems(menuItems);*/
/*
        try{

        }catch(RestaurantNotFoundException exception){

        }*/
        // 기본정보 + 메뉴정보
        Restaurant newRestaurant = resService.getRestaurant(id);

        return newRestaurant;
    }

    @PostMapping("/restaurants")
    public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource)
            throws URISyntaxException {
        //Restaurant restaurant = new Restaurant(name, address);
        Restaurant restaurant = Restaurant.builder()
                .name(resource.getName())
                .address(resource.getAddress())
                .build();
        resService.addRestaurants(restaurant);

        URI location = new URI("/restaurants/"+restaurant.getId());
        return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("/restaurants/{id}")
    public String update(@PathVariable("id") Long id, @Valid @RequestBody Restaurant resource){
        String name = resource.getName();
        String address = resource.getAddress();
        resService.updateRestaurant(id, name, address);
        return "{}";
    }
}
