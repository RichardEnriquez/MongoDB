package com.lucaxiang.controller;

import com.lucaxiang.Utils;
import com.lucaxiang.models.User;

import java.util.ArrayList;
import java.util.List;

public class DisplayUser {

    public static void display(List<User> userList)
    {
        String[] header = new String[]{
                "Username", "Name", "Phone"
        };
        List<String> options = new ArrayList<String>();
        if(userList == null || userList.size() == 0)
        {
            System.out.println("users is empty");
            Utils.pressKeyToContinue();
            return;
        }
        for(User user : userList)
        {
            options.add(String.format("%s %s %s", user.getUsername(),user.getName(),user.getPhone()));
        }
        Utils.SimpleTable simpleTable =
                new Utils.SimpleTable(header, Utils.SimpleTable.buildOptions(options));
        System.out.println("Users List");
        simpleTable.display();
        Utils.pressKeyToContinue();
    }
}
