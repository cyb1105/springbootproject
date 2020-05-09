package com.example.demo.dao;

import com.example.demo.entity.User;

import java.util.List;

public interface IUserService  {

    List<User> getUserList();

    User getUser(Integer id);

    User createUser(User newUser);

    User modifyUser(User modifyUser);

    User removeUser(Integer id);


}
