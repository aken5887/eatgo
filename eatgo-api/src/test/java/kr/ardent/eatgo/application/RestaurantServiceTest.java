package kr.ardent.eatgo.application;

import kr.ardent.eatgo.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private ResRepository resRepository;

    @Mock
    private MenuItemRepository menuItemRespository;

    // Test가 실행되기 전에 실행
    @Before
    public void setUp(){
       /* resRepository = new ResRepositoryImpl();
        menuItemRespository = new MenuItemRepositoryImpl();*/
        // 초기화
        MockitoAnnotations.initMocks(this);

        mockRestaurantRepository();
        mockMenuItemRepository();
        restaurantService = new RestaurantService(resRepository, menuItemRespository);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants= new ArrayList<>();
        //Restaurant restaurant = new Restaurant(1004L,"Bob zip", "Seoul");
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        restaurants.add(restaurant);

        given(resRepository.findAll()).willReturn(restaurants);
        given(resRepository.findById(1004L)).willReturn(Optional.of(restaurant));

    }

    private void mockMenuItemRepository(){
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(MenuItem.builder().name("KimChi").build());

        given(menuItemRespository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    @Test
    public void getRestaurantWithExisted(){
        Restaurant res = restaurantService.getRestaurant(1004L);
        assertThat(res.getId(), is(1004L));
    }

    @Test
    public void getRestaurantWithNotExisted(){
        restaurantService.getRestaurant(1004L);
    }

    @Test
    public void getRestaurants(){
        Restaurant restaurant = restaurantService.getRestaurant(1004L);
        assertThat(restaurant.getId(), is(1004L));

        MenuItem menuItem = restaurant.getMenuItems().get(0);
        assertThat(menuItem.getName(), is("KimChi"));
    }

    @Test
    public void addRestaurant(){
        /// Restaurant saved = new Restaurant(1234L, "BB","Seoul");
        given(resRepository.save(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });

        Restaurant restaurant = Restaurant.builder()
                .name("BB")
                .address("Seoul")
                .build();


        Restaurant created = restaurantService.addRestaurants(restaurant);
        assertThat(created.getId(), is(1234L));
    }

    @Test
    public void updateRestaurant(){
        //Restaurant restaurant = new Restaurant(1004L, "Jocker", "JongRo");
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Joker")
                .address("JongRo")
                .build();
        given(resRepository.findById(1004L)).willReturn(Optional.of(restaurant));
        restaurantService.updateRestaurant(1004L, "Batman", "Insadong");

        assertThat(restaurant.getName(), is("Batman"));
        assertThat(restaurant.getAddress(), is("Insadong"));
    }

}