package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
//implements ( class -> interface)
//extends ( class -=> class)
//interface -> class (x)
//extends( interface -> interface)
@Component
public class UserDaoService implements IUserService {
    private  static List<User> list = new ArrayList<>();

    private  static  int userCount=3;

    static{
        list.add(new User(1,"kenneth",new Date(), "test1","700101-1111111"));
        list.add(new User(2,"Alice",new Date(), "test2","700101-1112211"));
        list.add(new User(3,"Elena",new Date(), "test3","700101-1113311"));
    }

    @Override
    public List<User> getUserList() {
        return list;
    }

    @Override
    public User getUser(Integer id) {
        for(User user : list){
            if(id.equals(user.getId())){
                return user;
            }
        }
        return null;
    }

    @Override
    public User createUser(User newUser) {
        if(newUser.getId() == null){
            newUser.setId(list.get(list.size()-1).getId()+1);
        }
        list.add(newUser);
        return newUser;
    }

    @Override
    public User modifyUser(User modifyUser) {
        Iterator<User> itUsers = list.iterator();
        while (itUsers.hasNext()){
            User user = itUsers.next();
            if(user.getId() == modifyUser.getId()){
                user.setName(modifyUser.getName());
                user.setJoinDate(modifyUser.getJoinDate());
                user.setPassword(modifyUser.getPassword());
                user.setSsn(modifyUser.getSsn());
                return user;
            }
        }
        return null;
    }

    @Override
    public User removeUser(Integer id) {
        Iterator<User> itUsers = list.iterator();
        //list -> ordering
        //set -> set ordering, not duplicate
        //map(key,value) not ordering, duplicate
        //hashmap hashtable
        while (itUsers.hasNext()){
            User user = itUsers.next();
            if(user.getId() == id){
                itUsers.remove();
                return user;
            }
        }
        return null;
    }


    //    public User postUser(User user){
//        list.add(user);
//        return user;
//    }
//
//    public User deleteUser(Integer id){
//        list.remove(id);
//        return null;
//    }

}
