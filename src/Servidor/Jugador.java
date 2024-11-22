package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Jugador implements Runnable {

    private Socket socketCliente;
    private Juego juego;
    private Thread hilo;
    private static int numCliente = 0;

    public Jugador(Socket socketCliente) {
        hilo = new Thread(this, "Jugador : " + numCliente);
        this.socketCliente = socketCliente;
    }

    @Override
    public void run() {
        try {
            opcion();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void opcion() throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

        System.out.println("Introduzca la opcion: \n" +
                "1. Piedra \n" +
                "2. Papel\n" +
                "3. Tijera");

        if (juego.getOpcion1() == -1){
            juego.setOpcion1(Byte.parseByte(entrada.readLine()));
        } else {
            juego.setOpcion2(Byte.parseByte(entrada.readLine()));
        }
    }
}
