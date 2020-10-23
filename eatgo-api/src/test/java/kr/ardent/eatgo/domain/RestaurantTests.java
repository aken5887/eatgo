package kr.ardent.eatgo.domain;


import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RestaurantTests {

    @Test
    public void creation() {
        //Restaurant res = new Restaurant(1004L, "Bob zip","Seoul");
        Restaurant res = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        assertThat(res.getId(), is(1004L));
        assertThat(res.getName(), is("Bob zip"));
        assertThat(res.getAddress(), is("Seoul"));
    }

    @Test
    public void infomation(){
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bobzip")
                .address("Seoul")
                .build();
        assertThat(restaurant.getInformation(), is("Bobzip in Seoul"));
    }
}