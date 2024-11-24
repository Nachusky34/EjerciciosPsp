package EjercicioAEntregar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente2 {
    private static final int puerto = 3009;
    private static final String IP_SERVER = "localhost";
    //10.34.121.251

    public static void main(String[] args) {
        InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, puerto);
        Socket socketAlServidor;

        try (Scanner sc = new Scanner(System.in)) {
            socketAlServidor = new Socket();
            socketAlServidor.connect(direccionServidor);
            PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketAlServidor.getInputStream()));

            boolean continuar = true;

            while (continuar) {
                System.out.println("Elije una opcion: \n1. Piedra\n2. Papel\n3. Tijera");
                salida.println(sc.nextByte());
                continuar = Boolean.parseBoolean(entrada.readLine());
            }

            if (Boolean.parseBoolean(entrada.readLine())) {
                System.out.println("Has ganado");
            } else {
                System.out.println("Has perdido");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
