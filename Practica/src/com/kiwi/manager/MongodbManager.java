package com.kiwi.manager;


import com.kiwi.Modelo.Empleado;
import com.kiwi.Modelo.Historial;
import com.kiwi.Modelo.Incidencia;
import com.kiwi.Modelo.RankingTO;
import com.kiwi.excepciones.Excepciones;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

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

        //cuando es iniciado verificaremos si hay un usuario admin para que se pueda usar la aplicacion
        generateAdmin();
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


    /**
     * Funcion que se encarga de retornar una incidencia por el id del que se logueo
     * en este caso el id es el username (Perdon mar :( )
     * @param id username del logueado
     * @return Incidencia
     * @throws Excepciones si no hay ninguna incidencia asignada al username saltara excepcion
     */
    public Incidencia getIncidenciaById(String id) throws Excepciones {

        MongoCollection<Document> collection = database.getCollection("incidencia");

        Document resultado = (Document) collection.find(
                eq("destino",id)
        ).first();

        if (resultado == null){
            throw new Excepciones(6);
        }

        String fecha = resultado.getString("fechaHora");
        String origen = resultado.getString("origen");
        String destino = resultado.getString("destino");
        String detalle = resultado.getString("detalle");
        String tipo = resultado.getString("tipo");

        Incidencia incidencia = new Incidencia(fecha,origen,destino,detalle,tipo);
        return incidencia;
    }

    /**
     * funcion que se encarga de retornar una lista con todas las incidencias que existen
     * @return List<Incidencia>
     */
    public List<Incidencia> selectAllIncidencias(){
        MongoCollection<Document> collection = database.getCollection("incidencia");

        FindIterable<Document> resultado = collection.find();
        List<Incidencia> incidencias = new ArrayList<>();

        for (Document doc : resultado) {
            String fecha = doc.getString("fechaHora");
            String origen = doc.getString("origen");
            String destino = doc.getString("destino");
            String detalle = doc.getString("detalle");
            String tipo = doc.getString("tipo");

            Incidencia incidencia = new Incidencia(fecha,origen,destino,detalle,tipo);
            incidencias.add(incidencia);
        }

        return incidencias;
    }


    /**
     * funcion que se encarga de registrar un objeto tipo incidencia en la bbdd callection incidencia
     * @param i objeto con datos
     */
    public void insertIncidencia(Incidencia i){
        MongoCollection<Document> collection = database.getCollection("incidencia");
        collection.insertOne(new Document()
                .append("fechaHora", i.getFechaHora())
                .append("origen", i.getOrigen())
                .append("destino", i.getDestino())
                .append("detalle", i.getDetalle())
                .append("tipo", i.getTipo())
        );
    }

    /**
     * funcion que se encarga de retornar todos los empleados con sus datos registrados en la base de datos
     * @return list<Empleados>
     */
    public List<Empleado> listaEmpleados(){
        MongoCollection<Document> collection = database.getCollection("empleado");

        FindIterable<Document> resultado = collection.find();
        List<Empleado> empleados = new ArrayList<>();

        for (Document doc : resultado) {

            ObjectId _ID = doc.getObjectId("_id");
            String username = doc.getString("username");
            String pass = doc.getString("pass");
            String nombre = doc.getString("nombre");
            int telefono = doc.getInteger("telefono");

            Empleado empleado = new Empleado(username,pass,nombre,telefono);
            empleado.set_ID(_ID.toString());
            empleados.add(empleado);
        }
        return empleados;
    }

    public Empleado getEmpleadoByUsername(String username){
        MongoCollection<Document> collection = database.getCollection("empleado");
        Document resultado = (Document) collection.find(
                eq("username",username)

        ).first();


        Empleado empleado = new Empleado(
                (String) resultado.get("username"),
                (String) resultado.get("pass"),
                (String) resultado.get("nombre"),
                (Integer) resultado.get("telefono")

                );
        empleado.set_ID(resultado.getObjectId("_id").toString());

        return empleado;
    }


    /**
     * funcion que se encarga de mostrar las incidencias de un empleado por su username y si perdon mar pero el modelo esta asi :(
     * @param e Empleado que buscaremos por username
     * @return Lista de incidencia
     * @throws Excepciones por si el destino es erroneo
     */
    public List<Incidencia> getIncidenciaByDestino(Empleado e) throws Excepciones {
        MongoCollection<Document> collection = database.getCollection("incidencia");

        FindIterable<Document> resultado = collection.find(
                eq("destino",e.getUsername())
        );

        if (resultado == null){
            throw new Excepciones(7);
        }

        List<Incidencia> incidencias = new ArrayList<>();
        for (Document doc : resultado) {

            String fecha = doc.getString("fechaHora");
            String origen = doc.getString("origen");
            String destino = doc.getString("destino");
            String detalle = doc.getString("detalle");
            String tipo = doc.getString("tipo");

            Incidencia incidencia = new Incidencia(fecha, origen, destino, detalle, tipo);
            incidencias.add(incidencia);
        }
        return incidencias;
    }

    /**
     * funcion que se encarga de retirnar una lista de todas las incidencias de un empleado de origen
     * @param e empleado con sus datos
     * @return list Incidencias
     * @throws Excepciones por si no se encunetra con ningun documneto con orgien
     */
    public List<Incidencia> getIncidenciaByOrigen(Empleado e) throws Excepciones {
        MongoCollection<Document> collection = database.getCollection("incidencia");

        FindIterable<Document> resultado = collection.find(
                eq("origen",e.getUsername())
        );

        if (resultado == null){
            throw new Excepciones(7);
        }

        List<Incidencia> incidencias = new ArrayList<>();
        for (Document doc : resultado) {

            String fecha = doc.getString("fechaHora");
            String origen = doc.getString("origen");
            String destino = doc.getString("destino");
            String detalle = doc.getString("detalle");
            String tipo = doc.getString("tipo");

            Incidencia incidencia = new Incidencia(fecha, origen, destino, detalle, tipo);
            incidencias.add(incidencia);
        }
        return incidencias;
    }

    public void insertarEvento(Historial historial){
        MongoCollection<Document> collection = database.getCollection("historial");
        collection.insertOne(new Document()
                .append("tipoEvent", historial.getTipoEvent())
                .append("fechaHora", historial.getFechaHora())
                .append("nombreUsuario", historial.getNombreUsuario())
        );

    }

    /**
     * funcion que se encarga de retornar objeto historial de un empleado pasado por parametro
     * @param e empleado que buscaremos sus datos de historial
     * @return Historial
     * @throws Excepciones por si el usuario buscado no a iniciado session aun
     */
    public Historial getUltimoInicioSesion(Empleado e) throws Excepciones {
        MongoCollection<Document> collection = database.getCollection("historial");

        FindIterable<Document> resultado = collection.find(
                and(
                        eq("tipoEvent","I"),
                        eq("nombreUsuario", e.getUsername())
                )
        );
        //ascendente (1) o descendente (-1)
        Document sortingDocument = new Document("fechaHora", -1);
        FindIterable<Document> listaOrdenada = resultado.sort(sortingDocument);
        Document datos = listaOrdenada.first();

        if (datos == null){
           throw  new Excepciones(10);
        }
        Historial historial = new Historial(
                datos.getString("tipoEvent"),
                datos.getString("fechaHora"),
                datos.getString("nombreUsuario")
        );

        return historial;
    }


    /**
     * funcion que se encarga de devolver una lista con el top de empleados que tengan mas incidencias urgentes asignadas
     * @return List<RankingTo>
     */
    public List<RankingTO> getRankingEmpleados(){
        MongoCollection<Document> collection = database.getCollection("incidencia");
        List<RankingTO> ranking = new ArrayList<>();
        List<Empleado> empleados = listaEmpleados();
        for (Empleado empleado: empleados){

            FindIterable<Document> resultado = collection.find(
                    and(
                            eq("destino", empleado.getUsername()),
                            eq("tipo","urgente")
                    )
            );
            int cantidad = 0;
            for (Document x: resultado){
                cantidad++;
            }
            ranking.add(new RankingTO(empleado,cantidad));
        }
        Collections.sort(ranking, new Comparator<RankingTO>() {
            @Override
            public int compare(RankingTO p1, RankingTO p2) {
                return new Integer(p2.getCantidadIncidencias()).compareTo(new Integer(p1.getCantidadIncidencias()));
            }
        });
        return ranking;
    }


    private void generateAdmin(){
        if (!ExistUserName("admin")){
            Empleado empleado = new Empleado("admin","admin","admin",000000000);
            insertEmpleado(empleado);
        }

    }
}
