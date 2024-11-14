import java.util.LinkedList;
import java.util.Queue;

public class Estacion {
    private int tiempo;
    private Queue<Robot> colaRobots;
    private Semaforo manejadorDeCola;
    private int tiempo2;
    private Queue<Robot> colaRobots2;
    private Semaforo manejadorDeCola2;
    private String nombre;
    private boolean isCasoEspecial;
    private Semaforo semaforoEstacion;
    private Semaforo semaforoEstacion2;

    public Estacion(int tiempo, Robot[] arrRobots, String nombre) {
        manejadorDeCola = new Semaforo(1);
        this.tiempo = tiempo;
        this.nombre = nombre;
        this.isCasoEspecial = false;
        colaRobots = new LinkedList<Robot>();
        for (Robot r : arrRobots)
            colaRobots.add(r);
        semaforoEstacion = new Semaforo(arrRobots.length);

    }

    public Estacion(int tiempo, int tiempo2, Robot[] arrRobots, Robot[] arrRobots2, String nombre) {
        manejadorDeCola = new Semaforo(1);
        manejadorDeCola2 = new Semaforo(1);
        this.tiempo = tiempo;
        this.tiempo2 = tiempo2;
        this.nombre = nombre;
        this.isCasoEspecial = true;
        colaRobots = new LinkedList<Robot>();
        for (Robot r : arrRobots)
            colaRobots.add(r);
        colaRobots2 = new LinkedList<Robot>();
        for (Robot r : arrRobots2)
            colaRobots2.add(r);
        semaforoEstacion = new Semaforo(arrRobots.length);
        semaforoEstacion2 = new Semaforo(arrRobots2.length);
    }

    public int getTiempo() {
        return tiempo;
    }

    public Queue<Robot> getColaRobots() {
        return colaRobots;
    }

    public Semaforo getManejadorDeCola() {
        return manejadorDeCola;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isCasoEspecial() {
        return isCasoEspecial;
    }

    public int getTiempo2() {
        return tiempo2;
    }

    public Queue<Robot> getColaRobots2() {
        return colaRobots2;
    }

    public Semaforo getManejadorDeCola2() {
        return manejadorDeCola2;
    }

    public Semaforo getSemaforoEstacion() {
        return semaforoEstacion;
    }

    public Semaforo getSemaforoEstacion2() {
        return semaforoEstacion2;
    }
}
