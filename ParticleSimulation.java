import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ParticleSimulation extends JFrame {
    private int boxLength = 500; // Default box length
    private int numParticles = 10; // Default number of particles
    private ParticlePanel particlePanel;

    public ParticleSimulation() {
        setTitle("Particle Simulation");
        setSize(boxLength + 16, boxLength + 38); // Adjust for window borders
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input for number of particles
        JTextField particleInput = new JTextField("10", 5);
        JButton startButton = new JButton("Start");
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Number of Particles:"));
        controlPanel.add(particleInput);
        controlPanel.add(startButton);
        add(controlPanel, BorderLayout.NORTH);

        // Particle panel
        particlePanel = new ParticlePanel();
        add(particlePanel, BorderLayout.CENTER);

        // Start button action
        startButton.addActionListener(e -> {
            try {
                numParticles = Integer.parseInt(particleInput.getText());
                particlePanel.initializeParticles();
                particlePanel.startSimulation();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            }
        });
    }

    // Panel where particles are drawn
    private class ParticlePanel extends JPanel {
        private ArrayList<Particle> particles;
        private Timer timer;

        public ParticlePanel() {
            particles = new ArrayList<>();
            timer = new Timer(100, e -> repaint());
        }

        public void initializeParticles() {
            particles.clear();
            Random rand = new Random();
            for (int i = 0; i < numParticles; i++) {
                int x = rand.nextInt(boxLength - 20) + 10;
                int y = rand.nextInt(boxLength - 20) + 10;
                particles.add(new Particle(x, y));
            }
        }

        public void startSimulation() {
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Particle particle : particles) {
                particle.move();
                g.fillOval(particle.x, particle.y, 10, 10);
            }
        }
    }

    // Particle class
    private class Particle {
        int x, y;
        int xVel, yVel;

        public Particle(int x, int y) {
            this.x = x;
            this.y = y;
            Random rand = new Random();
            xVel = rand.nextInt(10) - 5;
            yVel = rand.nextInt(10) - 5;
        }

        public void move() {
            x += xVel;
            y += yVel;

            if (x < 0 || x > boxLength - 10) xVel = -xVel;
            if (y < 0 || y > boxLength - 10) yVel = -yVel;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ParticleSimulation().setVisible(true);
        });
    }
}
