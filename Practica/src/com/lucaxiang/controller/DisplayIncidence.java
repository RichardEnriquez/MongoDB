package com.lucaxiang.controller;

import com.lucaxiang.Utils;
import com.lucaxiang.models.Incidence;
import com.lucaxiang.models.User;
import com.lucaxiang.services.UserPanelService;

import java.util.ArrayList;
import java.util.List;

public class DisplayIncidence {

    public static void display(List<Incidence> incidenceList)
    {
        String[] header = new String[]{
                "origin", "destin", "description","date","createUser"
        };
        List<String> options = new ArrayList<String>();
        if(incidenceList == null || incidenceList.size() == 0)
        {
            System.out.println("incidence is empty");
            Utils.pressKeyToContinue();
            return;
        }
        for(Incidence incidence : incidenceList)
        {
            String originUsername = incidence.getOrigin();
            String destinUsername = incidence.getDestin();
            User originUser = UserPanelService.getUserByUsername(originUsername);
            User destinUser = UserPanelService.getUserByUsername(destinUsername);
            String originName = originUser != null ? originUser.getName() : "removed";
            String destinName = destinUser != null ? destinUser.getName() : "removed";

            options.add(String.format("%s(%s) %s(%s) %s %s %s",
                    originUsername,originName,
                    destinUsername,destinName,
                    incidence.getDescription(),
                    incidence.getCreateDate(),
                    incidence.getCreateUser()));
        }
        Utils.SimpleTable simpleTable =
                new Utils.SimpleTable(header, Utils.SimpleTable.buildOptions(options));
        System.out.println("Incidence List");
        simpleTable.display();
        Utils.pressKeyToContinue();
    }
}
