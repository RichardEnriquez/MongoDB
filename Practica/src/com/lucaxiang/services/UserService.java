package com.lucaxiang.services;

import com.lucaxiang.IncidenceManager;
import com.lucaxiang.exceptions.ErrorCode;
import com.lucaxiang.exceptions.ManagerException;
import com.lucaxiang.models.User;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class UserService {

    public static User login(String username, String password) throws ManagerException
    {
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getUserCollection();
        // si nombre de usuario es vacio
        if(username.isEmpty())
        {
            throw new ManagerException(ErrorCode.ERROR_1);
        }
        // si contrasenya es vacio
        else if(password.isEmpty())
        {
            throw new ManagerException(ErrorCode.ERROR_2);
        }
        // hacer un consulta
        Document document =
                collection.find(and(
                        eq("username",username),
                        eq("password",password))).first();
        // si no hay resultado
        if(document == null)
        {
            throw new ManagerException("username or password incorrect!!");
        }
        else
        {
            return User.fromDocument(document);
        }
    }
    // para registrar un usuario
    public static void register(User user)
    {
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getUserCollection();
        collection.insertOne(user.toDocument());
    }
    // para comprobar un nombre de usuario ya existe o no
    public static boolean existUsername(String username)
    {
        IncidenceManager incidenceManager = IncidenceManager.getInstance();
        MongoCollection<Document> collection = incidenceManager.getUserCollection();
        Document document = collection.find(eq("username",username)).first();
        return document != null;
    }
}
