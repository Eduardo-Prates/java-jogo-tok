import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Tabuleiro tabuleiro = new Tabuleiro();
        Scanner input = new Scanner(System.in);

        menu(tabuleiro, input);
    }

    public static void menu(Tabuleiro tabuleiro, Scanner input) throws IOException, InterruptedException {
        tabuleiro.exibirTabuleiro();

        int op = 0;

        System.out.println("==================================");
        System.out.println("1 - Mover uma peca                       ");
        System.out.println("==================================");

        op = input.nextInt();

        switch (op){
            case 1:
                System.out.println("Informe a linha da peca que deseja mover: ");
                int linha = input.nextInt();
                System.out.println("Informe a coluna da peca que deseja mover: ");
                int coluna = input.nextInt();

                System.out.println("Informe a direcao que deseja mover: ");
                System.out.println("1 - Esquerda");
                System.out.println("2 - Direita");
                System.out.println("3 - Cima");
                System.out.println("4 - Baixo");
                int direcao = input.nextInt();

                Peca pecaASerMovimentada = tabuleiro.getPecaAt(linha, coluna);
                pecaASerMovimentada.mover(tabuleiro, direcao);

                menu(tabuleiro, input);
        }
    }
}
