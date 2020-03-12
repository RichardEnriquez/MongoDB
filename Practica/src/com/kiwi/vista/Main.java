package com.kiwi.vista;

import com.kiwi.Modelo.Empleado;
import com.kiwi.excepciones.Excepciones;
import com.kiwi.manager.MongodbManager;
import org.bson.Document;


public class Main {
    public static MongodbManager mongodbManager = MongodbManager.getInstance();
    private static Empleado empleadoLogueado = null;
    public static void main(String[] args) {

        boolean start = true;
        do {
            try {
                if (empleadoLogueado == null){
                    loginEmpleado();
                }

                switch (vistaMenu()) {
                    case 1:
                        insertarEmpleado();
                        break;

                    case 2:
                        updateEmpleado();
                        break;


/*                    case 10:
                        mongodbManager.idUsuarioByUserName("x");
                        break;*/


                    case 0:
                        start = false;
                        break;
                    default:
                        System.out.println("Opcion desconocida");
                        break;
                }

            }catch (Excepciones ex){
                System.out.println(ex.getMessage());
            }
        }while (start);
        //cuando termine la aplicacion podremos cerrar base de datos
        mongodbManager.sessionClose();


    }

    public static int vistaMenu(){
        System.out.println("*--- BIENVENIDO ---*");
        System.out.println("1. Nuevo empleado");
        System.out.println("2. Editar perfil");
        System.out.println("0. Salir");

        int opcion = InputAsker.askInt("Seleccione opcion: ");
        return opcion;
    }

    /**
     * funcion que se encarga de pedir datos para usar una funcion que dara de alta a un nuevo empleado
     */
    public static void insertarEmpleado(){
        System.out.println("*Registro nuevo empleado*");

        String nombreUsu = InputAsker.askString("Indique User name: ");
        String pass = InputAsker.askString("Indique Password: ");
        String nombre = InputAsker.askString("Indique nombre: ");
        int telefono = InputAsker.askInt("Indique telefono: ");

        //despues de crear el objeto empleado podremos usar funcion que esta en manager
        Empleado empleado = new Empleado(nombreUsu,pass,nombre,telefono);
        mongodbManager.insertEmpleado(empleado);

        System.out.println("Empleado: "+nombre+" creado correctamente \n");
    }

    /**
     * funcion que se encarga de iniciar session, cuando el usuario se loguee con username y pass
     * crearemos un objeto que estara en main para poder usarlo mientras la aplicacion este arrancada
     * setearemos el id porque al iniciar el objeto tiene un campo _id = null
     * esto lo hice por que mongodb ya proporciona un id al registrar un documento en su colleccion
     * @throws Excepciones
     */
    public static void loginEmpleado() throws Excepciones {
        String username = InputAsker.askString("Nombre de usuario");
        String pass = InputAsker.askString("Password");
        System.out.print("\n");

        if (!mongodbManager.loginEmpleado(username,pass)){
            throw new Excepciones(1);
        }

        Document datos = mongodbManager.datosUsuario(username);
        String _id = datos.getObjectId("_id").toString();
        //username
        //pass
        String nombre = datos.get("nombre").toString();
        int telefono = datos.get("telefono").hashCode();

        Empleado empleado = new Empleado(username,pass,nombre,telefono);
        empleado.set_ID(_id);
        empleadoLogueado = empleado;

    }


    /**
     * funcion que se encarga de mostrar en modo basico los datos del usuario logueado
     * y pide que campo quiere modificar, dentro de username verificaremos que no este en uso si quiere cambiarlo
     * @throws Excepciones
     */
    public static void updateEmpleado() throws Excepciones {
        System.out.println("\n"+empleadoLogueado);

        String campo = InputAsker.askString("Indique que campo quiere cambiar: ");
        switch (campo.toLowerCase()){
            case "username":
                String username = InputAsker.askString("Nuevo user name: ");
                if (username.equals(empleadoLogueado.getUsername())){
                    throw new Excepciones(4);
                }

                if (mongodbManager.ExistUserName(username)){
                    System.out.println("Este nombre de usuario ya esta en uso \n");
                }else {
                    empleadoLogueado.setUsername(username);
                    mongodbManager.updateEmpleado(empleadoLogueado);
                }
                break;

            case "pass":
                String pass = InputAsker.askString("Indique nuevo password: ");
                String confirmacion = InputAsker.askString("Repita la contraseña");
                if (!pass.equals(confirmacion)){
                    throw new Excepciones(2);
                }
                empleadoLogueado.setPass(pass);
                mongodbManager.updateEmpleado(empleadoLogueado);
                break;

            case "nombre":
                String nombre = InputAsker.askString("Nuevo nombre: ");
                empleadoLogueado.setNombre(nombre);
                mongodbManager.updateEmpleado(empleadoLogueado);
                break;

            case "telefono":
                int telf = InputAsker.askInt("Nuevo telefono: ");
                empleadoLogueado.setTelefono(telf);
                mongodbManager.updateEmpleado(empleadoLogueado);
                break;

            default:
                throw new Excepciones(3);
        }

    }
}
