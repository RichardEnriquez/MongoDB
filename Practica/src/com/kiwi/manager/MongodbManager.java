package com.kiwi.manager;


import com.kiwi.Modelo.Empleado;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

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



    //FUNCIONES
    //**************** INSERT ****************//
    public void insertEmpleado(Empleado empleado){
        MongoCollection<Document> collection = database.getCollection("empleado");
        collection.insertOne(new Document()
                .append("nombre", empleado.getNombreUsu())
                .append("pass", empleado.getPass())
                .append("nombre", empleado.getNombre())
                .append("telefono", empleado.getTelefono())
        );

       /* Document resultado = collection.find(eq("nombre","nombre que buscas")).first();
        System.out.println(resultado.getString("nombre"));*/
    }

}
