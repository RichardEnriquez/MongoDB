package com.lucaxiang;


import com.lucaxiang.controller.Login;
import com.lucaxiang.models.User;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

public class IncidenceManager {

    private final static String DATABASE_NAME = "incidence";
    private static IncidenceManager instance = null;
    public final static String USER_COL = "user";
    public final static String INCIDENCE_COL = "incidence";
    public final static String HISTORY_COL = "history";
    public static IncidenceManager getInstance() {
        if(instance == null)
        {
            instance = new IncidenceManager();
        }
        return instance;
    }
    private MongoClient  mongoClient = null;
    private MongoDatabase database   = null;
    private IncidenceManager()
    {
        connectToDataBase();
        initDataBase();
    }


    private void connectToDataBase() {
        try
        {
            //ocultar message debug
            //antes de crear una session o cliente ocultaremos informacion que da mongodb al realizar la conexion
            Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
            mongoLogger.setLevel(Level.SEVERE);
            //por defecto se conecta a mongodb://localhost:27017
            mongoClient = new MongoClient();
            database = mongoClient.getDatabase(DATABASE_NAME);
        }
        catch (MongoClientException e) {
            System.err.println("Can't connect to Mongo Service !");
            System.exit(-1);
        }
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getDatabase() {
        return database;
    }
    private void initDataBase() {

        MongoCollection<Document> userCollection = database.getCollection(USER_COL);
        //comprobar si exit un usuario admin o  no
        Document queryResult = userCollection.find(eq("username","admin")).first();
        // si no existe
        if(queryResult == null)
        {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setPhone("123");
            admin.setName("admin");
            admin.setAdmin(true);
            //crear un usuario admin-admin
            userCollection.insertOne(admin.toDocument());

            //crear 5 usuario para probar
            for(int i=0; i<5; i++)
            {
                User user = new User();
                String v = String.valueOf(i);
                user.setUsername(v);
                user.setPassword(v);
                user.setPhone(v);
                user.setName(v);
                user.setAdmin(false);
                userCollection.insertOne(user.toDocument());
            }

        }
    }
    public MongoCollection<Document> getUserCollection()
    {
        return  database.getCollection(IncidenceManager.USER_COL);
    }
    public MongoCollection<Document> getIncidenceCollection()
    {
        return  database.getCollection(IncidenceManager.INCIDENCE_COL);
    }
    public MongoCollection<Document> getHistoryCollection()
    {
        return  database.getCollection(IncidenceManager.HISTORY_COL);
    }

    public void start()
    {
        String[] options = new String[]{"Login"};
        Utils.SimpleMenu simpleMenu = new Utils.SimpleMenu("Incidence Manager",options,"Exit");
        boolean shouldExit = false;
        do {
            switch (simpleMenu.select())
            {
                case  1:
                    Login.start();
                    break;
                case  0:
                    shouldExit = true;
                    break;
            }
        }while (!shouldExit);
    }
}
