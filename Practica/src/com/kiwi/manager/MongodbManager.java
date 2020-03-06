package com.kiwi.manager;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongodbManager {
    private static MongodbManager instancia = null;
    private MongoDatabase database;
    private MongoClient mongoClient;

    //constructor
    private MongodbManager() {
        mongoClient = new MongoClient();
        database = mongoClient.getDatabase("incidencias_system_manager");

    }

    /**
     * funcion  que se encarga de devolver instancia y si se llama por primera vez
     * nos encargaremos de instanciar la base de datos
     * @return
     */
    public static MongodbManager getInstance(){
        if(instancia == null){
            instancia = new MongodbManager();
        }
        return instancia;
    }

    


}
