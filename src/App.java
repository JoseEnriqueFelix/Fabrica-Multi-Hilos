/*
 * Nombre : Jose Enrique Felix Esparragoza
 * NoControl : 21170315
 * Materia : Topicos avanzados de programacion
 * Unidad : 3
 * Proyecto :  LINEA DE PRODUCCION VERSION 2
 * Fecha : 17, nov, 2024
 * Maestro : Clemente Garcia Gerardo
 */

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
            Queue<Robot> auxCola = new LinkedList<>();
            for (int k = RANGOS_ROBOTS[i * 2]; k < RANGOS_ROBOTS[i * 2 + 1]; k++) {
                auxCola.add(auxRbts[k]);
            }
            String nombre = NOMBRES_ESTACIONES[i];
            Semaforo manejaCola = new Semaforo(1);
            Semaforo semEstacion = new Semaforo(auxCola.size());

            // Variables específicas para la estación 1
            int tiempo2 = 0;
            Queue<Robot> auxCola2 = null;
            Semaforo manejaCola2 = null;
            Semaforo semEstacion2 = null;

            if (i == 1) {
                tiempo2 = 400;
                auxCola2 = new LinkedList<>();
                auxCola2.add(auxRbts[9]);
                auxCola2.add(auxRbts[10]);
                manejaCola2 = new Semaforo(1);
                semEstacion2 = new Semaforo(auxCola2.size());
            }

            for (int j = 0; j < numLineas; j++) {
                if (i == 1) {
                    estaciones[j][i] = new Estacion(
                            tiempo, tiempo2, auxCola, auxCola2, nombre, manejaCola, manejaCola2, semEstacion,
                            semEstacion2, j, i);
                } else {
                    estaciones[j][i] = new Estacion(
                            tiempo, auxCola, nombre, manejaCola, semEstacion, j, i);
                }
            }
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
    }
}
