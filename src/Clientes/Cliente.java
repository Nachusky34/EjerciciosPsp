package Clientes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static final int puerto = 3008;
    public static final String IP_SERVER = "localhost";
    //10.34.121.251

    public static void main(String[] args) {
        InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, puerto);

        try (Scanner sc = new Scanner(System.in)) {
            Socket socketAlServidor = new Socket();
            socketAlServidor.connect(direccionServidor);
            PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketAlServidor.getInputStream()));

            //Pedimos al usuario lo que tiene de opciones
            System.out.println("1. Piedra\n2. Papel\n3. Tijera");
            salida.println(sc.nextByte());
            System.out.println(entrada.readLine());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
