package com.lucaxiang.controller;

import com.lucaxiang.Utils;

public class SelectType {

    public static String select()
    {
        String[] options = new String[]{"NORMAL","URGENT"};
        Utils.SimpleSelect simpleSelect = new Utils.SimpleSelect("select type",options);
        return options[simpleSelect.indexSelect()];
    }
}

