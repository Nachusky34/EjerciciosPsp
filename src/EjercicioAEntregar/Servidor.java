package EjercicioAEntregar;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private static final int puerto = 3009;
    private int victorias1 = 0;
    private int victorias2 = 0;

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        System.out.println("Servidor iniciado");

        try (ServerSocket serverSocket = new ServerSocket()) {
            InetSocketAddress direccion = new InetSocketAddress(puerto);
            serverSocket.bind(direccion);

            System.out.println("SERVIDOR: Esperando peticion por el puerto " + puerto);

            Socket socketAlCliente1 = serverSocket.accept();
            System.out.println("Esperando una segunda peticion");
            Socket socketAlCliente2 = serverSocket.accept();

            if(servidor.juego()){
                System.out.println("El jugaor 1 ha ganado");
            } else {
                System.out.println("El jugaor 2 ha ganado");
            }

        } catch (
                IOException e) {
            System.err.println("SERVIDOR: Error de entrada/salida");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("SERVIDOR: Error");
            e.printStackTrace();
        }
    }

    public boolean juego(){
        while (victorias1 < 5 && victorias2 < 5){
            int ganador = jugada();
            if (ganador == 1){
                victorias1++;
            } else if (ganador == 2) {
                victorias2++;
            } else {
                System.out.println("Empate");
            }
        }
        if (victorias2 == 5){
            return false;
        }
        return true;
    }

    public int jugada(){
        int jugada1 = 0;
        int jugada2 = 0;

        if (jugada1 == jugada2) {
             return 0;
        } else if ((jugada2 + 1) == jugada1 || (jugada2 == 3 && jugada1 == 1)) {
            return 1;
        } else {
            return 2;
        }
    }
}
