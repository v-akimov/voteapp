package ru.akimov.voteapp.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import ru.akimov.voteapp.domain.Dish;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by z003cptz on 06.12.2015.
 */
public class RestorantControllerTest extends AbstractControllerTest {

    @Test
    public void testGetRestorants() throws Exception {
        mockMvc.perform(get("/restorants").headers(RequestUtils.getHeaders("user1", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Metropol")))
                .andExpect(jsonPath("$[1].name", is("Ugly Coyote")))
                .andDo(print());
    }

    @Test
    public void testCreateAndGetMenu() throws Exception {
        String url = String.format("/restorants/%d/menu", 1);
        Collection<Dish> menu = Lists.newArrayList(new Dish("chips", 1.12d), new Dish("beer", 3d));

        MvcResult result = mockMvc.perform(put(url)
                .headers(RequestUtils.getHeaders("admin", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menu)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Collection<Dish> savedMenu = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<Collection<Dish>>() {
                });
        assertThat(savedMenu, is(menu));

        //test that get will return the same value
        result = mockMvc.perform(get(url).headers(RequestUtils.getHeaders("admin", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Collection<Dish> loadedMenu = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<Collection<Dish>>() {
                });
        assertThat(loadedMenu, is(menu));
    }

    @Test
    public void testForbidUserMenuUpdate() throws Exception {
        String url = String.format("/restorants/%d/menu", 1);
        mockMvc.perform(put(url)
                .headers(RequestUtils.getHeaders("user1", "password"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
