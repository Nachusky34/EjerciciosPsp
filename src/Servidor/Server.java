package Servidor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int puerto = 3008;

    public static void main(String[] args) {
        System.out.println("Servidor iniciado");
        int peticion = 0;

        try (ServerSocket servidor = new ServerSocket()){
            InetSocketAddress direccion = new InetSocketAddress(puerto);
            servidor.bind(direccion);

            System.out.println("SERVIDOR: Esperando peticion por el puerto " + puerto);

            while (true) {
                //Por cada peticion de cliente aceptada se me crea un objeto socket diferente
                Socket socketAlCliente = servidor.accept();
                System.out.println("SERVIDOR: peticion numero " + ++peticion + " recibida");
                //Abrimos un hilo nuevo y liberamos el hilo principal para que pueda
                //recibir peticiones de otros clientes
                new Juego(socketAlCliente);
            }
        } catch (IOException e) {
            System.err.println("SERVIDOR: Error de entrada/salida");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("SERVIDOR: Error");
            e.printStackTrace();
        }
    }
}
