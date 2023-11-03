public class Tabuleiro {
    private Peca[][] tabuleiro;
    public Tabuleiro() {
        this.tabuleiro = new Peca[5][5];
        definirPosicoes();
    }
    private void definirPosicoes(){
        for(int linha = 0; linha < 5; linha++){
            for(int coluna = 0; coluna < 5; coluna++){
                switch (linha) {
                    case 0 -> this.tabuleiro[linha][coluna] = new Peca(linha, coluna, 1);
                    case 4 -> this.tabuleiro[linha][coluna] = new Peca(linha, coluna, 2);
                    case 2 -> {
                        if (coluna == 2) {
                            this.tabuleiro[linha][coluna] = new Peca(linha, coluna, 3);
                        } else {
                            this.tabuleiro[linha][coluna] = new Peca(linha, coluna, 0);
                        }
                    }
                    default -> this.tabuleiro[linha][coluna] = new Peca(linha, coluna, 0);
                }
            }
        }
    }
    public void adicionarPeca(Peca peca, int tipo){
        this.tabuleiro[peca.getLinha()][peca.getColuna()] = new Peca(peca.getLinha(), peca.getColuna(), tipo);
    }

    public void removerPeca(Peca peca){
        adicionarPeca(peca, Peca.SLOT_VAZIO);
    }

    public Peca getPecaAt(int linha, int coluna){
        return tabuleiro[linha][coluna];
    }

    public void exibirTabuleiro(){
        for(int linha = 0; linha < 5; linha++){
            for(int coluna = 0; coluna < 5; coluna++){
                System.out.print(this.tabuleiro[linha][coluna].getTipo() + " ");
            }
            System.out.println();
        }
    }
}
