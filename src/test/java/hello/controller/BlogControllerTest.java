package hello.controller;

import hello.entity.User;
import hello.service.AuthService;
import hello.service.BlogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class BlogControllerTest {
    private MockMvc mvc;
    @Mock
    AuthService authService;
    @Mock
    BlogService blogService;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new BlogController(authService, blogService)).build();
    }

    @Test
    void requireLoginBeforeProceeding() throws Exception {
        mvc.perform(post("/blog").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("登录后才能操作")));
    }

    @Test
    void invalidRequestIfTitleIsEmpty() throws Exception {
        Mockito.when(authService.getCurrentUser()).thenReturn(Optional.of(new User(1, "mockUser", "")));
        mvc.perform(post("/blog").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"content\":\"123\"}"))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("title is invalid")));
    }
}