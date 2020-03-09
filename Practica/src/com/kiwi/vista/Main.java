package com.kiwi.vista;

import com.kiwi.Modelo.Empleado;
import com.kiwi.excepciones.Excepciones;
import com.kiwi.manager.MongodbManager;

public class Main {
    public static MongodbManager mongodbManager = MongodbManager.getInstance();
    public static void main(String[] args) {



        boolean start = true;
        do {

            switch (vistaMenu()){
                case 1:
                    insertarEmpleado();
                    break;




                case 0:
                    start = false;
                    break;
                default:
                    System.out.println("Opcion desconocida");
                    break;
            }
        }while (start);


    }

    public static int vistaMenu(){
        System.out.println("*--- BIENVENIDO ---*");
        System.out.println("1. Nuevo empleado");
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

        System.out.println("Empleado: "+nombre+" creado correctamente");
    }

    public static void loginEmpleado() throws Excepciones {
        String username = InputAsker.askString("Nombre de usuario");
        String pass = InputAsker.askString("Password");

        if (!mongodbManager.loginEmpleado(username,pass)){
            throw new Excepciones(1);
        }

    }


}
