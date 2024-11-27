package Clientes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientePelicula {

    public static final int PUERTO = 238;
    public static final String IP_SERVER = "localhost";

    public static void main(String[] args) {
        InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);

        Socket socket = null;
        Scanner sc = null;

        try {
            // Inicializar Socket y Scanner
            socket = new Socket();
            socket.connect(direccionServidor);
            sc = new Scanner(System.in);

            // Crear flujos de entrada y salida
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String opcion;

            do {
                System.out.println("Menu:");
                System.out.println("1. Consultar película por ID");
                System.out.println("2. Consultar película por título");
                System.out.println("3. Consultar películas por director");
                System.out.println("4. Agregar película");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextLine();
                salida.println(opcion);

                switch (opcion) {
                    case "1":
                        System.out.print("Ingrese el ID de la película: ");
                        int id = 0;
                        while (true){
                            try{
                                id = sc.nextInt();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Introduce un numero valido");
                            }
                        }
                        salida.println(id);
                        System.out.println("Respuesta del servidor: " + entrada.readLine());
                        break;

                    case "2":
                        System.out.print("Ingrese el título de la película: ");
                        String titulo = sc.nextLine();
                        salida.println(titulo);
                        System.out.println("Respuesta del servidor: " + entrada.readLine());
                        break;

                    case "3":
                        System.out.print("Ingrese el director: ");
                        String director = sc.nextLine();
                        salida.println(director);
                        System.out.println("Respuesta del servidor: " + entrada.readLine());
                        break;

                    case "4":
                        String[] datos = new String[3];
                        System.out.print("Ingrese el titulo: ");
                        datos[0] = sc.nextLine();
                        System.out.print("Ingrese el director: ");
                        datos[1] = sc.nextLine();
                        System.out.print("Ingrese el precio: ");
                        datos[2] = sc.nextLine();
                        salida.println(datos[0] + "-" + datos[1] + "-" + datos[2]);
                        System.out.println("Respuesta del servidor: " + entrada.readLine());
                        break;

                    case "5":
                        System.out.println("Saliendo...");
                        salida.println("SALIR");
                        socket.close();
                        break;

                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            } while (!opcion.equals("5"));

            socket.close();

        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
