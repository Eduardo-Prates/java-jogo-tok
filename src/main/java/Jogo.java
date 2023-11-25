import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JogoTela extends JFrame {
    private Tabuleiro tabuleiro;
    private Rodada rodada;
    private Tok tok;
    private int[] resultado;
    private Peca pecaSelecionada;
    private JPanel painelPrincipal;
    private JButton[][] botoes;
    private JPanel painelLateral;
    private JButton botaoEsquerda;
    private JButton botaoDireita;
    private JButton botaoCima;
    private JButton botaoBaixo;
    private JLabel erroLabel;

    public JogoTela() {
        rodada = new Rodada();

        configurarJanela();

        configurarBarraMenus();

        configurarPainelPrincipal();

        configurarPainelLateral();

        criarTabuleiro();

        setVisible(true);
    }

    private void configurarBarraMenus() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuJogo = new JMenu("Jogo");

        JMenuItem reiniciarItem = new JMenuItem("Reiniciar");

        JMenuItem sairItem = new JMenuItem("Sair");
        reiniciarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarJogo();
            }
        });
        sairItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuJogo.add(reiniciarItem);
        menuJogo.add(sairItem);

        JMenu menuAutores = new JMenu("Autores");

        JMenuItem verNomesItem = new JMenuItem("Ver Nomes");
        verNomesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarNomesAutores();
            }
        });

        menuAutores.add(verNomesItem);

        menuBar.add(menuJogo);
        menuBar.add(menuAutores);

        setJMenuBar(menuBar);
    }

    private void reiniciarJogo() {
    }

    private void mostrarNomesAutores() {
        JOptionPane.showMessageDialog(this, "Autores do Jogo:\ncasé \nkng");
    }

    private void configurarJanela(){
        setTitle("Jogo do Tok");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());
    }

    private void configurarPainelPrincipal(){
        tabuleiro = new Tabuleiro();
        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new GridLayout(5, 5));
        botoes = new JButton[5][5];
        add(painelPrincipal, BorderLayout.CENTER);
    }

    private void criarTabuleiro() {
        for (int i = 0; i < botoes.length; i++) {
            for (int j = 0; j < botoes[i].length; j++) {

                botoes[i][j] = new JButton();

                Peca peca = tabuleiro.getPecaAt(i, j);

                if(peca.getTipo() == peca.PECA_TOK){
                    tok = (Tok) peca;
                }

                if(peca.getTipo() == Peca.SLOT_VAZIO){
                    botoes[i][j].setText("");
                } else {
                    botoes[i][j].setText(String.valueOf(peca.getTipo()));
                }

                int linha = i;
                int coluna = j;
                botoes[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        botoes[linha][coluna].setBackground(Color.LIGHT_GRAY);
                        pecaSelecionada = peca;
                        deselecionarPecasComExcessao(linha, coluna);
                    }
                });
                painelPrincipal.add(botoes[i][j]);
            }
        }
    }

    private void configurarPainelLateral(){
        painelLateral = new JPanel();
        painelLateral.setLayout(new GridLayout(5, 1));

        botaoEsquerda = new JButton("Esquerda");
        botaoDireita = new JButton("Direita");
        botaoCima = new JButton("Cima");
        botaoBaixo = new JButton("Baixo");

        erroLabel = new JLabel("Erros serão exibidos aqui", JLabel.CENTER);

        painelLateral.add(botaoEsquerda);
        painelLateral.add(botaoDireita);
        painelLateral.add(botaoCima);
        painelLateral.add(botaoBaixo);
        painelLateral.add(erroLabel);

        adicionarListenersDoPainelLateral();

        add(painelLateral, BorderLayout.EAST);
    }

    private void deselecionarPecasComExcessao(int linha, int coluna){
        for(int i = 0; i < botoes.length; i++){
            for(int j = 0; j < botoes[i].length; j++){
                if(i == linha && j == coluna){
                    continue;
                }
                botoes[i][j].setBackground(null);
            }
        }
    }

    private void deselecionarTodasAsPecas(){
        for(int i = 0; i < botoes.length; i++){
            for(int j = 0; j < botoes[i].length; j++){
                botoes[i][j].setBackground(null);
            }
        }
    }

    private void adicionarListenersDoPainelLateral(){
        botaoBaixo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pecaSelecionada.mover(tabuleiro, Peca.BAIXO);
                atualizarTabuleiro();
                deselecionarTodasAsPecas();
            }
        });
        botaoCima.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pecaSelecionada.mover(tabuleiro, Peca.CIMA);

                atualizarTabuleiro();
                deselecionarTodasAsPecas();
            }
        });
        botaoEsquerda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pecaSelecionada.mover(tabuleiro, Peca.ESQUERDA);
                atualizarTabuleiro();
                deselecionarTodasAsPecas();
            }
        });
        botaoDireita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pecaSelecionada.mover(tabuleiro, Peca.DIREITA);
                atualizarTabuleiro();
                deselecionarTodasAsPecas();
            }
        });
    }

    private void atualizarTabuleiro(){

        //VERIFICANDO SE É O INÍCIO DA RODADA
        if(rodada.etapa % 1 == 0){
            resultado = tok.verificarCondicaoDeVitoria(tabuleiro, rodada);
            rodada.passarMetadeRodada();
        } else {
            resultado = tok.verificarCondicaoDeVitoria(tabuleiro, rodada);
            rodada.passarRodada();
        }
        for(int i = 0; i < botoes.length; i++){
            for(int j = 0; j < botoes[i].length; j++){
                Peca peca = tabuleiro.getPecaAt(i, j);

                if(peca.getTipo() == Peca.SLOT_VAZIO){
                    botoes[i][j].setText("");
                } else {
                    botoes[i][j].setText(String.valueOf(peca.getTipo()));
                }

                int linha = i;
                int coluna = j;

                botoes[i][j].removeActionListener(botoes[i][j].getActionListeners()[0]);
                botoes[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        botoes[linha][coluna].setBackground(Color.LIGHT_GRAY);
                        pecaSelecionada = peca;
                        deselecionarPecasComExcessao(linha, coluna);
                    }
                });
            }
        }

        if(resultado[Tok.SITUACAO_VITORIA] == Tok.HOUVE_VITORIA){
            System.out.println("Houve vitória");
        }
    }
}