import java.util.LinkedList;
import java.util.Queue;

public class Estacion extends Thread {
    private int tiempo;
    private Queue<Robot> colaRobots;
    private Semaforo manejadorDeCola;
    private String nombre;
    private Semaforo semaforoEstacion;
    private int numDeLinea;
    private int pos;
    private boolean valido;
    private boolean fin;
    private int numCarro;
    private Estacion[] estacionesLinea;
    private Semaforo semPropio;
    private static Semaforo sFin;
    private static final int MAX_CARROS = 1000;

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
        fin = false;
        valido = true;
        if (sFin == null)
            sFin = new Semaforo(1);

    }

    public void run() {
        while (numCarro < MAX_CARROS) {
            semaforoEstacion.Espera();
            // System.out.println("Consola: 41. " + "Linea: " + numDeLinea + ", Pos: " + pos
            // + " ha accedido al semaforo estaciones");
            semPropio.Espera();
            // System.out.println(
            // "Consola: 44. " + "Linea: " + numDeLinea + ", Pos: " + pos + " ha accedido al
            // semaforo propio");

            if (pos == 0) {
                if (valido) {
                    numCarro = CarroAuxiliar.obtenerNumCarro();
                    System.out
                            .println("Consola: 50. " + "Linea: " + numDeLinea + ", Pos: " + pos + " tiene el carro "
                                    + numCarro);

                    trabajoAuxiliar(false);
                    boolean b = false;
                    while (!b) {
                        System.out.println("Consola: 57. " + "Linea: " + numDeLinea + ", Pos: " + pos
                                + "  ha entrado al ciclo para modificar el numCarro siguiente");

                        estacionesLinea[pos + 1].getSemaforoEstacion().Espera();
                        System.out.println("Consola: 61. " + "Linea: " + numDeLinea + ", Pos: " + pos
                                + "  ha accedido al semaforo estaciones del siguiente. ergo: linea " + numDeLinea
                                + " ,pos " + (pos + 1));

                        estacionesLinea[pos + 1].getSemPropio().Espera();
                        System.out.println("Consola: 66. " + "Linea: " + numDeLinea + ", Pos: " + pos
                                + "  ha accedido al semaforo propio del siguiente. ergo: linea " + numDeLinea
                                + " ,pos " + (pos + 1));

                        if (b = estacionesLinea[pos + 1].isValido()) {
                            System.out.println("Consola: 71. " + "Linea: " + numDeLinea + ", Pos: " + pos
                                    + "  condicion cumplida. ergo: linea " + numDeLinea
                                    + " ,pos " + (pos + 1) + " isValido = true");
                            estacionesLinea[pos + 1].setNumCarro(numCarro);
                            System.out.println("Consola: 75. " + "Linea: " + numDeLinea + ", Pos: " + pos
                                    + "  ha modificado al numCarro del siguiente. ergo: linea " + numDeLinea
                                    + " ,pos " + (pos + 1) + ". El nuevo valor de carro (de " + numDeLinea + ":" + pos
                                    + 1
                                    + ") es " + numCarro);
                        }
                        estacionesLinea[pos + 1].getSemPropio().Libera();
                        System.out.println("Consola: 81. " + "Linea: " + numDeLinea + ", Pos: " + pos
                                + "  ha liberado al semaforo propio del siguiente. ergo: linea " + numDeLinea
                                + " ,pos " + (pos + 1));
                        estacionesLinea[pos + 1].getSemaforoEstacion().Libera();
                        System.out.println("Consola: 85. " + "Linea: " + numDeLinea + ", Pos: " + pos
                                + "  ha liberado al semaforo estaciones del siguiente. ergo: linea " + numDeLinea
                                + " ,pos " + (pos + 1));
                    }
                    System.out.println("Consola: 89. " + "Linea: " + numDeLinea + ", Pos: " + pos
                            + "  ha salido del ciclo para modificar el numCarro siguiente");
                }
            } else if (pos != estacionesLinea.length - 1) {
                if (valido && numCarro != -1) {
                    System.out
                            .println("Consola: 95. " + "Linea: " + numDeLinea + ", Pos: " + pos + " tiene el carro "
                                    + numCarro);
                    trabajoAuxiliar(false);
                    estacionesLinea[pos + 1].getSemaforoEstacion().Espera();
                    System.out.println("Consola: 99. " + "Linea: " + numDeLinea + ", Pos: " + pos
                            + " ha accedido al semaforo estaciones del siguiente. ergo: linea " +
                            numDeLinea
                            + " ,pos " + (pos + 1));
                    estacionesLinea[pos + 1].getSemPropio().Espera();
                    System.out.println("Consola: 103. " + "Linea: " + numDeLinea + ", Pos: " +
                            pos
                            + " ha accedido al semaforo propio del siguiente. ergo: linea " + numDeLinea
                            + " ,pos " + (pos + 1));
                    boolean b = false;
                    while (!b) {
                        System.out.println("Consola: 108. " + "Linea: " + numDeLinea + ", Pos: " +
                                pos
                                + " ha entrado al ciclo para modificar el numCarro siguiente");
                        if (b = estacionesLinea[pos + 1].isValido()) {
                            System.out.println("Consola: 111. " + "Linea: " + numDeLinea + ", Pos: " +
                                    pos
                                    + " condicion cumplida. ergo: linea " + numDeLinea
                                    + " ,pos " + (pos + 1) + " isValido = true");
                            estacionesLinea[pos + 1].setNumCarro(numCarro);
                            System.out.println("Consola: 115. " + "Linea: " + numDeLinea + ", Pos: " +
                                    pos
                                    + " ha modificado al numCarro del siguiente. ergo: linea " + numDeLinea
                                    + " ,pos " + (pos + 1) + ". El nuevo valor de carro (de " + numDeLinea + ":"
                                    +
                                    (pos + 1)
                                    + ") es " + numCarro);
                        }
                    }
                    numCarro = -1;
                    System.out.println("Consola: 122. " + "Linea: " + numDeLinea + ", Pos: " +
                            pos
                            + " se ha modificado al numCarro actual. nuevoValor = -1 ");
                    estacionesLinea[pos + 1].getSemPropio().Libera();
                    System.out.println("Consola: 125. " + "Linea: " + numDeLinea + ", Pos: " +
                            pos
                            + " ha liberado al semaforo propio del siguiente. ergo: linea " + numDeLinea
                            + " ,pos " + (pos + 1));
                    estacionesLinea[pos + 1].getSemaforoEstacion().Libera();
                    System.out.println("Consola: 129. " + "Linea: " + numDeLinea + ", Pos: " +
                            pos
                            + " ha liberado al semaforo estaciones del siguiente. ergo: linea " +
                            numDeLinea
                            + " ,pos " + (pos + 1));
                }
            } else {
                if (valido && numCarro != -1) {
                    trabajoAuxiliar(true);
                    numCarro = -1;
                }
            }
            semPropio.Libera();
            // System.out.println(
            // "Consola: 140. " + "Linea: " + numDeLinea + ", Pos: " + pos
            // + " ha liberado su semaforo propio");
            semaforoEstacion.Libera();
            // System.out.println(
            // "Consola:145. " + "Linea: " + numDeLinea + ", Pos: " + pos
            // + " ha liberado al semaforo estacion");
        }
    }

    private void trabajoAuxiliar(boolean esUltimo) {
        valido = false;
        System.out.println("Linea: " + numDeLinea + ", Pos: " + pos + "  ha modificado valido a falso");

        manejadorDeCola.Espera();
        System.out.println("Linea: " + numDeLinea + ", Pos: " + pos + "  ha accedido al semaforo manejadorCola");

        Robot auxRbt = colaRobots.poll();
        System.out.println("Linea: " + numDeLinea + ", Pos: " + pos + "  ha sacado al robot " + auxRbt.getNumDeSerie());

        manejadorDeCola.Libera();
        System.out.println("Linea: " + numDeLinea + ", Pos: " + pos + "  ha liberado al semaforo manejadorCola");

        dormir(tiempo);
        System.out.println(
                nombre + ", linea: " + numDeLinea + ", pos: " + pos + ", Robot: " + auxRbt.getNumDeSerie()
                        + " ,numCarro " + numCarro);
        manejadorDeCola.Espera();
        System.out.println("Linea: " + numDeLinea + ", Pos: " + pos + "  ha accedido al semaforo manejadorCola");

        colaRobots.add(auxRbt);
        System.out.println("Linea: " + numDeLinea + ", Pos: " + pos + "  ha insertado al robot "
                + auxRbt.getNumDeSerie() + " a la cola");

        manejadorDeCola.Libera();
        System.out.println("Linea: " + numDeLinea + ", Pos: " + pos + "  ha liberado al semaforo manejadorCola");

        valido = true; // Tal vez cambiar orden
        System.out.println("Linea: " + numDeLinea + ", Pos: " + pos + "  ha modificado valido a verdadero");

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