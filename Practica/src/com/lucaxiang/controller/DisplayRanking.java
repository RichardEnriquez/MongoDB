package com.lucaxiang.controller;

import com.lucaxiang.Utils;
import com.lucaxiang.models.Incidence;
import com.lucaxiang.models.RankUser;
import com.lucaxiang.models.User;
import com.lucaxiang.services.UserPanelService;

import java.util.ArrayList;
import java.util.List;

public class DisplayRanking {
    public static void display(List<RankUser> rankUserList)
    {
        String[] header = new String[]{
                "username", "count",
        };
        List<String> options = new ArrayList<String>();
        if(rankUserList == null || rankUserList.size() == 0)
        {
            System.out.println("rank is empty");
            Utils.pressKeyToContinue();
            return;
        }
        for(RankUser rankUser : rankUserList)
        {

            options.add(String.format("%s %d",rankUser.getUsername(), rankUser.getCount()));

        }
        Utils.SimpleTable simpleTable =
                new Utils.SimpleTable(header, Utils.SimpleTable.buildOptions(options));
        System.out.println("Incidence List");
        simpleTable.display();
        Utils.pressKeyToContinue();
    }
}
