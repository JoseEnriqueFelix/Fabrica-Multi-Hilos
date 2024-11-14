import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {
        Estacion[] estaciones = new Estacion[6];
        Robot[] auxRbts = new Robot[24];

        for (int i = 0; i < auxRbts.length; i++)
            auxRbts[i] = new Robot();

        estaciones[0] = new Estacion(2000, Arrays.copyOfRange(auxRbts, 0, 5), "Chasis y cableado");
        estaciones[1] = new Estacion(600, 400, Arrays.copyOfRange(auxRbts, 5, 9), Arrays.copyOfRange(auxRbts, 9, 11),
                "Motor-Transmision");
        estaciones[2] = new Estacion(1000, Arrays.copyOfRange(auxRbts, 11, 14), "Carroceria");
        estaciones[3] = new Estacion(500, Arrays.copyOfRange(auxRbts, 14, 17), "Interiores");
        estaciones[4] = new Estacion(500, Arrays.copyOfRange(auxRbts, 17, 19), "Llantas");
        estaciones[5] = new Estacion(1000, Arrays.copyOfRange(auxRbts, 19, 24), "Pruebas");
        Linea[] lineasDeProduccion = new Linea[Rutinas.nextInt(8, 15)];

        for (int i = 0; i < lineasDeProduccion.length; i++)
            lineasDeProduccion[i] = new Linea(estaciones);

        Vista v = new Vista(estaciones.length, lineasDeProduccion.length);
        Controlador controlador = new Controlador(v, lineasDeProduccion);

        for (int i = 0; i < lineasDeProduccion.length; i++)
            lineasDeProduccion[i].setControlador(controlador);

        controlador.inicializarVista();

        for (int i = 0; i < lineasDeProduccion.length; i++)
            lineasDeProduccion[i].start();
    }
}
