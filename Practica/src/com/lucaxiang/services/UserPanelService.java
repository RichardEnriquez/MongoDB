package com.lucaxiang.services;

import com.lucaxiang.IncidenceManager;
import com.lucaxiang.models.History;
import com.lucaxiang.models.Incidence;
import com.lucaxiang.models.RankUser;
import com.lucaxiang.models.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.*;

import static com.mongodb.client.model.Filters.*;

public class UserPanelService {

    public static List<User> getUserList()
    {
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getUserCollection();
        ArrayList<User> userList = new ArrayList<User>();
        for(Document document : collection.find())
        {
            userList.add(User.fromDocument(document));
        }
        return userList;
    }
    public static List<User> getUserListWithout(User user)
    {
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getUserCollection();
        ArrayList<User> userList = new ArrayList<User>();
        // buscar usuario que no tiene username igual que parametro user
        for(Document document : collection.find(not(eq("username",user.getUsername()))))
        {
            userList.add(User.fromDocument(document));
        }
        return userList;
    }
    public static List<Incidence> getIncidenceList()
    {
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getIncidenceCollection();
        ArrayList<Incidence> incidenceList = new ArrayList<Incidence>();
        for(Document document : collection.find())
        {
            incidenceList.add(Incidence.fromDocument(document));
        }
        return incidenceList;
    }
    public static User getUserByUsername(String username)
    {
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getUserCollection();
        Document document = collection.find(eq("username",username)).first();
        if(document == null)
        {
            return null;
        }
        else
        {
            return User.fromDocument(document);
        }
    }
    public static User getUserById(String id)
    {
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getUserCollection();
        Document document = collection.find(eq("_id", new ObjectId(id))).first();
        if(document == null)
        {
            return null;
        }
        else
        {
            return User.fromDocument(document);
        }
    }

    public static List<Incidence> getUserIncidence(User user) {

        String username = user.getUsername();
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getIncidenceCollection();
        FindIterable<Document> result =
                collection.find(or(eq("origin",username),eq("destin",username)));
        ArrayList<Incidence> incidenceList= new ArrayList<>();
        for(Document document : result)
        {
            incidenceList.add(Incidence.fromDocument(document));
        }
        if(incidenceList.size() == 0)
        {
            return null;
        }
        else
        {
            return incidenceList;
        }
    }

    public static List<Incidence> getUserIncidenceFilterWithField(String field, String value) {
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getIncidenceCollection();
        FindIterable<Document> result =
                collection.find(eq(field,value));
        ArrayList<Incidence> incidenceList= new ArrayList<>();
        for(Document document : result)
        {
            incidenceList.add(Incidence.fromDocument(document));
        }
        if(incidenceList.size() == 0)
        {
            return null;
        }
        else
        {
            return incidenceList;
        }
    }
    public static History getUserLastLogin(User user)
    {
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getHistoryCollection();
        Document document = collection.find(and(eq("username",user.getUsername()),eq("type","L"))).sort(
                new Document("dateTime",-1)
        ).first();
        return  History.fromDocument(document);
    }


    public static List<RankUser> getRanking() {
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getIncidenceCollection();
        HashMap<String,Integer> hashMap = new HashMap<>();
        FindIterable<Document> result = collection.find(eq("type","URGENT"));
        for(Document document : result)
        {
            Incidence incidence = Incidence.fromDocument(document);
            String username = incidence.getDestin();
            if(hashMap.containsKey(incidence.getDestin()))
            {
               hashMap.put(username, hashMap.get(username) + 1);
            }
            else
            {
                hashMap.put(username,1);
            }
        }
        ArrayList<RankUser> rankUsers = new ArrayList<>();
        for(Map.Entry<String,Integer> element : hashMap.entrySet())
        {
            RankUser rankUser = new RankUser();
            rankUser.setUsername(element.getKey());
            rankUser.setCount(element.getValue());
            rankUsers.add(rankUser);
        }
        rankUsers.sort((rankUser, t1) -> t1.getCount()- rankUser.getCount() );
        return rankUsers;
    }

    public static void newIncidence(Incidence incidence) {
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getIncidenceCollection();
        collection.insertOne(incidence.toDocument());
    }
    public static void newHistory(History history)
    {
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getHistoryCollection();
        collection.insertOne(history.toDocument());
    }
    public static void updateUser(User user,String newName,String newPassword,String newPhone)
    {
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getUserCollection();
        collection.updateOne(eq("username",user.getUsername()),
                new Document("$set",new Document("name",newName).append("password",newPassword).append("phone",newPhone)));
    }

    public static void removeUser(User user) {
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getUserCollection();
        collection.deleteOne(eq("username",user.getUsername()));
    }
}
