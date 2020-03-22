package com.lucaxiang.controller;

import com.lucaxiang.Utils;
import com.lucaxiang.models.History;
import com.lucaxiang.models.Incidence;
import com.lucaxiang.models.RankUser;
import com.lucaxiang.models.User;
import com.lucaxiang.services.UserPanelService;
import com.lucaxiang.services.UserService;

import java.util.Arrays;
import java.util.List;

public class UserPanel {

    static User currentUser = null;

    final static String[] menuOptions = new String[] {
            "Create Incidence",
            "Display All Incidence",
            "My Incidence",
            "Display Incidence with Origin",
            "Display Incidence with Destin",
            "Display Last login DateTime",
            "Update Profile",
            "Ranking",
            "Create  Employ",
            "Display Employ",
            "Remove  Employ",

    };

    public static void start(User user)
    {
        currentUser = user;
        //guardar evento de login
        History history = new History();
        history.setUsername(currentUser.getUsername());
        history.setType("L");
        history.setDateTime(Utils.nowDateTimeString());
        UserPanelService.newHistory(history);
        System.out.println("Hello " + user.getName());
        boolean isAdmin = user.isAdmin();
        int level = isAdmin ? menuOptions.length : 7;
        String[] newOptions = Arrays.copyOf(menuOptions, level);
        display(newOptions);
    }

    private static void display(String[] options)
    {
        Utils.SimpleMenu simpleMenu = new Utils.SimpleMenu("User Panel",options,"Logout");
        boolean shouldExit = false;
        do {
            switch (simpleMenu.select())
            {
                case 1:
                    createIncidence();
                    break;
                case 2:
                    displayAllIncidence();
                    break;
                case 3:
                    myIncidence();
                    break;
                case  4:
                    displayIncidenceWithOrigin();
                    break;
                case  5:
                    displayIncidenceWithDestin();
                    break;
                case 6:
                    displayLastLogin();
                    break;
                case 7:
                    updateProfile();
                    break;
                case 8:
                    ranking();
                    break;
                case 9:
                    createEmploy();
                    break;
                case 10:
                    displayEmploy();
                    break;
                case 11:
                    removeEmploy();
                    break;

                case 0:
                    shouldExit = true;
                    break;
            }
        }while (!shouldExit);
    }



    private static void createIncidence() {
        Incidence incidence = new Incidence();
        incidence.setCreateUser(currentUser.get_id());
        User originUser;
        User destinUser;
        String description;
        String type;
        //si usuario actual es administrador el puede seleccionar un origen
        if(currentUser.isAdmin())
        {
            List<User> originUserList = UserPanelService.getUserList();
            originUser = SelectUser.select("Please Select Origin",originUserList,"No exist option for origin");
            if(originUser == null)
            {
                return;
            }
        }
        // si no origen solo puede ser usuario actual
        else
        {
            originUser = currentUser;
        }
        //obtener una lista sin usuario origen
        List<User> destinUserList = UserPanelService.getUserListWithout(originUser);
        destinUser = SelectUser.select("Please Select Destin",destinUserList,"No exist option for origin");
        if(destinUser == null)
        {
            return;
        }
        System.out.println("Please Input description");
        description = Utils.input();
        type = SelectType.select();
        incidence.setOrigin(originUser.getUsername());
        incidence.setDestin(destinUser.getUsername());
        incidence.setCreateDate(Utils.nowDateString());
        incidence.setCreateUser(currentUser.getUsername());
        incidence.setDescription(description);
        incidence.setType(type);
        //anadir a bases de datos
        UserPanelService.newIncidence(incidence);
        System.out.println("create incidence successful!!");

        // si tipo es urgente grabar en historia
        if(type.equals("URGENT"))
        {
            String dateTime = Utils.nowDateTimeString();
            History history = new History();
            history.setUsername(currentUser.getUsername());
            history.setDateTime(dateTime);
            history.setType("U");
            UserPanelService.newHistory(history);
        }
        Utils.pressKeyToContinue();
    }

