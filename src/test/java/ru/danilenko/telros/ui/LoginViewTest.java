package ru.danilenko.telros.ui;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
public class LoginViewTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void logInTestSucceeded1() throws Exception {
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().user("admin").password("admin"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin"));
    }
    @Test
    public void logInTestSucceeded2() throws Exception {
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().user("user1@mail.ru").password("user1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user"));
    }


    @Test
    public void logInTestFailed() throws Exception {
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().user("admin").password("a"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error"));
    }
}
