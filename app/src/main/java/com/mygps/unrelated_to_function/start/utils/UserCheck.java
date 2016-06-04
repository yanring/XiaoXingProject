package com.mygps.unrelated_to_function.start.utils;

import com.mygps.unrelated_to_function.start.model.User;

/**
 * Created by 10397 on 2016/6/1.
 */
public class UserCheck {
    public static final int OK = 0;
    public static final int USERNAMEERROR = 1;
    public static final int PASSWORDERROR = 2;
    public static int check(String username,String password){
//用户密码检查
        return OK;
    }

    public static int check(User user){
        return check(user.getUsername(),user.getPassword());
    }
}
