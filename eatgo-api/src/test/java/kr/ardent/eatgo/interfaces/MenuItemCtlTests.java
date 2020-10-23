package kr.ardent.eatgo.interfaces;

import kr.ardent.eatgo.application.MenuItemService;
import kr.ardent.eatgo.domain.MenuItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MenuItemCtl.class)
public class MenuItemCtlTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MenuItemService menuItemService;

    @Test
    public void bulkUpdate() throws Exception {
        // {"name" : "Jocker" , "address" : "Busan"}
        mvc.perform(patch("/restaurants/1/menuItems")
        .contentType(MediaType.APPLICATION_JSON)
        .content("[]"))
        .andExpect(status().isOk());

        //verify(menuItemService).updateRestaurant(1004L,"Jocker","Busan");
        verify(menuItemService).bulkUpdate(1L, new ArrayList<MenuItem>());
//        verify(menuItemService).bulkUpdate(eq(1L), any());
    }
}