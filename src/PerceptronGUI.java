import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe PerceptronGUI que representa uma interface gráfica para treinar e testar um Perceptron
 * com duas matrizes de referência e uma matriz de teste.
 */
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

    /**
     * Construtor da classe PerceptronGUI. Configura a interface gráfica e inicializa as matrizes.
     */
    public PerceptronGUI() {
        setTitle("Perceptron - Comparação de Matrizes");

        // Criação de um painel principal com um layout diferente para permitir espaçamento
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        JPanel painelGradeA = new JPanel(new GridLayout(10, 10));
        JPanel painelGradeB = new JPanel(new GridLayout(10, 10));
        JPanel painelGradeTest = new JPanel(new GridLayout(10, 10));

        inicializarGrade(painelGradeA, gradeA, matrizA);
        inicializarGrade(painelGradeB, gradeB, matrizB);
        inicializarGrade(painelGradeTest, gradeTest, matrizTest);

        painelPrincipal.add(new JLabel("Matriz A", SwingConstants.CENTER));
        painelPrincipal.add(painelGradeA);

        // Adicionando um espaço entre a matriz A e a matriz B
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        painelPrincipal.add(new JLabel("Matriz B", SwingConstants.CENTER));
        painelPrincipal.add(painelGradeB);

        // Adicionando um espaço entre a matriz B e a matriz de Teste
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        painelPrincipal.add(new JLabel("Matriz de Teste", SwingConstants.CENTER));
        painelPrincipal.add(painelGradeTest);

        JPanel painelBotoes = new JPanel();

        JButton botaoTreinar = new JButton("Treinar");
        JButton botaoIdentificar = new JButton("Identificar");

        painelBotoes.add(botaoTreinar);
        painelBotoes.add(botaoIdentificar);

        // Adicionando o painel de botões e o painel principal à janela
        add(painelPrincipal, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    /**
     * Inicializa a grade de botões e configura a ação de alternância de pixels.
     *
     * @param painel O painel que contém a grade de botões.
     * @param grade  A matriz de botões a ser inicializada.
     * @param matriz A matriz de inteiros correspondente à grade de botões.
     */
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

    /**
     * Alterna o estado do pixel na matriz e altera a cor do botão correspondente.
     *
     * @param grade  A matriz de botões.
     * @param matriz A matriz de inteiros.
     * @param x      A coordenada x do pixel.
     * @param y      A coordenada y do pixel.
     */
    private void alternarPixel(JButton[][] grade, int[][] matriz, int x, int y) {
        if (matriz[x][y] == 0) {
            matriz[x][y] = 1;
            grade[x][y].setBackground(Color.BLACK);
        } else {
            matriz[x][y] = 0;
            grade[x][y].setBackground(Color.WHITE);
        }
    }

    /**
     * Treina o Perceptron usando as matrizes A e B.
     * Os pesos e o bias são atualizados para diferenciar as matrizes.
     */
    private void treinarPerceptron() {
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

    /**
     * Calcula a saída do Perceptron para uma entrada fornecida.
     *
     * @param entrada Um vetor de inteiros representando a entrada (matriz achatada).
     * @return 1 se a saída for positiva, -1 caso contrário.
     */
    private int calcularSaida(int[] entrada) {
        double soma = bias;
        for (int i = 0; i < 100; i++) {
            soma += pesos[i] * entrada[i];
        }
        return soma >= 0 ? 1 : -1; // Função de ativação (degrau)
    }

    /**
     * Identifica a letra (A ou B) com base na matriz de teste.
     */
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

    /**
     * Método principal que inicializa a interface gráfica do Perceptron.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        new PerceptronGUI();
    }
}
