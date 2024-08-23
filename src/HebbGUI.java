import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HebbGUI extends JFrame {
    // Matrizes de botões e pixels para as duas matrizes de comparação (A e B)
    private JButton[][] gradeA = new JButton[10][10];
    private JButton[][] gradeB = new JButton[10][10];
    private JButton[][] gradeTest = new JButton[10][10]; // Matriz para o teste de identificação

    private int[][] matrizA = new int[10][10];
    private int[][] matrizB = new int[10][10];
    private int[][] matrizTest = new int[10][10];

    // Construtor da interface gráfica
    public HebbGUI() {
        setTitle("Regra de Hebb - Comparação de Matrizes");
        setLayout(new GridLayout(4, 1));

        // Painéis para as matrizes
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

        // Painel para os botões "Treinar" e "Identificar"
        JPanel painelBotoes = new JPanel();

        JButton botaoTreinar = new JButton("Treinar");
        botaoTreinar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Matrizes A e B treinadas!"); // Simulação do treinamento
            }
        });

        JButton botaoIdentificar = new JButton("Identificar");
        botaoIdentificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                identificarLetra(); // Chama o método para identificar a letra mais próxima
            }
        });

        painelBotoes.add(botaoTreinar);
        painelBotoes.add(botaoIdentificar);
        add(painelBotoes);

        // Configurações da janela
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Método para inicializar uma grade de botões
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
                        alternarPixel(grade, matriz, x, y); // Alterna o estado do pixel quando o botão é clicado
                    }
                });
                painel.add(grade[i][j]); // Adiciona o botão ao painel
            }
        }
    }

    // Método para alternar o estado de um pixel na matriz (ativo/inativo)
    private void alternarPixel(JButton[][] grade, int[][] matriz, int x, int y) {
        if (matriz[x][y] == 0) {
            matriz[x][y] = 1;
            grade[x][y].setBackground(Color.BLACK); // Ativa o pixel e muda a cor para preto
        } else {
            matriz[x][y] = 0;
            grade[x][y].setBackground(Color.WHITE); // Desativa o pixel e muda a cor para branco
        }
    }

    // Método para identificar qual letra (A ou B) a matriz de teste se aproxima mais
    private void identificarLetra() {
        int distanciaA = calcularDistancia(matrizTest, matrizA);
        int distanciaB = calcularDistancia(matrizTest, matrizB);

        String letraReconhecida = (distanciaA <= distanciaB) ? "A" : "B";
        JOptionPane.showMessageDialog(this, "A matriz de teste se parece mais com a letra: " + letraReconhecida);
    }

    // Método para calcular a "distância" entre duas matrizes
    private int calcularDistancia(int[][] matriz1, int[][] matriz2) {
        int distancia = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (matriz1[i][j] != matriz2[i][j]) {
                    distancia++;
                }
            }
        }
        return distancia;
    }

    // Método principal para executar o programa
    public static void main(String[] args) {
        new HebbGUI(); // Cria e exibe a interface gráfica
    }
}
