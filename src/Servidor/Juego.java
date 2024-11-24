package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Juego implements Runnable {

    private byte victorias1;
    private byte victorias2;
    private byte opcion1;
    private byte opcion2;
    private Jugador jugador1;
    private Jugador jugador2;

    public Juego(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        opcion1 = -1;
        opcion2 = 0;
    }

    public Juego(){
        opcion1 = -1;
        opcion2 = 0;
    }

    @Override
    public void run() {
        jugador1.run();
        jugador2.run();
        while (victorias1 < 5 || victorias2 < 5) {
            byte ganador;
            try {
                ganador = calcularGanador();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (ganador == 1) {
                victorias1++;
            } else if (ganador == 2) {
                victorias2++;
            }
            try {
                jugador1.opcion();
                jugador2.opcion();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public byte calcularJugada(byte jugada1, byte jugada2) {
        if (jugada1 == jugada2) {
            return 0;
        } else if ((jugada2 + 1) == jugada1 || (jugada2 == 3 && jugada1 == 1)) {
            return 1;
        } else {
            return 2;
        }
    }

    public byte calcularGanador() throws IOException {
        byte ganador = -1;
        byte opcion;

        opcion = calcularJugada(getOpcion1(), getOpcion2());

        if (opcion == 0) {
            System.out.println("Empate");
            jugador1.mostrarMensaje("Empate");
            jugador2.mostrarMensaje("Empate");
            ganador = 0;
        } else if (opcion == 1) {
            System.out.println("Victoria de jugador 1. Lleva " + victorias1 + " victorias");
            jugador1.mostrarMensaje("Victoria de jugador 1. Lleva " + victorias1 + " victorias");
            jugador2.mostrarMensaje("Victoria de jugador 1. Lleva " + victorias1 + " victorias");
            ganador = 1;
        } else if (opcion == 2) {
            System.out.println("Victoria de jugador 2. Lleva " + victorias2 + " victorias");
            jugador1.mostrarMensaje("Victoria de jugador 2. Lleva " + victorias2 + " victorias");
            jugador2.mostrarMensaje("Victoria de jugador 2. Lleva " + victorias2 + " victorias");
            ganador = 2;
        }

        return ganador;
    }

    public byte getOpcion1() {
        return opcion1;
    }

    public void setOpcion1(byte opcion1) {
        this.opcion1 = opcion1;
    }

    public byte getOpcion2() {
        return opcion2;
    }

    public void setOpcion2(byte opcion2) {
        this.opcion2 = opcion2;
    }

    public boolean clienteDesconectado() {
        return true;
    }
}
