public class Controlador {
    private Vista vista;
    private Linea[] lineasDeProd;

    public Controlador(Vista v, Linea[] lineas) {
        vista = v;
        lineasDeProd = lineas;
    }

    public void actualizarVista(int linea, int estacion, int numRobot, int numAuto) {
        vista.actualizarVista(linea, estacion, numRobot, numAuto);
    }

    public void inicializarVista() {
        String[] nombresEstaciones = new String[lineasDeProd[0].getEstaciones().length];
        int[] numsDeLinea = new int[lineasDeProd.length];
        for (int i = 0; i < nombresEstaciones.length; i++)
            nombresEstaciones[i] = lineasDeProd[0].getEstaciones()[i].getNombre();

        for (int i = 0; i < numsDeLinea.length; i++)
            numsDeLinea[i] = lineasDeProd[i].getNumDeLinea();

        vista.inicializarVista(numsDeLinea, nombresEstaciones);
    }
}
