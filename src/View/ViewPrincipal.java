/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ControllerColunasTabelas;
import Controller.ControllerTabelas;
import Model.ModelColunasTabelas;
import Model.ModelListaBanco;
import Model.ModelTabelas;
import static View.TabbedPaneDemo.createImageIcon;
import conexoes.ConexaoMySql;
import controller.ControllerListaBanco;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Analise em Curso
 */
public class ViewPrincipal extends javax.swing.JFrame {
//istacia a classe de conexão

    ConexaoMySql con = new ConexaoMySql();
    //faz aconexao para a requisição de metadados
    Connection conexao = null;
    //________________________________________________________________________//
    //lista temporaria de bancos
    ArrayList<ModelListaBanco> listaModelListaBancos = new ArrayList<>();
    //lista temporaria das tabelas dos bancos de dados
    ArrayList<ModelTabelas> listaModelTabelas = new ArrayList<>();
    //lista as coluns da tabela
    ArrayList<ModelColunasTabelas> listaModelColunasTabelas = new ArrayList<>();
    //________________________________________________________________________//
    //istancia o metodo
    ModelListaBanco modelListaBanco = new ModelListaBanco();
    //istancia o metodo
    ModelTabelas modelTabelas = new ModelTabelas();
    //istancia o metodo
    ModelColunasTabelas modelColunasTabelas = new ModelColunasTabelas();
    //________________________________________________________________________//
    //istancia o metodo
    ControllerListaBanco controllerListaBanco = new ControllerListaBanco();
    //istancia o metodo
    ControllerTabelas controllerTabelas = new ControllerTabelas();
    //istancia o metodo
    ControllerColunasTabelas controllerColunasTabelas = new ControllerColunasTabelas();
    //________________________________________________________________________//
    //pegara a localização do projeto
    File diretorio = new File("..");
    //nome da sessao padrao
    String SessaoDeflaut = "indice.dat";
    //caminho do aruivo de configuração
    private final String configuracao = "sys/config.ini";
    //conta o numerro de abas
    int conta;
    //index da aba selecionada
    int indeaba;
    //________________________________________________________________________//

