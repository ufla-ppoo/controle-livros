package br.ufla.dcc.ppoo.gui;

import br.ufla.dcc.ppoo.i18n.I18N;
import br.ufla.dcc.ppoo.imagens.GerenciadorDeImagens;
import br.ufla.dcc.ppoo.servicos.GerenciadorUsuarios;
import br.ufla.dcc.ppoo.util.Utilidades;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Classe que representa a tela Meus Livros
 * 
 * @author Paulo Jr. e Julio Alves
 */
public class TelaMeusLivros {

    // referência para a tela principal
    private final TelaPrincipal telaPrincipal;
            
    // componentes da tela
    private JDialog janela;
    private GridBagLayout layout;
    private GridBagConstraints gbc;
    private JButton btnNovoLivro;
    private JButton btnEditarLivro;
    private JButton btnDeletarLivro;
    private JButton btnSalvarLivro;
    private JButton btnCancelar;
    private JTable tbLivros;
    private JLabel lbTitulo;
    private JLabel lbAutores;
    private JLabel lbAno;
    private JLabel lbNumPaginas;
    private JLabel lbDescricao;
    private JTextField txtTitulo;
    private JTextField txtAutores;
    private JTextField txtAno;
    private JTextField txtNumPaginas;
    private JTextArea taDescricao;

     /**
     * Constrói a tela Meus Livros guardando a referência da tela principal.
     * 
     * @param telaPrincipal Referência da tela principal.
     */
    public TelaMeusLivros(TelaPrincipal telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
    }

    /**
     * Inicializa a tela, construindo seus componentes, configurando os eventos
     * e, ao final, exibe a tela.
     */
    public void inicializar() {
        construirTela();
        configurarEventosTela();
        exibirTela();
    }

    /**
     * Constrói a janela tratando internacionalização, componentes e layout.
     */
    private void construirTabela() {
        Object[] titulosColunas = {
            I18N.obterRotuloLivroTitulo(),
            I18N.obterRotuloLivroAutores()
        };

        // Dados "fake"
        Object[][] dados = {
            {"O dia do Curinga", "Jostein Gaarder"},
            {"Java: como programar", "Deitel, Paul & Deitel, Harvey"}
        };

        tbLivros = new JTable(dados, titulosColunas);
        tbLivros.setPreferredScrollableViewportSize(new Dimension(500, 70));
        tbLivros.setFillsViewportHeight(true);
    }

    /**
     * Adiciona um componente à tela.
     */
    private void adicionarComponente(Component c,
            int anchor, int fill, int linha,
            int coluna, int largura, int altura) {
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.gridy = linha;
        gbc.gridx = coluna;
        gbc.gridwidth = largura;
        gbc.gridheight = altura;
        gbc.insets = new Insets(5, 5, 5, 5);
        layout.setConstraints(c, gbc);
        janela.add(c);
    }

