import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorPPT {

    private static final int puerto = 3008;
    private byte numClientes = 0;

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
        try(Socket socketCliente = serverSocket.accept()){
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            PrintStream salida = new PrintStream(socketCliente.getOutputStream());
            numClientes++;
            System.out.println("Cliente conectado");

            if (numClientes % 2 == 0){
                if (calcularGanador()){
                    System.out.println("Victoria del jugador 2");
                } else {
                    System.out.println("Victoria del jugador 1");
                }
            }
        }
    }

    public byte calcularJugada(byte jugada1, byte jugada2){
        if (jugada1 == jugada2){
            return 0;
        } else if ((jugada2 + 1) == jugada1 || (jugada2 == 3 && jugada1 == 1)){
            return 1;
        } else {
            return 2;
        }
    }

    public boolean calcularGanador(){
        byte victorias1 = 0;
        byte victorias2 = 0;
        byte opcion;

        while (victorias1 < 5 || victorias2 < 5) {
            opcion = calcularJugada();

            if (opcion == 0) {
                System.out.println("Empate");
            } else if (opcion == 1) {
                System.out.println("Victoria de jugador 1. Lleva " + victorias1 + " victorias");
                victorias1++;
            } else if (opcion == 2) {
                System.out.println("Victoria de jugador 2. Lleva " + victorias2 + " victorias");
                victorias2++;
            }
        }

        if (victorias1 >= 5) {
            return false;
        }
        return true;
    }
}
