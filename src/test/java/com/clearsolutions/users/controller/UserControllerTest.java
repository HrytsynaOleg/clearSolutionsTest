package com.clearsolutions.users.controller;

import com.clearsolutions.users.errors.UserNotFoundException;
import com.clearsolutions.users.service.IUserService;
import com.clearsolutions.users.model.User;
import com.clearsolutions.users.utils.FileReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IUserService service;

    @Value("classpath:new.json")
    private Resource resource;

    @Value("classpath:partial.json")
    private Resource partialResource;

    @Value("classpath:invalid.json")
    private Resource invalidResource;

    @Value("classpath:users.json")
    private Resource usersResource;

    @Test
    public void shouldCreateUser() throws Exception {
        String json = FileReader.asString(resource);
        given(service.createUser(any())).willReturn("mail@gmail.com");

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/users/mail@gmail.com"));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        String json = FileReader.asString(resource);
        doNothing().when(service).updateUser(any(), anyString());

        mvc.perform(put("/users/{email}", "mail@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateUserIncorrectHandler() throws Exception {
        String json = FileReader.asString(resource);
        doNothing().when(service).updateUser(any(), anyString());

        mvc.perform(put("/users/{email}", "gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateIfNotFoundUser() throws Exception {
        String json = FileReader.asString(resource);
        doThrow(new UserNotFoundException()).when(service).updateUser(any(), anyString());

        mvc.perform(put("/users/{email}", "mail@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateUserWithInvalidEmail() throws Exception {
        String json = FileReader.asString(invalidResource);
        given(service.createUser(any())).willReturn("mail@gmail.com");

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldPartialUpdateUser() throws Exception {
        String json = FileReader.asString(partialResource);
        doNothing().when(service).partialUpdateUser(any(), anyString());

        mvc.perform(patch("/users/{email}", "mail@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        String json = FileReader.asString(partialResource);
        doNothing().when(service).partialUpdateUser(any(), anyString());

        mvc.perform(delete("/users/{email}", "mail@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetUsersByDateBirthRange() throws Exception {
        String json = FileReader.asString(usersResource);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<User> users =  mapper.readValue(json, new TypeReference<>(){});
        given(service.getUsersByDateRange(any(), any())).willReturn(users);

        mvc.perform(get("/users?dateFrom=1972-11-10&dateTo=1972-11-12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(3));
    }

    @Test
    public void shouldGetUsersByDateBirthRangeWithIllegalArgument() throws Exception {
        String json = FileReader.asString(usersResource);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<User> users =  mapper.readValue(json, new TypeReference<>(){});
        given(service.getUsersByDateRange(any(), any())).willReturn(users);

        mvc.perform(get("/users?dateFrom=1972-11-10&dateTo=1972-11-09")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[?(@.detail =='Date to cannot be less than date from')].detail")
                        .value("Date to cannot be less than date from"));
    }
}
