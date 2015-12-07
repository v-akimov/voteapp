package ru.akimov.voteapp.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.akimov.voteapp.util.DateTimeUtils;

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
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PrepareForTest(DateTimeUtils.class)
public class VoteControllerTest extends AbstractControllerTest {

    @Test
    public void testGetVoteResult() throws Exception {
        PowerMockito.mockStatic(DateTimeUtils.class);
        PowerMockito.when(DateTimeUtils.getCurrentHours()).thenReturn(10);

        createVote(1L, "admin", "password");
        createVote(2L, "user1", "password");
        createVote(2L, "user2", "password");

        mockMvc.perform(get("/votes/restorants")
                .headers(RequestUtils.getHeaders("user1", "password"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].score", is(2)))
                .andExpect(jsonPath("$[0].restorantName", is("Ugly Coyote")))
                .andExpect(jsonPath("$[1].score", is(1)))
                .andExpect(jsonPath("$[1].restorantName", is("Metropol")));
    }

    private void createVote(Long restorantId, String username, String password) throws Exception {
        mockMvc.perform(put("/votes/restorants")
                .headers(RequestUtils.getHeaders(username, password))
                .contentType(MediaType.APPLICATION_JSON)
                .content(Long.toString(restorantId)))
                .andExpect(status().isOk());
    }
}