    /**
     * Creates new form ViewPrincipal
     */
    public ViewPrincipal() {
        initComponents();
        //centraliza o formulario
        this.setLocationRelativeTo(null);
        //faz a janela maxminizada
        this.setExtendedState(MAXIMIZED_BOTH);
        //chama o metodo do arquivo de configuração
        this.lerconfiguracao();
        //chama o metodo de listagem de clientes
        this.carrega();
        //jtree                
        jTree1.setModel(createTreeModel());

        //iconos del jtree
        DefaultTreeCellRenderer render = (DefaultTreeCellRenderer) jTree1.getCellRenderer();
        render.setLeafIcon(new ImageIcon(getClass().getResource("/Icon/table.png")));
        render.setOpenIcon(new ImageIcon(getClass().getResource("/Icon/open.png")));
        render.setClosedIcon(new ImageIcon(getClass().getResource("/Icon/close.png")));

        //propiedades de jSplitPane1
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setDividerSize(10);
        //propiedades de jSplitPane2
        jSplitPane2.setOneTouchExpandable(true);
        jSplitPane2.setDividerLocation(500);
        jSplitPane2.setDividerSize(10);

    }

// ler os dados de configuração
    public void lerconfiguracao() {
        File file = new File(configuracao);

        try {
            FileInputStream fis = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fis);
            SessaoDeflaut = properties.getProperty("Sessao");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
            e.printStackTrace();
        }
    }

    //preenche a tabela com os funcionarios cadastrados o banco
    public void carrega() {
        listaModelListaBancos = controllerListaBanco.getListarTabelaBancosController();
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setNumRows(0);
        //inserir funcionario na tabela

        for (int i = 0; i < listaModelListaBancos.size(); i++) {
            //lista de objeto
            modelo.addRow(new Object[]{
                //                listaModelClientes.get(i).getcodCli(),
                listaModelListaBancos.get(i).getNome_Banco(),
                listaModelListaBancos.get(i).getNome_Colecao()

            });
        }
        try {
            con.conectar();
            jlNomeProduto.setText("Nome Produto: " + con.getCon().getMetaData().getDatabaseProductName());
            //seta o nome do produto
            jlNomeProduto.setToolTipText("<html>"
                    + "Nome Produto: " + con.getCon().getMetaData().getDatabaseProductName()
                    + "<br>Versão Produto: " + con.getCon().getMetaData().getDatabaseProductVersion()
                    + "<br>JDBC Driver name: " + con.getCon().getMetaData().getDriverName()
                    + "<br>JDBC Driver version: " + con.getCon().getMetaData().getDriverVersion()
                    + "</html>");

            con.fecharConexao();
        } catch (Exception e) {
            System.out.println("Erro ao obter nome do produto: " + e);
        }
    }

    /**
     * Metodo que lee el archivo HELP para crear el arbol de ayuda
     *
     * @return DefaultTreeModel
     */
    public DefaultTreeModel createTreeModel() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        DefaultMutableTreeNode hoja = new DefaultMutableTreeNode();
        //carga archivo help       
        try {
            InputStream input = new FileInputStream(new File(System.getProperty("user.dir") + "/Sessoes/" + SessaoDeflaut));

            Scanner scanner = new Scanner(input);

            //lee archivo linea por linea
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                //es la raiz del arbol
                if (line.length() > 0) {
                    switch (line.substring(0, 1)) {
                        case ">":
                            root = new DefaultMutableTreeNode(line.substring(1, line.length()));
                            break;
                        case "|":
                            hoja = new DefaultMutableTreeNode(line.substring(1, line.length()));
                            root.add(hoja);
                            break;
                        //es una hoja
                        case "-":
                            hoja.add(new DefaultMutableTreeNode(line.substring(1, line.length())));
                            break;
                        default:
                            break;
                    }
                }
            }
            scanner.close();
            //se añade arbol al modelo
            DefaultTreeModel modelo = new DefaultTreeModel(root);
            return modelo;
        } catch (FileNotFoundException | NullPointerException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Error Na Criação da Arvore" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(0);

        }

        return null;
    }

    //carregas as tabelas do banco selecionado
    //preenche a tabela com os funcionarios cadastrados o banco
    public void carregaTabelas(String no) {
        listaModelTabelas = controllerTabelas.getListarTabelaBancosController("" + no);

        //inserir funcionario na tabela
        //conta os registros da lista
        int cont = listaModelTabelas.size();
        if (cont > 0) {
            //limpa a tabela
            DefaultTableModel modelo = (DefaultTableModel) jtTabela.getModel();
            modelo.setNumRows(0);
            for (int i = 0; i < cont; i++) {
                //lista de objeto
                modelo.addRow(new Object[]{
                    i + 1,
                    listaModelTabelas.get(i).getTabela(),
                    listaModelTabelas.get(i).getTabelaQTDRegistros()

                });
            }
            //deixa a aba visivel
            shapeTabbedPane1.setSelectedIndex(1);
            //seta o nome do banco selecionado na aba
            shapeTabbedPane1.setTitleAt(1, "Tabela do Banco: " + no);
        }
    }

    //carregas as tabelas do banco selecionado
    //preenche a tabela com os funcionarios cadastrados o banco
    public void carregaColunasdaTabelas(String no) {
        listaModelColunasTabelas = controllerColunasTabelas.getListarColunaTabelaController("" + no);

        //inserir funcionario na tabela
        //conta os registros da lista
        int cont = listaModelColunasTabelas.size();
        if (cont > 0) {
            //limpa a tabela
            DefaultTableModel modelo = (DefaultTableModel) jtColunas.getModel();
            modelo.setNumRows(0);
            for (int i = 0; i < cont; i++) {
                //lista de objeto
                modelo.addRow(new Object[]{
                    i + 1,
                    listaModelColunasTabelas.get(i).getColumn_Name(),
                    listaModelColunasTabelas.get(i).getTipo(),
                    listaModelColunasTabelas.get(i).getNulo(),
                    listaModelColunasTabelas.get(i).getChavePrimary()
                });
            }
            //deixa a aba visivel
            shapeTabbedPane1.setSelectedIndex(2);
            //seta o nome do banco selecionado na aba
            shapeTabbedPane1.setTitleAt(1, "Tabela: " + no);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        shapeTabbedPane1 = new swing.xp.ShapeTabbedPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jCPanel1 = new com.bolivia.panel.JCPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jCPanel3 = new com.bolivia.panel.JCPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jCPanel5 = new com.bolivia.panel.JCPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtTabela = new javax.swing.JTable();
        jCPanel6 = new com.bolivia.panel.JCPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtColunas = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jCPanel2 = new com.bolivia.panel.JCPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jCPanel4 = new com.bolivia.panel.JCPanel();
        jlNomeProduto = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jtbAriadeTransferencia = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SGBD");

        shapeTabbedPane1.setOpaque(true);
        shapeTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                shapeTabbedPane1StateChanged(evt);
            }
        });

        jSplitPane2.setDividerLocation(300);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jCPanel1.setSecondColor(new java.awt.Color(255, 102, 0));
        jCPanel1.setUseMode(com.bolivia.panel.USEMODE.DEGRADED);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Banco", "Agrupamento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setOpaque(false);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jCPanel1Layout = new javax.swing.GroupLayout(jCPanel1);
        jCPanel1.setLayout(jCPanel1Layout);
        jCPanel1Layout.setHorizontalGroup(
            jCPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
        );
        jCPanel1Layout.setVerticalGroup(
            jCPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jCPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        jSplitPane2.setTopComponent(jCPanel1);

        jCPanel3.setFirstColor(new java.awt.Color(255, 51, 51));
        jCPanel3.setPreferredSize(new java.awt.Dimension(50, 200));
        jCPanel3.setSecondColor(new java.awt.Color(0, 255, 204));
        jCPanel3.setUseMode(com.bolivia.panel.USEMODE.DEGRADED);
        jCPanel3.setVisibleLogo(false);

        jTextPane1.setBackground(new java.awt.Color(0, 0, 0));
        jTextPane1.setForeground(new java.awt.Color(0, 153, 51));
        jScrollPane5.setViewportView(jTextPane1);

        javax.swing.GroupLayout jCPanel3Layout = new javax.swing.GroupLayout(jCPanel3);
        jCPanel3.setLayout(jCPanel3Layout);
        jCPanel3Layout.setHorizontalGroup(
            jCPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
        );
        jCPanel3Layout.setVerticalGroup(
            jCPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(jCPanel3);

        shapeTabbedPane1.addTab("Bancos", jSplitPane2);

        jCPanel5.setUseMode(com.bolivia.panel.USEMODE.DEGRADED);
        jCPanel5.setVisibleLogo(false);

        jtTabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°", "Nome", "Registro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtTabela.setOpaque(false);
        jScrollPane3.setViewportView(jtTabela);
        if (jtTabela.getColumnModel().getColumnCount() > 0) {
            jtTabela.getColumnModel().getColumn(0).setMinWidth(5);
            jtTabela.getColumnModel().getColumn(0).setPreferredWidth(5);
            jtTabela.getColumnModel().getColumn(2).setMinWidth(20);
            jtTabela.getColumnModel().getColumn(2).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jCPanel5Layout = new javax.swing.GroupLayout(jCPanel5);
        jCPanel5.setLayout(jCPanel5Layout);
        jCPanel5Layout.setHorizontalGroup(
            jCPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jCPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addGap(3, 3, 3))
        );
        jCPanel5Layout.setVerticalGroup(
            jCPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
        );

        shapeTabbedPane1.addTab("Tabela do Banco: ", jCPanel5);

        jCPanel6.setUseMode(com.bolivia.panel.USEMODE.DEGRADED);
        jCPanel6.setVisibleLogo(false);

        jtColunas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Tipo", "Tamanho", "Nulo", "Chave Primaria"
            }
        ));
        jtColunas.setOpaque(false);
        jScrollPane4.setViewportView(jtColunas);

        javax.swing.GroupLayout jCPanel6Layout = new javax.swing.GroupLayout(jCPanel6);
        jCPanel6.setLayout(jCPanel6Layout);
        jCPanel6Layout.setHorizontalGroup(
            jCPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jCPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane4)
                .addGap(3, 3, 3))
        );
        jCPanel6Layout.setVerticalGroup(
            jCPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
        );

        shapeTabbedPane1.addTab("Dados", jCPanel6);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 443, Short.MAX_VALUE)
        );

        shapeTabbedPane1.addTab("+", jPanel2);

        jSplitPane1.setRightComponent(shapeTabbedPane1);

        jCPanel2.setUseMode(com.bolivia.panel.USEMODE.DEGRADED);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Base de Dados");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTree1);

        javax.swing.GroupLayout jCPanel2Layout = new javax.swing.GroupLayout(jCPanel2);
        jCPanel2.setLayout(jCPanel2Layout);
        jCPanel2Layout.setHorizontalGroup(
            jCPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        jCPanel2Layout.setVerticalGroup(
            jCPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jCPanel2);

        jCPanel4.setFirstColor(new java.awt.Color(204, 204, 204));
        jCPanel4.setSecondColor(new java.awt.Color(204, 204, 204));
        jCPanel4.setUseMode(com.bolivia.panel.USEMODE.BICOLOR);

        jlNomeProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/server-mysql.png"))); // NOI18N
        jlNomeProduto.setText("Nome Produto: Sem Informação");

        javax.swing.GroupLayout jCPanel4Layout = new javax.swing.GroupLayout(jCPanel4);
        jCPanel4.setLayout(jCPanel4Layout);
        jCPanel4Layout.setHorizontalGroup(
            jCPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jCPanel4Layout.createSequentialGroup()
                .addGap(246, 246, 246)
                .addComponent(jlNomeProduto)
                .addContainerGap(258, Short.MAX_VALUE))
        );
        jCPanel4Layout.setVerticalGroup(
            jCPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jCPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jlNomeProduto))
        );

        jtbAriadeTransferencia.setRollover(true);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/tesoura.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jtbAriadeTransferencia.add(jLabel1);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/copiar.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jtbAriadeTransferencia.add(jLabel2);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/colar.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jtbAriadeTransferencia.add(jLabel3);
        jtbAriadeTransferencia.add(jSeparator1);

        jButton3.setText("+");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText("X");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jtbAriadeTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(193, 193, 193)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(0, 269, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtbAriadeTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton2)
                .addComponent(jButton3))
        );

        jMenu1.setText("Arquivo");

        jMenuItem1.setText("Novo Banco");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Novo Tabela");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Atualiza Arvore");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Sessão");
        jMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu3ActionPerformed(evt);
            }
        });

        jMenuItem4.setText("Nova Sessão");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem5.setText("Carregar Sessão");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jCPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSplitPane1)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 528, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu3ActionPerformed

    }//GEN-LAST:event_jMenu3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        ViewSessao viewPrincipal = new ViewSessao();
        viewPrincipal.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        ViewListaSessao viewListaSessao = new ViewListaSessao();
        viewListaSessao.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        //chama o metodo do arquivo de configuração
        this.lerconfiguracao();
        // chama o metodo que preenche a arvore atualizandoa
        jTree1.setModel(createTreeModel());
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        // pega o no da arvore
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        this.carregaTabelas("" + node);
        //carrega as colunas da tabela
        this.carregaColunasdaTabelas("" + node);

    }//GEN-LAST:event_jTree1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Tremove a aba
        shapeTabbedPane1.remove(indeaba);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void shapeTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_shapeTabbedPane1StateChanged
        //seta na variavel indeaba o index da aba selecionada
        indeaba = shapeTabbedPane1.getSelectedIndex();
        if (shapeTabbedPane1.getSelectedIndex() == 3) {
            Component component = new Component() {
            };
            ImageIcon icon = createImageIcon("/Icon/sheet.jpg");
            Icon figura = new ImageIcon(getToolkit().createImage(getClass().getResource("/Icon/server-mysql.png")));
//        shapeTabbedPane1.addTab("Título", icon, component, "ToolTip Text");
            System.out.println("icon: " + figura);
            shapeTabbedPane1.addTab("Título" + shapeTabbedPane1.getSelectedIndex(), icon, component, "ToolTip Text");
            shapeTabbedPane1.setSelectedComponent(component);
            int i = shapeTabbedPane1.getSelectedIndex();
            shapeTabbedPane1.setTabComponentAt(i, new ButtonTabComponent(shapeTabbedPane1));

        }

    }//GEN-LAST:event_shapeTabbedPane1StateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Component component = new Component() {
        };
        ImageIcon icon = createImageIcon("/Icon/sheet.jpg");
        Icon figura = new ImageIcon(getToolkit().createImage(getClass().getResource("/Icon/server-mysql.png")));
