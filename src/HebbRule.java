import java.util.Scanner;

public class HebbRule {

    private static final int SIZE = 100; // Matriz 10x10
    private static final int LETTER_COUNT = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] input = new int[LETTER_COUNT][SIZE];
        int[] y = {1, -1}; // 1 para a primeira letra, -1 para a segunda letra
        int[] weights = new int[SIZE];
        int bias = 0;

        // Inicializando pesos e entradas
        for (int i = 0; i < LETTER_COUNT; i++) {
            for (int j = 0; j < SIZE; j++) {
                weights[j] = 0;
                input[i][j] = -1;
            }
        }

        // Entrada dos dados para a primeira letra (A)
        input[0][3] = input[0][10] = input[0][12] = input[0][18] = 1;
        input[0][20] = input[0][25] = input[0][29] = 1;
        input[0][33] = input[0][34] = input[0][35] = 1;
        input[0][36] = input[0][37] = input[0][41] = 1;
        input[0][45] = input[0][49] = input[0][53] = 1;
        input[0][57] = input[0][61] = 1;

        // Entrada dos dados para a segunda letra (B)
        input[1][9] = input[1][10] = input[1][11] = 1;
        input[1][17] = input[1][19] = input[1][25] = 1;
        input[1][26] = input[1][33] = input[1][34] = 1;
        input[1][43] = input[1][49] = input[1][51] = 1;
        input[1][57] = input[1][58] = input[1][59] = 1;

        // Treinamento com a Regra de Hebb
        for (int i = 0; i < LETTER_COUNT; i++) {
            for (int j = 0; j < SIZE; j++) {
                weights[j] += input[i][j] * y[i];
            }
            bias += y[i];
        }

        // Teste de reconhecimento
        int[] testInput = new int[SIZE];
        System.out.println("Desenhe uma letra na matriz 10x10 (insira 1 para pixel ativo e 0 para inativo):");
        for (int i = 0; i < SIZE; i++) {
            testInput[i] = scanner.nextInt();
        }

        int output = 0;
        for (int i = 0; i < SIZE; i++) {
            output += weights[i] * testInput[i];
        }
        output += bias;

        if (output >= 0) {
            System.out.println("Letra reconhecida: A");
        } else {
            System.out.println("Letra reconhecida: B");
        }

        scanner.close();
    }
}