    private static void displayAllIncidence() {
        List<Incidence> incidenceList = UserPanelService.getIncidenceList();
        DisplayIncidence.display(incidenceList);
    }
    private static void myIncidence()
    {
        List<Incidence> incidenceList = UserPanelService.getUserIncidence(currentUser);
        DisplayIncidence.display(incidenceList);
    }
    private static void displayIncidenceWithOrigin()
    {
        String username;
        User user;
        do {
            System.out.println("Please Input Origin Username");
            username = Utils.input();
            user = UserPanelService.getUserByUsername(username);
            if(user != null)
            {
                break;
            }
            System.out.println("user no exist!");
        }while (true);
        List<Incidence> incidenceList = UserPanelService.getUserIncidenceFilterWithField("origin",username);
        DisplayIncidence.display(incidenceList);
    }
    private static void displayIncidenceWithDestin()
    {
        String username;
        do {
            System.out.println("Please Input Origin Username");
            username = Utils.input();

            if(UserService.existUsername(username))
            {
                break;
            }
            System.out.println("user no exist!");
        }while (true);
        List<Incidence> incidenceList = UserPanelService.getUserIncidenceFilterWithField("destin",username);
        History history = new History();
        history.setUsername(currentUser.getUsername());
        history.setType("C");
        history.setDateTime(Utils.nowDateTimeString());
        UserPanelService.newHistory(history);
        DisplayIncidence.display(incidenceList);

    }
    private static void displayLastLogin() {
        History history = UserPanelService.getUserLastLogin(currentUser);
        System.out.println(history.getDateTime());
        Utils.pressKeyToContinue();
    }
    private static void updateProfile() {
       String newName;
       String newPhone;
       String newPassword;
       System.out.println("update profile empty to skip");
       System.out.println("Please input new password");
       newPassword = Utils.inputAllowEmpty();
       System.out.printf("Please input new name (%s)\n",currentUser.getName());
       newName = Utils.inputAllowEmpty();
       System.out.printf("Please input new phone (%s)\n",currentUser.getPhone());
       newPhone = Utils.inputAllowEmpty();
       boolean change = false;
       if(!newName.isEmpty() || !newPassword.isEmpty() || !newPhone.isEmpty())
       {
           change =true;
       }

       if(change)
       {
           UserPanelService.updateUser(currentUser, newName,newPassword,newPhone);

           System.out.println("update user profile successful!!");
           Utils.pressKeyToContinue();
       }

    }

    private static void createEmploy() {
        String username;
        String password;
        String name;
        String phone;
        boolean admin;
        do {
            System.out.println("Please Input Username");
            username = Utils.input();
            if(UserService.existUsername(username))
            {
                System.out.println("user no exist!");
                continue;
            }
            break;
        }while (true);
        System.out.println("Please Input password");
        password = Utils.input();
        System.out.println("Please Input name");
        name = Utils.input();
        System.out.println("Please Input phone");
        phone = Utils.input();
        admin = Utils.confirm("is admin");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setPhone(phone);
        user.setAdmin(admin);
        UserService.register(user);
        System.out.println("regiseter user successful");
        Utils.pressKeyToContinue();
    }
    private static void displayEmploy() {
        List<User> userList = UserPanelService.getUserList();
        DisplayUser.display(userList);
    }

    private static void removeEmploy() {
        User admin = UserPanelService.getUserByUsername("admin");
        List<User> userList = UserPanelService.getUserListWithout(admin);
        User user = SelectUser.select("select user to remove",userList,"user is empty");
        if(user != null)
        {
           if( Utils.confirm("Please confir to remove user " + user.getUsername()))
           {
               UserPanelService.removeUser(user);
           }
        }
    }

    private static void ranking() {
        List<RankUser> userList = UserPanelService.getRanking();
        DisplayRanking.display(userList);
    }



}
