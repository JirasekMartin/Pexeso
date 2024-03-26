
import java.util.Random;
import java.util.Scanner;

public class PexesoGame {

    private char[][] board;
    private boolean[][] cardFound;
    private int size;
    private int pairsLeft;

    public PexesoGame(int size) {
        this.size = size;
        this.pairsLeft = size * size / 2;
        this.board = new char[size][size];
        this.cardFound = new boolean[size][size];
        initializeBoard();
        shuffleBoard();
    }

    private void initializeBoard() {
        char value = 'A';
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = value;
                cardFound[i][j] = false;
                if (j % 2 == 1) {
                    value++;
                }
            }
        }
    }

    private void shuffleBoard() {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int randI = random.nextInt(size);
                int randJ = random.nextInt(size);

                // Swap values
                char temp = board[i][j];
                board[i][j] = board[randI][randJ];
                board[randI][randJ] = temp;
            }
        }
    }

    private void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cardFound[i][j]) {
                    System.out.print(board[i][j] + " ");
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }

    private boolean isGameOver() {
        return pairsLeft == 0;
    }

    private void revealCard(int row, int col) {
        cardFound[row][col] = true;
    }

    private void hideCards(int row1, int col1, int row2, int col2) {
        cardFound[row1][col1] = false;
        cardFound[row2][col2] = false;
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);

        while (!isGameOver()) {
            printBoard();

            int row1, col1, row2, col2;

            do {
                System.out.println("Zadejte řádek první karty (0-" + (size - 1) + "):");
                while (!scanner.hasNextInt()) {
                    System.out.println("Neplatný vstup. Zadejte číslo.");
                    scanner.next();
                }
                row1 = scanner.nextInt();

                System.out.println("Zadejte sloupec první karty (0-" + (size - 1) + "):");
                while (!scanner.hasNextInt()) {
                    System.out.println("Neplatný vstup. Zadejte číslo.");
                    scanner.next();
                }
                col1 = scanner.nextInt();
            } while (row1 < 0 || row1 >= size || col1 < 0 || col1 >= size || cardFound[row1][col1]);

            revealCard(row1, col1);
            printBoard();

            do {
                System.out.println("Zadejte řádek druhé karty (0-" + (size - 1) + "):");
                while (!scanner.hasNextInt()) {
                    System.out.println("Neplatný vstup. Zadejte číslo.");
                    scanner.next();
                }
                row2 = scanner.nextInt();

                System.out.println("Zadejte sloupec druhé karty (0-" + (size - 1) + "):");
                while (!scanner.hasNextInt()) {
                    System.out.println("Neplatný vstup. Zadejte číslo.");
                    scanner.next();
                }
                col2 = scanner.nextInt();
            } while (row2 < 0 || row2 >= size || col2 < 0 || col2 >= size || cardFound[row2][col2] || (row1 == row2 && col1 == col2));

            revealCard(row2, col2);
            printBoard();

            if (board[row1][col1] == board[row2][col2]) {
                pairsLeft--;
                System.out.println("Gratulujeme! Našli jste pár.");
            } else {
                System.out.println("Omlouváme se, karty se neshodují.");
                hideCards(row1, col1, row2, col2);
            }
        }

        System.out.println("Gratulujeme! Hra skončila, všechny páry byly nalezeny.");
        scanner.close();
    }

    public static void main(String[] args) {
        PexesoGame pexesoGame = new PexesoGame(4); // Zde si můžete zvolit velikost pole
        pexesoGame.playGame();
    }
}