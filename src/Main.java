import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new ventanaMain();
                frame.setVisible(true);
            }
        });
    }

    public static class hilo implements Runnable {
        public static int lugar = 1;
        public static final Object lock = new Object(); // Para sincronización
        Thread t;
        String nombre;
        JLabel personaje;
        JLabel posicionFInal;

        public hilo(String nombre, JLabel personaje, JLabel posicionFInal) {
            this.nombre = nombre;
            this.personaje = personaje;
            this.posicionFInal = posicionFInal;
            t = new Thread(this, nombre);
            t.start();
        }

        @Override
        public void run() {
            int retardo;
            try {
                retardo = (int) (Math.random() * 15) + 1;
                posicionFInal.setVisible(false);
                personaje.setVisible(true);
                for (int i = 50; i < 500; i++) {
                    personaje.setLocation(i, personaje.getY());
                    Thread.sleep(retardo);
                }
                personaje.setVisible(false);
                synchronized (lock) {
                    posicionFInal.setVisible(true);
                    posicionFInal.setText(nombre + " ha llegado en la posición: " + lugar);
                    lugar++;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class ventanaMain extends JFrame {
        public ventanaMain() {
            setTitle("Carrera Épica");
            JLabel personaje1, personaje2, personaje3, personaje1_pos, personaje2_pos, personaje3_pos;
            setSize(680, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel panel = new JPanel();
            panel.setLayout(null);

            Image pers1 = new ImageIcon("src/images/hornet.gif").getImage();
            ImageIcon iconPers1 = new ImageIcon(pers1.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            personaje1 = new JLabel();
            personaje1.setIcon(iconPers1);
            personaje1.setBounds(50, 50, 50, 50);

            Image pers2 = new ImageIcon("src/images/Cloth.gif").getImage();
            ImageIcon iconPers2 = new ImageIcon(pers2.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            personaje2 = new JLabel();
            personaje2.setIcon(iconPers2);
            personaje2.setBounds(50, 100, 50, 50);

            Image pers3 = new ImageIcon("src/images/hollow knigth.gif").getImage();
            ImageIcon iconPers3 = new ImageIcon(pers3.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            personaje3 = new JLabel();
            personaje3.setIcon(iconPers3);
            personaje3.setBounds(50, 150, 50, 50);

            personaje1_pos = new JLabel();
            personaje1_pos.setBounds(400, 50, 350, 50);

            personaje2_pos = new JLabel();
            personaje2_pos.setBounds(400, 100, 350, 50);

            personaje3_pos = new JLabel();
            personaje3_pos.setBounds(400, 150, 350, 50);

            JButton iniciar = new JButton();
            iniciar.setText("Iniciar");
            iniciar.setBounds(250, 250, 150, 50);
            iniciar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    hilo.lugar = 1; // Reiniciar lugar antes de cada carrera
                    new hilo("Hornet", personaje1, personaje1_pos);
                    new hilo("Cloth", personaje2, personaje2_pos);
                    new hilo("Hollow Knight", personaje3, personaje3_pos);
                }
            });

            panel.add(personaje1);
            panel.add(personaje2);
            panel.add(personaje3);
            panel.add(personaje1_pos);
            panel.add(personaje2_pos);
            panel.add(personaje3_pos);
            panel.add(iniciar);
            add(panel);
            setVisible(true);
        }
    }
}
