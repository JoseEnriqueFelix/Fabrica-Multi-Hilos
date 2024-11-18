import java.util.LinkedList;
import java.util.Queue;

public class Estacion extends Thread {
    private static final int MAX_CARROS = 1000;
    private static boolean fin;
    private static Semaforo sFin;

    private int tiempo, tiempo2;
    private Queue<Robot> colaRobots, colaRobots2;
    private Semaforo manejadorDeCola, manejadorDeCola2;
    private String nombre;
    private Semaforo semaforoEstacion, semaforoEstacion2;
    private int numDeLinea;
    private int pos;
    private boolean valido; // valido2?
    private boolean especial;
    private int numCarro;
    private Estacion[] estacionesLinea;
    private Semaforo semPropio, semPropio2;
    private Controlador controlador;

    public Estacion(int tiempo, Queue<Robot> colaRobots, String nombre, Semaforo manejadorDeCola,
            Semaforo semaforoEstacion, int linea, int pos) {
        numCarro = -1;
        this.tiempo = tiempo;
        this.nombre = nombre;
        this.colaRobots = colaRobots;
        this.manejadorDeCola = manejadorDeCola;
        this.semaforoEstacion = semaforoEstacion;
        this.numDeLinea = linea;
        this.pos = pos;
        semPropio = new Semaforo(1);
        valido = true;
        if (sFin == null) {
            sFin = new Semaforo(1);
            fin = false;
        }
        especial = false;
    }

    public Estacion(int tiempo, int tiempo2, Queue<Robot> colaRobots, Queue<Robot> colaRobots2, String nombre,
            Semaforo manejadorDeCola, Semaforo manejadorDeCola2, Semaforo semaforoEstacion,
            Semaforo semaforoEstacion2, int linea, int pos) {
        numCarro = -1;
        this.tiempo = tiempo;
        this.tiempo2 = tiempo2;
        this.nombre = nombre;
        this.colaRobots = colaRobots;
        this.colaRobots2 = colaRobots2;
        this.manejadorDeCola = manejadorDeCola;
        this.manejadorDeCola2 = manejadorDeCola2;
        this.semaforoEstacion = semaforoEstacion;
        this.semaforoEstacion2 = semaforoEstacion2;
        this.numDeLinea = linea;
        this.pos = pos;
        semPropio = new Semaforo(1);
        semPropio2 = new Semaforo(1);
        valido = true;
        // valido2 = true;
        if (sFin == null) {
            sFin = new Semaforo(1);
            fin = false;
        }
        especial = true;

    }

    public void setControlador(Controlador c) {
        this.controlador = c;
    }

    public void run() {
        while (true) {
            semaforoEstacion.Espera();
            semPropio.Espera();

            if (pos == 0) {
                if (valido) {
                    numCarro = CarroAuxiliar.obtenerNumCarro();
                    sFin.Espera();
                    if (fin) {
                        semPropio.Libera();
                        semaforoEstacion.Libera();
                        sFin.Libera();
                        continue;
                    }

                    if (numCarro >= MAX_CARROS)
                        fin = true;
                    sFin.Libera();

                    if (especial)
                        trabajoAuxiliarEspecial();
                    else
                        trabajoAuxiliar();

                    boolean b = false;
                    while (!b) {
                        estacionesLinea[pos + 1].getSemaforoEstacion().Espera();
                        estacionesLinea[pos + 1].getSemPropio().Espera();

                        if (b = estacionesLinea[pos + 1].isValido())
                            estacionesLinea[pos + 1].setNumCarro(numCarro);

                        estacionesLinea[pos + 1].getSemPropio().Libera();
                        estacionesLinea[pos + 1].getSemaforoEstacion().Libera();
                    }
                }
            } else if (pos != estacionesLinea.length - 1) {
                if (valido && numCarro != -1) {
                    if (especial)
                        trabajoAuxiliarEspecial();
                    else
                        trabajoAuxiliar();
                    boolean b = false;
                    while (!b) {
                        estacionesLinea[pos + 1].getSemaforoEstacion().Espera();
                        estacionesLinea[pos + 1].getSemPropio().Espera();
                        if (b = estacionesLinea[pos + 1].isValido())
                            estacionesLinea[pos + 1].setNumCarro(numCarro);
                        estacionesLinea[pos + 1].getSemPropio().Libera();
                        estacionesLinea[pos + 1].getSemaforoEstacion().Libera();
                    }
                    numCarro = -1;
                }
            } else {
                if (valido && numCarro != -1) {
                    if (especial)
                        trabajoAuxiliarEspecial();
                    else
                        trabajoAuxiliar();
                    controlador.vaciar(numDeLinea, pos);
                    numCarro = -1;
                    sFin.Espera();
                    if (fin) {
                        semPropio.Libera();
                        semaforoEstacion.Libera();
                        sFin.Libera();
                        continue;
                    }
                    sFin.Libera();
                }
            }
            semPropio.Libera();
            semaforoEstacion.Libera();
        }
    }

    private void trabajoAuxiliar() {
        valido = false;
        manejadorDeCola.Espera();
        Robot auxRbt = colaRobots.poll();
        manejadorDeCola.Libera();
        controlador.actualizarVista(numDeLinea, pos, auxRbt.getNumDeSerie(), numCarro);
        dormir(tiempo);
        System.out.println(
                nombre + ", linea: " + numDeLinea + ", pos: " + pos + ", Robot: " + auxRbt.getNumDeSerie()
                        + " ,numCarro " + numCarro);
        manejadorDeCola.Espera();
        colaRobots.add(auxRbt);
        manejadorDeCola.Libera();
        valido = true;
    }

    private void trabajoAuxiliarEspecial() {
        valido = false;
        manejadorDeCola.Espera();
        Robot auxRbt = colaRobots.poll();
        manejadorDeCola.Libera();
        controlador.actualizarVista(numDeLinea, pos, auxRbt.getNumDeSerie(), numCarro);
        dormir(tiempo);
        System.out.println(
                nombre + ", linea: " + numDeLinea + ", pos: " + pos + ", Robot: " + auxRbt.getNumDeSerie()
                        + " ,numCarro " + numCarro);
        semaforoEstacion2.Espera();
        semPropio2.Espera();

        manejadorDeCola2.Espera();
        Robot auxRbt2 = colaRobots2.poll();
        manejadorDeCola.Espera();
        colaRobots.add(auxRbt);
        manejadorDeCola.Libera();
        manejadorDeCola2.Libera();
        controlador.actualizarVista(numDeLinea, pos, auxRbt2.getNumDeSerie(), numCarro);
        dormir(tiempo2);
        System.out.println(
                nombre + ", linea: " + numDeLinea + ", pos: " + pos + ", Robot: " + auxRbt2.getNumDeSerie()
                        + " ,numCarro " + numCarro);
        manejadorDeCola2.Espera();
        colaRobots2.add(auxRbt2);
        manejadorDeCola2.Libera();
        semPropio2.Libera();
        semaforoEstacion2.Libera();

        valido = true;
    }

    private void dormir(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
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

    public Semaforo getSemaforoEstacion() {
        return semaforoEstacion;
    }

    public int getNumDeLinea() {
        return numDeLinea;
    }

    public int getNumCarro() {
        return numCarro;
    }

    public void setNumCarro(int numCarro) {
        this.numCarro = numCarro;
    }

    public Estacion[] getEstacionesLinea() {
        return estacionesLinea;
    }

    public void setEstacionesLinea(Estacion[] estacionesLinea) {
        this.estacionesLinea = estacionesLinea;
    }

    public Semaforo getSemPropio() {
        return semPropio;
    }
}