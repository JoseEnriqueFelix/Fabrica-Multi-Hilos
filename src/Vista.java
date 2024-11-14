import java.awt.*;

import javax.swing.*;

public class Vista extends JFrame {
    private PanelInformacion[][] elementos;
    private JLabel[] lblEstaciones, lblInfoLineaDeProd;
    private int estacionesLength;

    public Vista(int numEstaciones, int numLineasDeProd) {
        super("Fabrica de autos version 1");
        estacionesLength = numEstaciones;
        lblEstaciones = new JLabel[numEstaciones];
        lblInfoLineaDeProd = new JLabel[numLineasDeProd];
        hazInterfaz(numEstaciones, numLineasDeProd);
    }

    private void hazInterfaz(int numEstaciones, int numLineasDeProd) {
        setSize(1000, 1000);
        this.setLocationRelativeTo(null);
        setLayout(new GridLayout(0, numEstaciones + 1));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(new JLabel());
        for (int i = 0; i < numEstaciones; i++) {
            lblEstaciones[i] = new JLabel();
            add(lblEstaciones[i]);
        }
        elementos = new PanelInformacion[numLineasDeProd][numEstaciones];
        for (int i = 0; i < numLineasDeProd; i++) {
            lblInfoLineaDeProd[i] = new JLabel();
            add(lblInfoLineaDeProd[i]);
            for (int j = 0; j < numEstaciones; j++) {
                elementos[i][j] = new PanelInformacion();
                add(elementos[i][j]);
            }
        }
        setVisible(true);
    }

    public void actualizarVista(int linea, int estacion, int numRobot, int numAuto) {
        int borrado;
        borrado = (estacion == 0) ? estacionesLength - 1 : estacion - 1;
        elementos[linea][borrado].vaciar();
        elementos[linea][estacion].actualizarDatos(numRobot, numAuto, linea);
        repaint();
        revalidate();
    }

    public void inicializarVista(int[] numsDeLinea, String[] estacionesNombres) {
        for (int i = 0; i < estacionesNombres.length; i++)
            lblEstaciones[i].setText(estacionesNombres[i]);

        for (int i = 0; i < numsDeLinea.length; i++)
            lblInfoLineaDeProd[i].setText("Linea " + numsDeLinea[i] + "");
    }

}
