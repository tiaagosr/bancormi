/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabrmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author tiagosr
 */
public class FrmBancoCliente extends javax.swing.JFrame {

    public Banco conexao;
    public int contaAtual = -1;
    
    public FrmBancoCliente(String enderecoRegistro) {
        initComponents();
        habilitarOperacoes(false);
        try {
            conexao = (Banco) Naming.lookup("//"+enderecoRegistro+"/Bancos");
        } catch (NotBoundException ex) {
            System.out.println("Servidor de registro offline!");
        } catch (MalformedURLException ex) {
            System.out.println("Endereço IP fornecido inválido!");
        } catch (RemoteException ex) {
            Logger.getLogger(FrmBancoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtConta = new javax.swing.JTextField();
        btnConectar = new javax.swing.JButton();
        btnCriar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtQtd = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDestino = new javax.swing.JTextField();
        btnDepositar = new javax.swing.JButton();
        btnSacar = new javax.swing.JButton();
        btnTransferir = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        btnSaldo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SDI - Cliente");

        jLabel1.setText("Conta:");

        txtConta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtContaFocusGained(evt);
            }
        });
        txtConta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContaActionPerformed(evt);
            }
        });

        btnConectar.setText("Conectar");
        btnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarActionPerformed(evt);
            }
        });

        btnCriar.setText("Criar");
        btnCriar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCriarActionPerformed(evt);
            }
        });

        jLabel2.setText("Quantia:");

        jLabel4.setFont(new java.awt.Font("Droid Sans", 1, 15)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Operações");

        jLabel5.setText("Conta destino:");

        btnDepositar.setText("Depositar");
        btnDepositar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepositarActionPerformed(evt);
            }
        });

        btnSacar.setText("Sacar");
        btnSacar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSacarActionPerformed(evt);
            }
        });

        btnTransferir.setText("Transferir");
        btnTransferir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransferirActionPerformed(evt);
            }
        });

        btnFechar.setText("Fechar");
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        btnSaldo.setText("Verificar Saldo");
        btnSaldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaldoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(btnConectar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnCriar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtConta, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTransferir, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(txtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFechar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSacar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSaldo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(btnDepositar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConectar)
                    .addComponent(btnCriar))
                .addGap(25, 25, 25)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDepositar))
                        .addGap(40, 40, 40)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSaldo)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSacar)
                        .addGap(46, 46, 46)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTransferir)
                .addGap(18, 18, 18)
                .addComponent(btnFechar)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void habilitarOperacoes(boolean habilitar){
        txtQtd.setEditable(habilitar);
        txtDestino.setEditable(habilitar);
        btnDepositar.setEnabled(habilitar);
        btnSacar.setEnabled(habilitar);
        btnSaldo.setEnabled(habilitar);
        btnTransferir.setEnabled(habilitar);
    }
    
    public int getTxtConta(){
        return Integer.parseInt(this.txtConta.getText());
    }
    
    private void btnSaldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaldoActionPerformed
        try {
            JOptionPane.showMessageDialog(this, "Saldo: "+String.valueOf(this.conexao.saldo(this.contaAtual)));
        } catch (RemoteException ex) {
            System.out.println("Erro ao contatar servidor!");
        }
    }//GEN-LAST:event_btnSaldoActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnTransferirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransferirActionPerformed
        Integer destino = Integer.valueOf(txtDestino.getText());
        Double qtd = Double.valueOf(txtQtd.getText());
        double resultado = -1;
        try {
            resultado = this.conexao.transfere(contaAtual, destino, qtd);
        } catch (RemoteException ex) {
            System.out.println("Erro ao contatar servidor!");
        }
        if(resultado == -1){
            JOptionPane.showMessageDialog(this, "Conta destino inválida!");
        }
        if(resultado == -2){
            JOptionPane.showMessageDialog(this, "Saldo insuficiente!");
        }
        if(resultado > -1){
            JOptionPane.showMessageDialog(this, "Transferência efetuada com sucesso!");
        }
        
    }//GEN-LAST:event_btnTransferirActionPerformed

    private void btnSacarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSacarActionPerformed
        Double qtd = Double.valueOf(txtQtd.getText());
        double saldo = -1;
        try {
            saldo = this.conexao.saque(this.contaAtual, qtd);
        } catch (RemoteException ex) {
            System.out.println("Erro ao contatar servidor!");
        }
        
        String mensagem = "Saldo insuficiente!";
        if(saldo >= 0){
            mensagem = "Saque efetuado com sucesso, novo saldo: "+saldo;
        }
        JOptionPane.showMessageDialog(this, mensagem);
    }//GEN-LAST:event_btnSacarActionPerformed

    private void btnDepositarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepositarActionPerformed
        Double qtd = Double.valueOf(txtQtd.getText());
        try {
            JOptionPane.showMessageDialog(this, "Novo Saldo: "+String.valueOf(this.conexao.deposito(this.contaAtual, qtd)));;
        } catch (RemoteException ex) {
            System.out.println("Erro ao contatar servidor!");
        }
    }//GEN-LAST:event_btnDepositarActionPerformed

    private void btnCriarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCriarActionPerformed
        int conta = -1;
        try {
            conta = conexao.novaConta(getTxtConta());
        } catch (RemoteException ex) {
            Logger.getLogger(FrmBancoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.contaAtual = getTxtConta();
        if(conta > -1){
            this.habilitarOperacoes(true);
        }
        
    }//GEN-LAST:event_btnCriarActionPerformed

    private void btnConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConectarActionPerformed
        boolean conectou = false;
        try {
            conectou = conexao.conectaConta(getTxtConta());
        } catch (RemoteException ex) {
            System.out.println("Erro ao contatar servidor!");
        }
        
        this.contaAtual = getTxtConta();
        if(conectou){
            this.habilitarOperacoes(conectou);
        }
    }//GEN-LAST:event_btnConectarActionPerformed

    private void txtContaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContaActionPerformed
        this.habilitarOperacoes(false);
    }//GEN-LAST:event_txtContaActionPerformed

    private void txtContaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContaFocusGained
        this.habilitarOperacoes(false);
    }//GEN-LAST:event_txtContaFocusGained
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConectar;
    private javax.swing.JButton btnCriar;
    private javax.swing.JButton btnDepositar;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnSacar;
    private javax.swing.JButton btnSaldo;
    private javax.swing.JButton btnTransferir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtConta;
    private javax.swing.JTextField txtDestino;
    private javax.swing.JTextField txtQtd;
    // End of variables declaration//GEN-END:variables
}
