import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorPPT {

    private static final int puerto = 3008;
    private ArrayList<Byte> opciones;

    public void iniciarServer() throws IOException {
        InetSocketAddress direccion = new InetSocketAddress(puerto);


        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(direccion);
            System.out.println("Servidor iniciado");

            while (true) {
                conexionCliente(serverSocket);
            }
        }
    }

    public void conexionCliente(ServerSocket serverSocket) throws IOException {
        try (Socket socketCliente = serverSocket.accept()) {
            System.out.println("Cliente conectado");
            Hilos h1 = new Hilos(serverSocket);
            h1.run();

            try (Socket socketCliente1 = serverSocket.accept()){
                Hilos h2 = new Hilos(serverSocket);
                h2.run();
            }
        }
    }

    public byte solicitarInformacion() throws IOException {
        byte opcion = 0;
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Introduzca la opcion: \n" +
                "1. Piedra \n" +
                "2. Papel\n" +
                "3. Tijera");
        opcion = Byte.parseByte(entrada.readLine());
        return opcion;
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

    public boolean calcularGanador() throws IOException {
        byte victorias1 = 0;
        byte victorias2 = 0;
        byte opcion;

        while (victorias1 < 5 || victorias2 < 5) {
            opcion = calcularJugada(getOpcion1(), getOpcion2());

            if (opcion == 0) {
                System.out.println("Empate");
            } else if (opcion == 1) {
                System.out.println("Victoria de jugador 1. Lleva " + victorias1 + " victorias");
                victorias1++;
            } else if (opcion == 2) {
                System.out.println("Victoria de jugador 2. Lleva " + victorias2 + " victorias");
                victorias2++;
            }
            solicitarInformacion();
        }

        return victorias1 < 5;
    }

    public byte getOpcion1() {
        return opciones.getFirst();
    }

    public byte getOpcion2() {
        return opciones.get(1);
    }

    public void addOpciones(byte opcion) {
        opciones.add(opcion);
    }
}