//        shapeTabbedPane1.addTab("Título", icon, component, "ToolTip Text");
        System.out.println("icon: " + icon);
        shapeTabbedPane1.addTab("Título" + shapeTabbedPane1.getSelectedIndex(), icon, component, "ToolTip Text");
        shapeTabbedPane1.setSelectedComponent(component);
        int i = shapeTabbedPane1.getSelectedIndex();
        System.out.println("i : : " + i);
        shapeTabbedPane1.setTabComponentAt(i, new ButtonTabComponent(shapeTabbedPane1));
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // corta o texto 
        jTextPane1.cut();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // copia o texto
        jTextPane1.copy();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        //cola o texto
        jTextPane1.paste();
    }//GEN-LAST:event_jLabel3MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private com.bolivia.panel.JCPanel jCPanel1;
    private com.bolivia.panel.JCPanel jCPanel2;
    private com.bolivia.panel.JCPanel jCPanel3;
    private com.bolivia.panel.JCPanel jCPanel4;
    private com.bolivia.panel.JCPanel jCPanel5;
    private com.bolivia.panel.JCPanel jCPanel6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel jlNomeProduto;
    private javax.swing.JTable jtColunas;
    private javax.swing.JTable jtTabela;
    private javax.swing.JToolBar jtbAriadeTransferencia;
    private swing.xp.ShapeTabbedPane shapeTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
