public class Linea extends Thread {
    private static final int MAX_CARROS = 1000;
    private static int contador = 0;
    private Estacion[] estaciones;
    private int numDeLinea;
    private Controlador controlador;

    public Linea(Estacion[] estaciones) {
        this.estaciones = estaciones;
        this.numDeLinea = contador++;
    }

    public void run() {
        int auxNumCarro = CarroAuxiliar.obtenerNumCarro();
        while (auxNumCarro <= MAX_CARROS) {
            for (int i = 0; i < estaciones.length; i++) {
                if (estaciones[i].isCasoEspecial())
                    trabajaCasoEspecial(i, auxNumCarro);
                else
                    trabajaNormal(i, auxNumCarro);
            }
            auxNumCarro = CarroAuxiliar.obtenerNumCarro();
        }
    }

    private void trabajaCasoEspecial(int i, int auxNumCarro) {
        estaciones[i].getSemaforoEstacion().Espera();
        estaciones[i].getManejadorDeCola().Espera();
        Robot auxRbt = estaciones[i].getColaRobots().remove();
        estaciones[i].getManejadorDeCola().Libera();
        controlador.actualizarVista(numDeLinea, i, auxRbt.getNumDeSerie(), auxNumCarro);
        dormir(estaciones[i].getTiempo());
        System.out.println("Linea " + numDeLinea + ", numDeCarro " + auxNumCarro + ", estacion "
                + estaciones[i].getNombre() + ", robot " + auxRbt.getNumDeSerie());
        estaciones[i].getManejadorDeCola().Espera();
        estaciones[i].getColaRobots().add(auxRbt);
        estaciones[i].getManejadorDeCola().Libera();
        estaciones[i].getSemaforoEstacion2().Espera();
        estaciones[i].getSemaforoEstacion().Libera();
        estaciones[i].getManejadorDeCola2().Espera();
        auxRbt = estaciones[i].getColaRobots2().remove();
        estaciones[i].getManejadorDeCola2().Libera();
        controlador.actualizarVista(numDeLinea, i, auxRbt.getNumDeSerie(), auxNumCarro);
        dormir(estaciones[i].getTiempo2());
        System.out.println("Linea " + numDeLinea + ", numDeCarro " + auxNumCarro + ", estacion "
                + estaciones[i].getNombre() + " caso especial" + ", robot " + auxRbt.getNumDeSerie());
        estaciones[i].getManejadorDeCola2().Espera();
        estaciones[i].getColaRobots2().add(auxRbt);
        estaciones[i].getManejadorDeCola2().Libera();
        estaciones[i].getSemaforoEstacion2().Libera();
    }

    private void trabajaNormal(int i, int auxNumCarro) {
        estaciones[i].getSemaforoEstacion().Espera();
        estaciones[i].getManejadorDeCola().Espera();
        Robot auxRbt = estaciones[i].getColaRobots().remove();
        estaciones[i].getManejadorDeCola().Libera();
        controlador.actualizarVista(numDeLinea, i, auxRbt.getNumDeSerie(), auxNumCarro);
        dormir(estaciones[i].getTiempo());
        System.out.println("Linea " + numDeLinea + ", numDeCarro " + auxNumCarro + ", estacion "
                + estaciones[i].getNombre() + ", robot " + auxRbt.getNumDeSerie());
        estaciones[i].getManejadorDeCola().Espera();
        estaciones[i].getColaRobots().add(auxRbt);
        estaciones[i].getManejadorDeCola().Libera();
        estaciones[i].getSemaforoEstacion().Libera();
    }

    private void dormir(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getNumDeLinea() {
        return numDeLinea;
    }

    public void setControlador(Controlador c) {
        controlador = c;
    }

    public Estacion[] getEstaciones() {
        return estaciones;
    }
}
