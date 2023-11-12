public class Tok extends Peca{
    public Tok(int i, int j, int tipo) {
        super(i, j, tipo);
    }

    public boolean verificarImobilizacao(){
        if(this.getLinha() == 2){
            return true;
        }
        return false;
    }
}
