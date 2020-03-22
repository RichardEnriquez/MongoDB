package com.lucaxiang;

import com.lucaxiang.controller.SelectUser;
import com.lucaxiang.services.UserPanelService;

public class Main {

    public static void main(String[] args) {

        // write your code here
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        incidenceManager.start();
    }
}
