import java.io.IOException;
import java.net.ServerSocket;

public class Hilos implements Runnable {

    private ServerSocket serverSocket;
    private byte opcion;

    public Hilos(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        byte numClientes = 0;
        ServidorPPT server = new ServidorPPT();
        try {
            opcion = server.solicitarInformacion();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.addOpciones(opcion);
    }
}
