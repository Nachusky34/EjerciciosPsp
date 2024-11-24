package EjercicioAEntregar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {

    private static final int puerto = 3009;
    private static int victorias1 = 0;
    private static int victorias2 = 0;
    private static Socket socket1 = null;
    private static Socket socket2 = null;

    public static void main(String[] args) throws IOException {

        while (true) {

            Servidor servidor = new Servidor();
            System.out.println("Servidor iniciado");

            try (ServerSocket serverSocket = new ServerSocket()) {
                InetSocketAddress direccion = new InetSocketAddress(puerto);
                serverSocket.bind(direccion);

                victorias1 = 0;
                victorias2 = 0;
                System.out.println("SERVIDOR: Esperando peticion por el puerto " + puerto);

                socket1 = serverSocket.accept();
                System.out.println("Esperando una segunda peticion");
                socket2 = serverSocket.accept();
                System.out.println("Partida iniciada");

                if (servidor.juego()) {
                    System.out.println("El jugaor 1 ha ganado");
                } else {
                    System.out.println("El jugaor 2 ha ganado");
                }

                System.out.println("Juego finalizado");

            } catch (IOException e) {
                System.err.println("SERVIDOR: Error de entrada/salida");
            } catch (Exception e) {
                System.err.println("SERVIDOR: Error");
                e.printStackTrace();
            }
        }

    }

    public boolean juego() throws IOException {
        PrintStream salida1 = new PrintStream(socket1.getOutputStream());
        PrintStream salida2 = new PrintStream(socket2.getOutputStream());

        while (victorias1 < 5 && victorias2 < 5) {
            try {
                salida1.println(false);
                salida2.println(false);

                int ganador = jugada();
                if (ganador == 1) {
                    victorias1++;
                    System.out.println("El jugador 1 ha ganado esta ronda");
                } else if (ganador == 2) {
                    victorias2++;
                    System.out.println("El jugador 2 ha ganado esta ronda");
                } else {
                    System.out.println("Empate en esta ronda");
                }
                continuar(socket1, socket2);

            } catch (IOException e) {
                System.out.println("Uno de los clientes se ha desconectado");
                return false;
            }
        }
        salida1.println(victorias2 != 5);
        salida2.println(victorias1 != 5);

        return victorias2 != 5;
    }


    public int jugada() throws IOException {
        int jugada1 = pedirNumero1();
        System.out.println("El jugador 1 ha elegido");
        int jugada2 = pedirNumero2();
        System.out.println("El jugador 2 ha elegido");

        if (jugada1 == jugada2) {
            return 0;
        } else if ((jugada2 + 1) == jugada1 || (jugada2 == 3 && jugada1 == 1)) {
            return 1;
        } else {
            return 2;
        }
    }

    public int pedirNumero1() throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
        return Integer.parseInt(entrada.readLine());
    }

    public int pedirNumero2() throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
        return Integer.parseInt(entrada.readLine());
    }

    public void continuar(Socket socket1, Socket socket2) throws IOException {
        PrintStream salida1 = new PrintStream(socket1.getOutputStream());
        PrintStream salida2 = new PrintStream(socket2.getOutputStream());

        boolean continuar = victorias1 < 5 && victorias2 < 5;

        salida1.println(continuar);
        salida2.println(continuar);
    }
}
