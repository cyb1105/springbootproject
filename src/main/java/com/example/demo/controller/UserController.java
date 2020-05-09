package com.example.demo.controller;

import com.example.demo.dao.UserDaoService;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@Slf4j
//@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDaoService service;


    @GetMapping("/users")
    public MappingJacksonValue getUserList() {
        List<User> list = service.getUserList();
        List<EntityModel<User>> result = new ArrayList<>();
        list.forEach(user -> {
            EntityModel<User> model = new EntityModel<>(user);
            WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUser(user.getId()));
            model.add(linkTo.withRel("user-detail"));

            result.add(model);

        });
//        for(User user : list) {
//            EntityModel<User> model = new EntityModel<>(user);
//            WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUser(user.getId()));
//            model.add(linkTo.withRel("user-detail"));
//            result.add(model)
//        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider provider = new SimpleFilterProvider().addFilter("Userinfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(result);
        mapping.setFilters(provider);
        return mapping;
    }

    //users/1
    //users/2
    @GetMapping("/users/{id}")
    public MappingJacksonValue getUser(@PathVariable Integer id) {
        User user = service.getUser(id);
        if (user == null) {
            throw new UserNotFoundException("id" + id);
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate");
        FilterProvider provider = new SimpleFilterProvider().addFilter("Userinfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(provider);
        return mapping;

    }

    @GetMapping("/admin/users/{id}")
    public MappingJacksonValue getUserByAdmin(@PathVariable Integer id) {
        User user = service.getUser(id);
        if (user == null) {
            throw new UserNotFoundException("id" + id);
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider provider = new SimpleFilterProvider().addFilter("Userinfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(provider);
        return mapping;
    }

    @GetMapping("/hateoas/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable int id) {
        User user = service.getUser(id);
        if (user == null) {
            throw new UserNotFoundException("id" + id);
        }
        EntityModel<User> model = new EntityModel<>(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUserList());
        model.add(linkTo.withRel("all-users"));

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider provider = new SimpleFilterProvider().addFilter("Userinfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(model);
        mapping.setFilters(provider);

        return mapping;
    }

//    @PostMapping("/users")
//    public List<User> postUser(@RequestBody User user){
//        service.postUser(user);
//        return service.getUserList();
//    }

    //    @DeleteMapping("/users/{id}")
//    public List<User> delete(@PathVariable Integer id){
//        service.deleteUser(id);
//        return service.getUserList();
//    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Integer id) {
        User user = service.removeUser(id);

        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createUser = service.createUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createUser.getId())
                .toUri();

        // MappingJacksonValue
        return ResponseEntity.created(location).build();

    }

}
