public class Peca {
    private int linha;
    private int coluna;
    private int tipo;

    public static final int ESQUERDA = 1;
    public static final int DIREITA = 2;
    public static final int CIMA = 3;
    public static final int BAIXO = 4;


    public static final int SLOT_VAZIO = 0;
    public static final int PECA_JOGADOR1 = 1;
    public static final int PECA_JOGADOR2 = 2;


    public Peca(int i, int j, int tipo) {
        this.linha = i;
        this.coluna = j;
        this.tipo = tipo;
    }

    public void mover(Tabuleiro tabuleiro, int direcao){
        while(estaLivre(tabuleiro, direcao)){
            System.out.println("Movendo...");
            mudarPosicao(tabuleiro, direcao);
        }
    }

    public boolean estaLivre(Tabuleiro tabuleiro, int direcao){
        if(verificarLimites(direcao)){
            System.out.println("passou dos limites");
            switch (direcao) {
                case ESQUERDA -> {
                    Peca slotEsquerda = tabuleiro.getPecaAt(this.getLinha(), this.getColuna() - 1);
                    if (slotEsquerda.getTipo() == SLOT_VAZIO) {
                        return true;
                    }
                }
                case DIREITA -> {
                    Peca slotDireita = tabuleiro.getPecaAt(this.getLinha(), this.getColuna() + 1);
                    if (slotDireita.getTipo() == SLOT_VAZIO) {
                        return true;
                    }
                }
                case CIMA -> {
                    Peca slotAbaixo = tabuleiro.getPecaAt(this.getLinha() - 1, this.getColuna());
                    if (slotAbaixo.getTipo() == SLOT_VAZIO) {
                        return true;
                    }
                }
                case BAIXO -> {
                    Peca slotAcima = tabuleiro.getPecaAt(this.getLinha() + 1, this.getColuna());
                    if (slotAcima.getTipo() == SLOT_VAZIO) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean verificarLimites(int direcao){
        switch (direcao) {
            case ESQUERDA -> {
                if(this.getColuna() - 1 < 0){
                    return false;
                }
            }
            case DIREITA -> {
                if(this.getColuna() + 1 > 4){
                    return false;
                }
            }
            case CIMA -> {
                if(this.getLinha() - 1 < 0){
                    return false;
                }
            }
            case BAIXO -> {
                if(this.getLinha() + 1 > 4){
                    return false;
                }
            }
        }
        return true;
    }

    public void mudarPosicao(Tabuleiro tabuleiro, int direcao){
        tabuleiro.removerPeca(this);

        switch (direcao) {
            case ESQUERDA -> this.setColuna(this.getColuna() - 1);
            case DIREITA -> this.setColuna(this.getColuna() + 1);
            case CIMA -> this.setLinha(this.getLinha() - 1);
            case BAIXO -> this.setLinha(this.getLinha() + 1);
        }
        tabuleiro.adicionarPeca(this, this.getTipo());
    }

    //getters
    public int getLinha() {
        return linha;
    }
    public int getColuna() {
        return coluna;
    }
    public int getTipo() {
        return tipo;
    }

    //setters
    public void setLinha(int linha) {
        this.linha = linha;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
