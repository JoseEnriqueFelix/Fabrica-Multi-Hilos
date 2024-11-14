import java.awt.GridLayout;

import javax.swing.*;

public class PanelInformacion extends JPanel {
    private JLabel imagenAuto, imagenRobot;
    private JLabel lblNumAuto, lblNumRobot;

    public PanelInformacion() {
        setLayout(new GridLayout(0, 2));
        lblNumAuto = new JLabel();
        lblNumRobot = new JLabel();
        imagenAuto = new JLabel();
        imagenRobot = new JLabel();
        this.add(imagenAuto);
        this.add(imagenRobot);
        this.add(lblNumAuto);
        this.add(lblNumRobot);
    }

    public void actualizarDatos(int numRobot, int numAuto, int numDeLinea) {
        imagenAuto.setIcon(
                Rutinas.AjustarImagen("imagenes/carro" + numDeLinea + ".png", this.getWidth() / 4,
                        this.getHeight() / 4));
        imagenRobot.setIcon(Rutinas.AjustarImagen("imagenes/robot.png", this.getWidth() / 4, this.getHeight() / 4));
        lblNumAuto.setText("Auto: " + numAuto);
        lblNumRobot.setText("Robot: " + numRobot);
    }

    public void vaciar() {
        imagenAuto.setIcon(null);
        imagenRobot.setIcon(null);
        lblNumAuto.setText("");
        lblNumRobot.setText("");
    }

}
