package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class ManejoPeliculas implements Runnable{

    private Thread hilo;
    private static int numCliente = 0;
    private Socket socketAlCliente;
    private static ArrayList<Pelicula> peliculas = new ArrayList<>();
    BufferedReader entrada;
    PrintStream salida;
    boolean continuar;

    public ManejoPeliculas(Socket socketAlCliente) throws IOException {
        numCliente++;
        hilo = new Thread(this, "Cliente_"  + numCliente);
        this.socketAlCliente = socketAlCliente;
        hilo.start();
        entrada = new BufferedReader(new InputStreamReader(socketAlCliente.getInputStream()));
        salida = new PrintStream(socketAlCliente.getOutputStream());
        this.continuar = false;
    }

    @Override
    public void run() {
        while(!continuar){
            try {
                String opcion = entrada.readLine();
                switch (opcion){
                    case "1":
                       salida.println(getPeliculaById(Integer.parseInt(entrada.readLine())).toString());
                        break;
                    case "2":
                        salida.println(getPeliculaByTitle(entrada.readLine()).toString());
                        break;
                    case "3":
                        salida.println(getPeliculaByDirector(entrada.readLine()).toString());
                        break;
                    case "4":
                        agregarPelicula();
                        salida.println("Pais insertado correctamente");
                        break;
                    case "5":
                        socketAlCliente.close();
                        continuar = true;
                        break;

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getPeliculaById(int id) {
        for (Pelicula p : peliculas) {
            if (p.getId() == id) {
                return p.toString();
            }
        }
        return "No se ha encontrado ninguna pelicula con ese id";
    }

    public String getPeliculaByTitle(String titulo) {
        for (Pelicula p : peliculas) {
            if (p.getTitulo().equalsIgnoreCase(titulo)) {
                return p.toString();
            }
        }
        return "No se ha encontrado ninguna pelicula con ese titulo";
    }

    public String getPeliculaByDirector(String director) {
        ArrayList<Pelicula> listaPeliculas = new ArrayList<>();
        for (Pelicula p : peliculas) {
            if (p.getDirector().equalsIgnoreCase(director)) {
                listaPeliculas.add(p);
            }
        }
        if (listaPeliculas.isEmpty()){
            return "No se ha encontrado ninguna pelicula con ese director";
        } else {
            return listaPeliculas.toString();
        }
    }

    public void agregarPelicula() throws IOException {
        int id;
        if (peliculas.isEmpty()){
            id = 1;
        } else {
            id = peliculas.getLast().getId();
            id++;
        }

        String[] datos = entrada.readLine().split("-");
        Pelicula p = new Pelicula(id, datos[0], datos[1], Double.parseDouble(datos[2]));
        peliculas.add(p);
    }
}
