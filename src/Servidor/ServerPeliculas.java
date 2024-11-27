package Servidor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerPeliculas {

    private final static int PUERTO = 238;

    public static void main(String[] args) throws IOException {

        try(ServerSocket servidor = new ServerSocket()){
            InetSocketAddress direccion = new InetSocketAddress(PUERTO);
            servidor.bind(direccion);
            System.out.println("Servidor iniciado");

            while (true){
                System.out.println("Esperando un cliente");
                Socket socketCliente = servidor.accept();
                System.out.println("Cliente encontrado");

                new ManejoPeliculas(socketCliente);
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
