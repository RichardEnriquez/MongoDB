package com.lucaxiang.controller;

import com.lucaxiang.Utils;
import com.lucaxiang.exceptions.ManagerException;
import com.lucaxiang.models.User;
import com.lucaxiang.services.UserService;

public class Login {

    public static void start()
    {
        do {
            String username;
            String password;
            System.out.println("Please Input your username");
            username = Utils.input();
            System.out.println("Please Input your password");
            password = Utils.input();
            try
            {
                User user = UserService.login(username,password);
                UserPanel.start(user);
            }
            catch (ManagerException e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }while (true);

    }
}
