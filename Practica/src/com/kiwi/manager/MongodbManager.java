package com.kiwi.manager;


import com.kiwi.Modelo.Empleado;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;

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
     * @return instancia de esta clase para usar las funciones
     */
    public static MongodbManager getInstance(){
        if(instancia == null){
            instancia = new MongodbManager();
        }
        return instancia;
    }



    //FUNCIONES
    //**************** INSERT ****************//

    /**
     * Funcion que se encarga de registrar un nuevo empleado en la base de datos
     * tendremos que pasarle un objeto tipo empleado
     * @param empleado Objeto con datos
     */
    public void insertEmpleado(Empleado empleado){
        MongoCollection<Document> collection = database.getCollection("empleado");
        collection.insertOne(new Document()
                .append("nombreUsu", empleado.getNombreUsu())
                .append("pass", empleado.getPass())
                .append("nombre", empleado.getNombre())
                .append("telefono", empleado.getTelefono())
        );

/*        Document resultado = collection.find(eq("nombre",empleado.getNombre())).first();
        System.out.println(resultado.getString("nombre"));*/
    }

    /**
     * funcion que se encarga de buscar a un usuario y comparar dos campos para poder hacer el login
     * @param username
     * @param pass
     * @return return false si no lo encuentra o si los parametros pasados son incorrectos
     */
    public boolean loginEmpleado(String username, String pass){
        MongoCollection<Document> collection = database.getCollection("empleado");
        Document resultado = (Document) collection.find(
                                            and(
                                                eq("nombreUsu",username),
                                                eq("pass",pass)
                                            )
                                        ).first();
        if(resultado == null){
            return false;
        }
        return true;
    }

}