    /**
     * Trata o estado inicial da tela
     */
    private void prepararComponentesEstadoInicial() {
        tbLivros.clearSelection();
        tbLivros.setEnabled(true);

        txtTitulo.setText("");
        txtAutores.setText("");
        txtAno.setText("");
        txtNumPaginas.setText("");
        taDescricao.setText("");

        txtTitulo.setEditable(false);
        txtAutores.setEditable(false);
        txtAno.setEditable(false);
        txtNumPaginas.setEditable(false);
        taDescricao.setEditable(false);

        btnNovoLivro.setEnabled(true);
        btnEditarLivro.setEnabled(false);
        btnSalvarLivro.setEnabled(false);
        btnDeletarLivro.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    /**
     * Trata o estado da tela para seleção de livros
     */
    private void prepararComponentesEstadoSelecaoLivro() {
        txtTitulo.setEditable(false);
        txtAutores.setEditable(false);
        txtAno.setEditable(false);
        txtNumPaginas.setEditable(false);
        taDescricao.setEditable(false);

        btnNovoLivro.setEnabled(true);
        btnEditarLivro.setEnabled(true);
        btnSalvarLivro.setEnabled(false);
        btnDeletarLivro.setEnabled(true);
        btnCancelar.setEnabled(true);
    }

    /**
     * Trata o estado da tela para cadastro de novo livro
     */
    private void prepararComponentesEstadoNovoLivro() {
        tbLivros.clearSelection();
        tbLivros.setEnabled(false);

        txtTitulo.setText("");
        txtAutores.setText("");
        txtAno.setText("");
        txtNumPaginas.setText("");
        taDescricao.setText("");

        txtTitulo.setEditable(true);
        txtAutores.setEditable(true);
        txtAno.setEditable(true);
        txtNumPaginas.setEditable(true);
        taDescricao.setEditable(true);

        btnNovoLivro.setEnabled(false);
        btnEditarLivro.setEnabled(false);
        btnSalvarLivro.setEnabled(true);
        btnDeletarLivro.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    /**
     * Trata o estado da tela para cadastro livro editado
     */
    private void prepararComponentesEstadoEditouLivro() {
        tbLivros.setEnabled(false);

        txtTitulo.setEditable(true);
        txtAutores.setEditable(true);
        txtAno.setEditable(true);
        txtNumPaginas.setEditable(true);
        taDescricao.setEditable(true);

        btnNovoLivro.setEnabled(false);
        btnEditarLivro.setEnabled(false);
        btnSalvarLivro.setEnabled(true);
        btnDeletarLivro.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    /**
     * Adiciona os componentes da tela tratando layout e internacionalização
     */
    private void adicionarComponentes() {
        construirTabela();
        JScrollPane scrollPaneTabela = new JScrollPane(tbLivros);
        adicionarComponente(scrollPaneTabela,
                GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                0, 0, 4, 1);

        lbTitulo = new JLabel(I18N.obterRotuloLivroTitulo());
        adicionarComponente(lbTitulo,
                GridBagConstraints.LINE_END,
                GridBagConstraints.NONE,
                1, 0, 1, 1);

        txtTitulo = new JTextField(25);
        adicionarComponente(txtTitulo,
                GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL,
                1, 1, 3, 1);

        lbAutores = new JLabel(I18N.obterRotuloLivroAutores());
        adicionarComponente(lbAutores,
                GridBagConstraints.LINE_END,
                GridBagConstraints.NONE,
                2, 0, 1, 1);

        txtAutores = new JTextField(25);
        adicionarComponente(txtAutores,
                GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL,
                2, 1, 3, 1);

        lbAno = new JLabel(I18N.obterRotuloLivroAno());
        adicionarComponente(lbAno,
                GridBagConstraints.LINE_END,
                GridBagConstraints.NONE,
                3, 0, 1, 1);

        txtAno = new JTextField(8);
        adicionarComponente(txtAno,
                GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL,
                3, 1, 1, 1);

        lbNumPaginas = new JLabel(I18N.obterRotuloLivroNroPaginas());
        adicionarComponente(lbNumPaginas,
                GridBagConstraints.LINE_END,
                GridBagConstraints.NONE,
                3, 2, 1, 1);

        txtNumPaginas = new JTextField(8);
        adicionarComponente(txtNumPaginas,
                GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL,
                3, 3, 1, 1);

        lbDescricao = new JLabel(I18N.obterRotuloLivroDescricao());
        adicionarComponente(lbDescricao,
                GridBagConstraints.LINE_END,
                GridBagConstraints.NONE,
                4, 0, 1, 1);

        taDescricao = new JTextArea(7, 25);
        JScrollPane scrollPaneDescricao = new JScrollPane(taDescricao,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        adicionarComponente(scrollPaneDescricao,
                GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL,
                4, 1, 3, 1);

        btnNovoLivro = new JButton(I18N.obterBotaoNovo(),
                GerenciadorDeImagens.NOVO);

        btnEditarLivro = new JButton(I18N.obterBotaoEditar(),
                GerenciadorDeImagens.EDITAR);

        btnSalvarLivro = new JButton(I18N.obterBotaoSalvar(),
                GerenciadorDeImagens.OK);

        btnDeletarLivro = new JButton(I18N.obterBotaoDeletar(),
                GerenciadorDeImagens.DELETAR);

        btnCancelar = new JButton(I18N.obterBotaoCancelar(),
                GerenciadorDeImagens.CANCELAR);

        prepararComponentesEstadoInicial();

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnNovoLivro);
        painelBotoes.add(btnEditarLivro);
        painelBotoes.add(btnSalvarLivro);
        painelBotoes.add(btnDeletarLivro);
        painelBotoes.add(btnCancelar);

        adicionarComponente(painelBotoes,
                GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                6, 0, 4, 1);
    }

    /**
     * Trata a selação de livros na grade.
     */
    private void selecionouLivro() {
        // Dados "fake"
        String texto = String.format("Linha selecionada: %d", tbLivros.getSelectedRow());
        txtTitulo.setText(texto);
        txtAutores.setText(texto);
        txtAno.setText(texto);
        txtNumPaginas.setText(texto);
        taDescricao.setText(texto);
    }

    /**
     * Configura os eventos da tela.
     */
    private void configurarEventosTela() {
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                janela.dispose();
            }
        });

        tbLivros.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                prepararComponentesEstadoSelecaoLivro();
                selecionouLivro();
            }
        });

        btnEditarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararComponentesEstadoEditouLivro();
            }
        });

        btnSalvarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararComponentesEstadoInicial();
            }
        });

        btnNovoLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararComponentesEstadoNovoLivro();
            }
        });

        btnDeletarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Utilidades.msgConfirmacao(I18N.obterConfirmacaoDeletar())) {
                    // Remover livro!
                }
            }
        });
    }

    /**
     * Constrói a janela tratando internacionalização, componentes e layout.
     */
    private void construirTela() {
        janela = new JDialog();
        janela.setTitle(I18N.obterTituloTelaMeusLivros());
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        janela.setLayout(layout);
        adicionarComponentes();
        janela.pack();
    }

    /**
     * Exibe a tela.
     */
    private void exibirTela() {
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.setLocationRelativeTo(telaPrincipal.obterJanela());
        janela.setModal(true);
        janela.setVisible(true);
        janela.setResizable(false);
    }
}
