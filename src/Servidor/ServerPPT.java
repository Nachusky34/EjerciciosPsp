package Servidor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerPPT {

    private static final int puerto = 3008;
    private static ArrayList<Jugador> jugadores = new ArrayList<>();
    private static ArrayList<Juego> partidas = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Servidor iniciado");
        int peticion = 0;

        try (ServerSocket servidor = new ServerSocket()){
            InetSocketAddress direccion = new InetSocketAddress(puerto);
            servidor.bind(direccion);

            System.out.println("SERVIDOR: Esperando peticion por el puerto " + puerto);

            while (true) {

                Socket socketAlCliente = servidor.accept();
                System.out.println("SERVIDOR: peticion numero " + ++peticion + " recibida");

                Jugador jugador = new Jugador(socketAlCliente);
                jugadores.add(jugador);

                if (peticion%2 == 0) {
                    System.out.println("Iniciando partida");
                    partidas.add(new Juego(jugadores.get(jugadores.size() - 2), jugadores.getLast()));
                    partidas.getLast().run();
                } else {
                    System.out.println("Buscando un segundo jugador");
                }
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
