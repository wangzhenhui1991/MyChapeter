package com.wzh.web.controller;

import com.wzh.web.dto.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by wangzhenhui on 2017/5/7.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    //创建一个线程安全的Map集合
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());
    static{
        User user = new User();
        user.setId(1000l);
        user.setName("wzh");
        user.setAge(26);
        users.put(user.getId(),user);
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public List<User> getUsers(){
        //TODO get all user from collections;

        return new ArrayList<>(users.values());
    }
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public String addUser(@RequestBody User user){
        //TODO add user to a list
        users.put(user.getId(),user);
        return "success";
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public User getUser(@PathVariable Long id){
        //TODO find a User by id from collections;
        return users.get(id);
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public User updateUser(@RequestBody User user){
        //TODO find the user by id and update this user
        return users.put(user.getId(),user);
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public boolean deleteUser(@PathVariable Long id){
        //TODO delete a user by id and return the result
        if(users.remove(id)!=null){
            return false;
        }
        return true;
    }

}
