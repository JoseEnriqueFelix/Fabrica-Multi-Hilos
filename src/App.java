import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class App {

    private final static String[] NOMBRES_ESTACIONES = { "Chasis y cableado", "Motor-transmision", "Carroceria",
            "Interiores", "Llantas", "Pruebas" };
    private final static int NUM_ESTACIONES = 6;
    private final static int[] RANGOS_ROBOTS = { 0, 5, 5, 9, 11, 14, 14, 17, 17, 19, 19, 24 };
    private final static int[] tiempos = { 2000, 600, 1000, 500, 500, 1000 };
    private static final int LIM_INF = 8;
    private static final int LIM_SUP = 15;

    public static void main(String[] args) throws Exception {
        int numLineas = Rutinas.nextInt(LIM_INF, LIM_SUP);
        Robot[] auxRbts = new Robot[24];
        for (int i = 0; i < auxRbts.length; i++)
            auxRbts[i] = new Robot();
        Estacion[][] estaciones = new Estacion[numLineas][NUM_ESTACIONES];

        for (int i = 0; i < NUM_ESTACIONES; i++) {
            int tiempo = tiempos[i];
            Queue<Robot> auxCola = new LinkedList<Robot>();
            for (int k = RANGOS_ROBOTS[(i * 2)]; k < RANGOS_ROBOTS[(i * 2) + 1]; k++)
                auxCola.add(auxRbts[k]);
            String nombre = NOMBRES_ESTACIONES[i];
            Semaforo manejaCola = new Semaforo(1);
            Semaforo semEstacion = new Semaforo(RANGOS_ROBOTS[(i * 2) + 1] - RANGOS_ROBOTS[i * 2]);
            for (int j = 0; j < numLineas; j++) {
                estaciones[j][i] = new Estacion(tiempo, auxCola, nombre, manejaCola, semEstacion, j, i);
            }
        }

        Linea[] lineasDeProd = new Linea[numLineas];
        for (int i = 0; i < numLineas; i++) {
            ArrayList<Estacion> auxEstacionesLinea = new ArrayList<>();
            for (int j = 0; j < NUM_ESTACIONES; j++)
                auxEstacionesLinea.add(estaciones[i][j]);
            lineasDeProd[i] = new Linea(auxEstacionesLinea);
        }

        Vista v = new Vista(estaciones[0].length, estaciones.length);
        Controlador controlador = new Controlador(v, estaciones);
        controlador.inicializarVista();

        for (int i = 0; i < numLineas; i++) {
            Estacion[] auxEstaciones = new Estacion[estaciones[i].length];
            for (int j = 0; j < estaciones[i].length; j++)
                auxEstaciones[j] = estaciones[i][j];
            for (int j = 0; j < estaciones[i].length; j++) {
                estaciones[i][j].setEstacionesLinea(auxEstaciones);
                estaciones[i][j].setControlador(controlador);
            }
        }

        for (int i = 0; i < estaciones.length; i++)
            for (int j = 0; j < estaciones[i].length; j++)
                estaciones[i][j].start();

        // for (int i = 0; i < lineasDeProd.length; i++)
        // lineasDeProd[i].iniciar();
        // Linea[] lineasDeProduccion = new Linea[Rutinas.nextInt(8, 15)];

        // for (int i = 0; i < lineasDeProduccion.length; i++)
        // lineasDeProduccion[i] = new Linea(estaciones);

        // for (int i = 0; i < lineasDeProduccion.length; i++)
        // lineasDeProduccion[i].setControlador(controlador);

        // controlador.inicializarVista();

        // for (int i = 0; i < lineasDeProduccion.length; i++)
        // lineasDeProduccion[i].start();
    }
}
