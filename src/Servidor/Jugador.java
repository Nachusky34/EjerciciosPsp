package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

public class Jugador implements Runnable {

    private Socket socketCliente;
    private Juego juego;

    public Jugador(Socket socketCliente) {
        this.socketCliente = socketCliente;
        juego = new Juego();
    }

    @Override
    public void run() {
        try {
            if(socketCliente.isClosed()) {
                juego.clienteDesconectado();
            }
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

    public void mostrarMensaje(String mensaje) throws IOException {
        PrintStream salida = new PrintStream(socketCliente.getOutputStream());
        salida.println(mensaje);
    }
}
