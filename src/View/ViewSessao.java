/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ControllerTabelas;
import Model.ModelListaBanco;
import Model.ModelTabelas;
import conexoes.ConexaoMySql;
import controller.ControllerListaBanco;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Analise em Curso
 */
public class ViewSessao extends javax.swing.JFrame {

    //istacia a classe de conexão
    ConexaoMySql con = new ConexaoMySql();
    //________________________________________________________________________//
    //lista temporaria de bancos
    ArrayList<ModelListaBanco> listaModelListaBancos = new ArrayList<>();
    //lista temporaria das tabelas dos bancos de dados
    ArrayList<ModelTabelas> listaModelTabelas = new ArrayList<>();
    //________________________________________________________________________//
    //istancia o metodo
    ModelListaBanco modelListaBanco = new ModelListaBanco();
    //istancia o metodo
    ModelTabelas modelTabelas = new ModelTabelas();
    //________________________________________________________________________//
    //istancia o metodo
    ControllerListaBanco controllerListaBanco = new ControllerListaBanco();
    //istancia o metodo
    ControllerTabelas controllerTabelas = new ControllerTabelas();
    //________________________________________________________________________//
    private final String configuracao = "sys/config.ini";

    /**
     * Creates new form ViewSessao
     */
    public ViewSessao() {
        initComponents();
        //centraliza o formulario
        this.setLocationRelativeTo(null);
        //chama o metodo de listagem de clientes
        this.carrega();
        //seta o caminho da pasta sessao no jtfild
        jtCaminho.setText(System.getProperty("user.dir") + "/Sessoes/");

    }

    //preenche a tabela com os funcionarios cadastrados o banco
    public void carrega() {
        listaModelListaBancos = controllerListaBanco.getListarTabelaBancosController();
        DefaultTableModel modelo = (DefaultTableModel) jtSessao.getModel();
        modelo.setNumRows(0);
        //inserir funcionario na tabela
        //conta os registros da lista
        int cont = listaModelListaBancos.size();
        for (int i = 0; i < cont; i++) {
            //lista de objeto
            modelo.addRow(new Object[]{
                //                listaModelClientes.get(i).getcodCli(),
                listaModelListaBancos.get(i).getNome_Banco(),
                listaModelListaBancos.get(i).getNome_Colecao()

            });
        }

    }

    //metodo para salvar as propriedades
    public boolean salvaconfiguracao() {
        try {
            File file = new File(configuracao);
            Properties properties = new Properties();

            properties.setProperty("Sessao", jtNomeSessao.getText() + ".dat");
            //JOptionPane.showMessageDialog(null, "Alteração Salva com Sucesso");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                properties.store(fos, "CODE 0:");

                fos.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR: " + ex);
                ex.printStackTrace();

            }
            return true;
        } catch (Exception e) {
            // JOptionPane.showMessageDialog(null, "Ouvi um comportamento anormal ao salvar suas configurações veja o erro ocorido erro=\"" + e + "\"");

            return false;
        }
    }

    //salva a  sesão
    public void salvarSessao() {
        // button to export data to text file
        String filePath = jtCaminho.getText() + "\\" + jtNomeSessao.getText() + ".dat";
        File file = new File(filePath);
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(">[Base de Dados]\n");
            for (int i = 0; i < jtSessao.getRowCount(); i++) {//rows
                for (int j = 0; j < jtSessao.getColumnCount(); j++) {//columns
                    bw.write(" |" + jtSessao.getValueAt(i, j).toString() + "\n ");
                }
                listaModelTabelas = controllerTabelas.getListarTabelaBancosController("" + jtSessao.getValueAt(i, 0).toString());
                for (int x = 0; x < listaModelTabelas.size(); x++) {//rows
                    bw.write("  -" + listaModelTabelas.get(x).getTabela() + "\n ");
                }
                bw.newLine();
            }

            bw.close();
            fw.close();

            JOptionPane.showMessageDialog(new JFrame(), "Sessão salva com sucesso!", "Aviso", JOptionPane.OK_OPTION);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Error" + ex, "Error", JOptionPane.ERROR_MESSAGE);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jtSessao = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jtCaminho = new javax.swing.JTextField();
        jtNomeSessao = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sessão");

        jtSessao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Banco"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtSessao);

        jButton1.setText("Remover");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Salvar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jtNomeSessao.setText("indice");

        jLabel1.setText("Nome da Sessão");

        jButton3.setText("Salvar e Carregar ao Iniciar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtCaminho)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtNomeSessao)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtNomeSessao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtCaminho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //remove a linha selecionada da tabela jTableClass
        DefaultTableModel dtm = (DefaultTableModel) jtSessao.getModel();
        if (jtSessao.getSelectedRow() >= 0) {
            dtm.removeRow(jtSessao.getSelectedRow());
            jtSessao.setModel(dtm);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.salvarSessao();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //salva o nome da sessao no arquivo de configuração
        this.salvaconfiguracao();
        //salva a sessao
        this.salvarSessao();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(ViewSessao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewSessao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewSessao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewSessao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewSessao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jtCaminho;
    private javax.swing.JTextField jtNomeSessao;
    private javax.swing.JTable jtSessao;
    // End of variables declaration//GEN-END:variables
}
