package com.lucaxiang.controller;

import com.lucaxiang.Utils;
import com.lucaxiang.models.User;

import java.util.ArrayList;
import java.util.List;

public class SelectUser {
    public static User select(String title, List<User> userList,String nullMessage)
    {
        if(userList.size() == 0)
        {
            System.out.println(nullMessage);
            return null;
        }

        String[] header = new String[]{
                "Username", "Name", "Phone"
        };
        List<String> options = new ArrayList<String>();
        for(User user : userList)
        {
            System.out.println(String.format("%s %s %s", user.getUsername(),user.getName(),user.getPhone()));
            options.add(String.format("%s %s %s", user.getUsername(),user.getName(),user.getPhone()));
        }

        Utils.SimpleTable simpleTable =
                new Utils.SimpleTable(header, Utils.SimpleTable.buildOptions(options));
        System.out.println(title);
        simpleTable.display();
        int index = Utils.inputNumber(1,userList.size(),"option not exist");
        return userList.get(index - 1);
    };
}
