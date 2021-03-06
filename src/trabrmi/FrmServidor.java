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
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabrmi.servidor.InstanciaBancoServidor;
import trabrmi.servidor.RegistroNomesServidor;

/**
 *
 * @author tiagosr
 */
public class FrmServidor extends javax.swing.JFrame {
    private final int MODO_SERVIDOR = 2, MODO_REGISTRO = 3;
    private RegistroNomesServidor registroNomes;
    private InstanciaBancoServidor instanciaBanco;
    private Thread threadServidor;
    private String endLocal;
    /**
     * Creates new form FrmServidor
     */
    public FrmServidor(int modo, String endRegistro, String endLocal, int porta) {
        String nome = "Um erro";
        
        initComponents();
        this.endLocal = endLocal;
        try {
            try{
                LocateRegistry.createRegistry(porta);
            }catch(ExportException e){
                LocateRegistry.getRegistry(porta);
            }
            //System.setProperty("java.rmi.server.hostname", endLocal);
            switch(modo){
                case MODO_SERVIDOR:
                    nome = "Servidor";
                    instanciaBanco = new InstanciaBancoServidor(endRegistro, endLocal);
                    threadServidor = new Thread(instanciaBanco);
                    threadServidor.start();
                break;
                case MODO_REGISTRO:
                    nome = "Servidor de registro";
                    registroNomes = new RegistroNomesServidor(endLocal);
                break;
            }  
        } catch (RemoteException ex) {
            Logger.getLogger(FrmServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.labelAviso.setText(nome+" em execução!");
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelAviso = new javax.swing.JLabel();
        btnFechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servidor");
        setResizable(false);

        labelAviso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelAviso.setText("Em execução!");

        btnFechar.setText("Fechar");
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAviso, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(labelAviso, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFechar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnFecharActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFechar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelAviso;
    // End of variables declaration//GEN-END:variables
}
