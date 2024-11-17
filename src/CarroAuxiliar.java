public class CarroAuxiliar {
    static private Semaforo s = new Semaforo(1);
    static private int numCarro = 0;

    static public int obtenerNumCarro() {
        s.Espera();
        numCarro++;
        s.Libera();
        return numCarro;
    }

    static public int obtenerSinAumento() {
        return numCarro;
    }
}
