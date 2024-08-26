import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PerceptronGUI extends JFrame {
    private JButton[][] gradeA = new JButton[10][10];
    private JButton[][] gradeB = new JButton[10][10];
    private JButton[][] gradeTest = new JButton[10][10];

    private int[][] matrizA = new int[10][10];
    private int[][] matrizB = new int[10][10];
    private int[][] matrizTest = new int[10][10];

    private double[] pesos = new double[100]; // Pesos do Perceptron (10x10 pixels)
    private double bias = 0.0; // Bias do Perceptron
    private final double taxaAprendizagem = 0.1; // Taxa de aprendizagem

    public PerceptronGUI() {
        setTitle("Perceptron - Comparação de Matrizes");
        setLayout(new GridLayout(4, 1));

        JPanel painelGradeA = new JPanel(new GridLayout(10, 10));
        JPanel painelGradeB = new JPanel(new GridLayout(10, 10));
        JPanel painelGradeTest = new JPanel(new GridLayout(10, 10));

        inicializarGrade(painelGradeA, gradeA, matrizA);
        inicializarGrade(painelGradeB, gradeB, matrizB);
        inicializarGrade(painelGradeTest, gradeTest, matrizTest);

        add(new JLabel("Matriz A", SwingConstants.CENTER));
        add(painelGradeA);
        add(new JLabel("Matriz B", SwingConstants.CENTER));
        add(painelGradeB);
        add(new JLabel("Matriz de Teste", SwingConstants.CENTER));
        add(painelGradeTest);

        JPanel painelBotoes = new JPanel();

        JButton botaoTreinar = new JButton("Treinar");
        botaoTreinar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                treinarPerceptron(); // Treinamento do Perceptron
                JOptionPane.showMessageDialog(null, "Matrizes A e B treinadas!");
            }
        });

        JButton botaoIdentificar = new JButton("Identificar");
        botaoIdentificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                identificarLetra(); // Identificar a letra
            }
        });

        painelBotoes.add(botaoTreinar);
        painelBotoes.add(botaoIdentificar);
        add(painelBotoes);

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void inicializarGrade(JPanel painel, JButton[][] grade, int[][] matriz) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grade[i][j] = new JButton();
                grade[i][j].setBackground(Color.WHITE);
                final int x = i;
                final int y = j;
                grade[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        alternarPixel(grade, matriz, x, y);
                    }
                });
                painel.add(grade[i][j]);
            }
        }
    }

    private void alternarPixel(JButton[][] grade, int[][] matriz, int x, int y) {
        if (matriz[x][y] == 0) {
            matriz[x][y] = 1;
            grade[x][y].setBackground(Color.BLACK);
        } else {
            matriz[x][y] = 0;
            grade[x][y].setBackground(Color.WHITE);
        }
    }

    // Método de treinamento do Perceptron
    private void treinarPerceptron() {
        // Esperado: 1 para A, -1 para B
        int[][][] entradas = {matrizA, matrizB};
        int[] saidas = {1, -1};

        // Inicializando pesos e bias
        for (int i = 0; i < 100; i++) {
            pesos[i] = Math.random() - 0.5;
        }
        bias = Math.random() - 0.5;

        // Treinamento
        for (int epoca = 0; epoca < 1000; epoca++) { // Número de épocas
            for (int t = 0; t < 2; t++) { // Para cada entrada (A e B)
                int[] entrada = new int[100];
                int index = 0;
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        entrada[index++] = entradas[t][i][j];
                    }
                }
                int y = calcularSaida(entrada);
                int erro = saidas[t] - y;

                // Ajuste dos pesos e bias
                for (int i = 0; i < 100; i++) {
                    pesos[i] += taxaAprendizagem * erro * entrada[i];
                }
                bias += taxaAprendizagem * erro;
            }
        }
    }

    // Método para calcular a saída do Perceptron
    private int calcularSaida(int[] entrada) {
        double soma = bias;
        for (int i = 0; i < 100; i++) {
            soma += pesos[i] * entrada[i];
        }
        return soma >= 0 ? 1 : -1; // Função de ativação (degrau)
    }

    // Método para identificar a letra com base na matriz de teste
    private void identificarLetra() {
        int[] entradaTeste = new int[100];
        int index = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                entradaTeste[index++] = matrizTest[i][j];
            }
        }
        int saida = calcularSaida(entradaTeste);
        String letraReconhecida = (saida == 1) ? "A" : "B";
        JOptionPane.showMessageDialog(this, "A matriz de teste se parece mais com a letra: " + letraReconhecida);
    }

    public static void main(String[] args) {
        new PerceptronGUI();
    }
}
