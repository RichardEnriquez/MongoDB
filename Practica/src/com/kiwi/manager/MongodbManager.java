package com.kiwi.manager;


import com.kiwi.Modelo.Empleado;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.*;

public class MongodbManager {
    private static MongodbManager instancia = null;
    private MongoDatabase database;
    private MongoClient mongoClient;

    //constructor
    private MongodbManager() {
        //antes de crear una session o cliente ocultaremos informacion que da mongodb al realizar la conexion
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

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

    /**
     * funcion que se encarga de cerrar session de mongodb cuando es llamada
     */
    public void sessionClose(){
        mongoClient.close();
    }

    /**
     * funcion que se encarga de verificar si el username existe en la bbdd collection empleado
     * usaremos esta funcion para detectar campo que no puede ser repetido aunque nos la permite meter en mongodb
     *
     * @param userName nombre username
     * @return false si no existe
     */
    public boolean ExistUserName(String userName){
        MongoCollection<Document> collection = database.getCollection("empleado");
        Document resultado = (Document) collection.find(
                        eq("username",userName)
        ).first();

        if(resultado != null){
            return true;
        }
        return false;
    }

    //**************** INSERTS ****************//

    /**
     * Funcion que se encarga de registrar un nuevo empleado en la base de datos
     * tendremos que pasarle un objeto tipo empleado
     * @param empleado Objeto con datos
     */
    public void insertEmpleado(Empleado empleado){
        MongoCollection<Document> collection = database.getCollection("empleado");
        collection.insertOne(new Document()
                .append("username", empleado.getUsername())
                .append("pass", empleado.getPass())
                .append("nombre", empleado.getNombre())
                .append("telefono", empleado.getTelefono())
        );

/*      Document resultado = collection.find(eq("nombre",empleado.getNombre())).first();
        System.out.println(resultado.getString("nombre"));*/
    }

    public Document datosUsuario(String userName){
        MongoCollection<Document> collection = database.getCollection("empleado");
        Document resultado = (Document) collection.find(
                eq("username",userName)

        ).first();

        return resultado;
    }






    //**************** FINDS ****************//
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
                                                eq("username",username),
                                                eq("pass",pass)
                                            )
                                        ).first();
        if(resultado != null){
            return true;
        }
        return false;
    }


    /**
     * funcion que se encargar de buscar el _id de un empleado que esta registrado en la colleccion buscado por su username como campo clave
     * @param userName nombre que buscaremos en la base de datos
     * @return _id en modo String
     */
    public String idUsuarioByUserName(String userName){
        MongoCollection<Document> collection = database.getCollection("empleado");
        Document resultado = (Document) collection.find(
                eq("username",userName)

        ).first();
        return resultado.getObjectId("_id").toString();
    }



    //**************** UPDATES ****************//

    /**
     * fucion que se encarga de actualizar los datos de un empleado buscando su _id
     * tendremos que pasarle el objeto empleado con los datos ya modificados
     * @param empleado objeto ya seteado
     */
    public void updateEmpleado(Empleado empleado){
        MongoCollection<Document> collection = database.getCollection("empleado");

        BasicDBObject search = new BasicDBObject();
        //primero intente modificar pasando el _id en modo String pero como no funciono
        //pense que si en ves de tipo String pongo un ObjetID y puuuuuuuuuuuuuum wala si sirve :D
        search.append("_id", new ObjectId(empleado.get_ID()));

        Document setData = new Document();
        setData.append("username", empleado.getUsername())
                .append("pass", empleado.getPass())
                .append("nombre",empleado.getNombre())
                .append("telefono",empleado.getTelefono());

        Document update = new Document();
        update.append("$set", setData);

        //System.out.println(collection.updateOne(search,update));
    }


    //**************** DELETES ****************//

    /**
     * funcion que se encarga de eliminar un empleado en la base de datos por su username
     * @param empleado
     */
    public void removeEmpleado(Empleado empleado){
        MongoCollection<Document> collection = database.getCollection("empleado");
        // Create the document to specify find criteria
        Document findDocument = new Document("username", empleado.getUsername());
        // Find one person and delete
        collection.findOneAndDelete(findDocument);
    }

    

}
