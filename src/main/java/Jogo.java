import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Jogo extends JFrame {
    private Tabuleiro tabuleiro;
    private Rodada rodada;
    private int[] resultado;
    private Peca pecaSelecionada;


    private JPanel painelPrincipal;
    private JButton[][] botoes;


    private JPanel painelLateral;

    private JPanel subPainelControles;
    private JButton botaoEsquerda;
    private JButton botaoDireita;
    private JButton botaoCima;
    private JButton botaoBaixo;

    private JPanel subPainelInfos;
    private JLabel rodadaLabel;
    private JLabel jogadorLabel;
    private JLabel pecaSelecionadaLabel;
    private JLabel pecaASerMovidaLabel;

    private JPanel subPainelErros;
    private JLabel erroLabel;

    public Jogo() {
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
        JMenu menuAutores = new JMenu("Autores");
        JMenuItem verNomesItem = new JMenuItem("Ver Nomes");

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
        verNomesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarNomesAutores();
            }
        });

        menuJogo.add(reiniciarItem);
        menuJogo.add(sairItem);
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
        setSize(800, 600);
        setResizable(false);
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
                Peca peca = tabuleiro.getPecaAt(i, j);

                botoes[i][j] = new JButton();
                if(peca.getTipo() == Peca.SLOT_VAZIO){
                    botoes[i][j].setText("");
                } else {
                    if(peca.getTipo() != Peca.TOK) {
                        botoes[i][j].setText(String.valueOf(peca.getTipo()));
                    } else {
                        botoes[i][j].setText("TOK");
                    }
                }

                int linha = i;
                int coluna = j;
                botoes[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        botoes[linha][coluna].setBackground(Color.LIGHT_GRAY);
                        pecaSelecionada = peca;
                        deselecionarPecasComExcessao(linha, coluna);
                        atualizarDados();
                    }
                });


                painelPrincipal.add(botoes[i][j]);
            }
        }
    }

    private void atualizarDados(){
        rodadaLabel.setText(String.valueOf(rodada.etapa));
        jogadorLabel.setText(String.valueOf(rodada.jogadorAtual));

        if(pecaSelecionada == null){
            pecaSelecionadaLabel.setText("Nenhuma");
        } else {
            pecaSelecionadaLabel.setText(String.valueOf(pecaSelecionada));
        }

        if(rodada.pecaASerMovida == Rodada.PECA_PADRAO){
            pecaASerMovidaLabel = new JLabel("Padrão", JLabel.CENTER);
        } else {
            pecaASerMovidaLabel = new JLabel("TOK", JLabel.CENTER);
        }
    }

    private void configurarPainelLateral(){
        painelLateral = new JPanel();
        painelLateral.setLayout(new GridLayout(3, 1));

        configurarSubPainelInfos();

        configurarSubPainelControles();

        configurarSubPainelErros();

        add(painelLateral, BorderLayout.EAST);
    }
    private void configurarSubPainelInfos(){
        subPainelInfos = new JPanel();
        subPainelInfos.setLayout(new GridLayout(4, 2));

        rodadaLabel = new JLabel(String.valueOf(rodada.etapa), JLabel.CENTER);
        jogadorLabel = new JLabel(String.valueOf(rodada.jogadorAtual), JLabel.CENTER);
        pecaSelecionadaLabel = new JLabel(String.valueOf(pecaSelecionada), JLabel.CENTER);
        if(rodada.pecaASerMovida == Rodada.PECA_PADRAO){
            pecaASerMovidaLabel = new JLabel("Padrão", JLabel.CENTER);
        } else {
            pecaASerMovidaLabel = new JLabel("TOK", JLabel.CENTER);
        }

        subPainelInfos.add(new JLabel("Rodada: "));
        subPainelInfos.add(rodadaLabel);

        subPainelInfos.add(new JLabel("Jogador: "));
        subPainelInfos.add(jogadorLabel);

        subPainelInfos.add(new JLabel("Posição Selecionada: "));
        if(pecaSelecionada == null){
            pecaSelecionadaLabel.setText("Nenhuma");
        } else {
            pecaSelecionadaLabel.setText(String.valueOf(pecaSelecionada));
        }
        subPainelInfos.add(pecaSelecionadaLabel);

        subPainelInfos.add(new JLabel("Peça a ser movida: "));
        subPainelInfos.add(pecaASerMovidaLabel);

        painelLateral.add(subPainelInfos);
    }

    private void configurarSubPainelControles(){
        subPainelControles = new JPanel();
        subPainelControles.setLayout(new GridLayout(2, 3));

        botaoEsquerda = new JButton("←");
        botaoDireita = new JButton("→");
        botaoCima = new JButton("↑");
        botaoBaixo = new JButton("↓");

        subPainelControles.add(new JLabel(""));
        subPainelControles.add(botaoCima);
        subPainelControles.add(new JLabel(""));
        subPainelControles.add(botaoEsquerda);
        subPainelControles.add(botaoBaixo);
        subPainelControles.add(botaoDireita);
        adicionarListenersDoSubPainelControles();
        painelLateral.add(subPainelControles);
    }

    private void configurarSubPainelErros(){
        subPainelErros = new JPanel();
        subPainelErros.setLayout(new GridLayout(1, 1));

        erroLabel = new JLabel("Erros serão exibidos aqui", JLabel.CENTER);

        subPainelErros.add(erroLabel);
        painelLateral.add(subPainelErros);
    }

    private void adicionarListenersDoSubPainelControles(){
        botaoBaixo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pecaSelecionada.mover(tabuleiro, Peca.BAIXO);
                deselecionarTodasAsPecas();
                atualizarTabuleiro();
            }
        });
        botaoCima.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pecaSelecionada.mover(tabuleiro, Peca.CIMA);
                deselecionarTodasAsPecas();
                atualizarTabuleiro();
            }
        });
        botaoEsquerda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pecaSelecionada.mover(tabuleiro, Peca.ESQUERDA);
                deselecionarTodasAsPecas();
                atualizarTabuleiro();
            }
        });
        botaoDireita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pecaSelecionada.mover(tabuleiro, Peca.DIREITA);
                deselecionarTodasAsPecas();
                atualizarTabuleiro();
            }
        });
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
        pecaSelecionada = null;
    }


    private void atualizarTabuleiro(){
        resultado = new int[3];
        resultado = tabuleiro.verificarCondicaoDeVitoria(rodada);

        rodada.passarRodada();
        for(int i = 0; i < botoes.length; i++){
            for(int j = 0; j < botoes[i].length; j++){
                Peca peca = tabuleiro.getPecaAt(i, j);

                int linha = i;
                int coluna = j;

                if(peca.getTipo() == Peca.SLOT_VAZIO){
                    botoes[i][j].setText("");
                } else {
                    if(peca.getTipo() != Peca.TOK) {
                        botoes[i][j].setText(String.valueOf(peca.getTipo()));
                    } else {
                        botoes[i][j].setText("TOK");
                    }
                }

                botoes[i][j].removeActionListener(botoes[i][j].getActionListeners()[0]);
                botoes[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        botoes[linha][coluna].setBackground(Color.LIGHT_GRAY);
                        pecaSelecionada = peca;
                        deselecionarPecasComExcessao(linha, coluna);
                        atualizarDados();
                    }
                });
            }
        }
        atualizarDados();
        verificarSituacao();
    }

    private void verificarSituacao(){
        if(resultado[Tabuleiro.SITUACAO_VITORIA] == Tabuleiro.HOUVE_VITORIA){
            desabilitarBotoes();
            String tipoVitoria = "";
            if(resultado[Tabuleiro.TIPO_VITORIA] == Tabuleiro.VITORIA_POR_POSICAO){
                tipoVitoria = "Por objetivo";
            } else {
                tipoVitoria = "Por imobilização";

            }
            String mensagem = "Jogo Finalizado!\n" +
                              "Vencedor: Jogador " + resultado[Tabuleiro.JOGADOR_VENCEDOR] + "\n" +
                              "Tipo de Vitória: " + tipoVitoria;
            Object[] opcoes = {"Encerrar", "Jogar Novamente"};
            int escolha = JOptionPane.showOptionDialog(
                    this,
                    mensagem,
                    "Fim de Jogo",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    new ImageIcon(""),
                    opcoes,
                    opcoes[1]
            );
            if(escolha == JOptionPane.YES_OPTION){
                System.exit(0);
            } else {
                reiniciarJogo();
            }
        }
    }

    private void desabilitarBotoes(){
        for(int i = 0; i < botoes.length; i++){
            for(int j = 0; j < botoes[i].length; j++){
                botoes[i][j].setEnabled(false);
            }
        }
        botaoBaixo.setEnabled(false);
        botaoCima.setEnabled(false);
        botaoDireita.setEnabled(false);
        botaoEsquerda.setEnabled(false);
    }
}