package kr.ardent.eatgo.interfaces;

import kr.ardent.eatgo.application.RestaurantService;
import kr.ardent.eatgo.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantCtl.class)
public class RestaurantCtlTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;
/*
    @SpyBean(RestaurantService.class)
    private RestaurantService restaurantService;*/

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
                .id(1004L)
                .name("ABC House")
                .address("Seoul")
                .build());

        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"ABC House\"")))
                .andExpect(content().string(containsString("\"id\":1004")));
    }

    @Test
    public void detailWithExisted() throws Exception{
        Restaurant restaurant =Restaurant.builder()
                .id(1004L)
                .name("ABC House")
                .address("Seoul")
                .build();
        //restaurant.addMenuItem(new MenuItem("Kimchi"));
        restaurant.setMenuItems(Arrays.asList(MenuItem.builder().name("Kimchi").build()));
        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);
        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"name\":\"ABC House\""))
                )
                .andExpect(content().string(
                        containsString("\"id\":1004"))
                )
                .andExpect(content().string(
                        containsString("Kimchi")
                ));
    }

    @Test
    public void detailWithNotExisted() throws Exception{
        given(restaurantService.getRestaurant(404L))
                .willThrow(new RestaurantNotFoundException(404L));
        
        mvc.perform(get("/restaurants/404"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("{}"));
    }

    @Test
    public void createWithValidData() throws Exception {
        //Restaurant restraunt = new Restaurant(1234L, "BB", "Seoul");
        //restaurantService.addRestaurants

        given(restaurantService.addRestaurants(any())).will(invocation -> {
           Restaurant restaurant = invocation.getArgument(0);
           return Restaurant.builder()
                   .id(1234L)
                   .name(restaurant.getName())
                   .address(restaurant.getAddress())
                   .build();
        });

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"BB\", \"address\" : \"Busan\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/restaurants/1234"))
                .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurants(any());
    }

    @Test
    public void createWithInvalidData() throws Exception {
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"\", \"address\" : \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithValidData() throws Exception{
        // {"name" : "Jocker", "address" : "JongRo"}
        mvc.perform(patch("/restaurants/1004")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\" : \"Jocker\", \"address\" : \"JongRo\"}"))
                    .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1004L, "Jocker", "JongRo");
    }

    @Test
    public void updateWithInvalidData() throws Exception{
        // {"name" : "Jocker", "address" : "JongRo"}
        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"\", \"address\" : \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithoutName() throws Exception{
        // {"name" : "Jocker", "address" : "JongRo"}
        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"\", \"address\" : \"Seoul\"}"))
                .andExpect(status().isBadRequest());
    }
}
