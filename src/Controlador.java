public class Controlador {
    private Vista vista;
    private Estacion[][] estaciones;

    public Controlador(Vista v, Estacion[][] estaciones) {
        vista = v;
        this.estaciones = estaciones;
    }

    public void actualizarVista(int linea, int estacion, int numRobot, int numAuto) {
        vista.actualizarVista(linea, estacion, numRobot, numAuto);
    }

    public void inicializarVista() {
        String[] nombresEstaciones = new String[estaciones[0].length];
        System.out.println(nombresEstaciones.length);
        int[] numsDeLinea = new int[estaciones.length];
        System.out.println(numsDeLinea.length);
        for (int i = 0; i < nombresEstaciones.length; i++)
            nombresEstaciones[i] = estaciones[0][i].getNombre();

        for (int i = 0; i < numsDeLinea.length; i++)
            numsDeLinea[i] = i;

        vista.inicializarVista(numsDeLinea, nombresEstaciones);
    }

    public void vaciar(int linea, int estacion) {
        vista.vaciar(linea, estacion);
    }

}
