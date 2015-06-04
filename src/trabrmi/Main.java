/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trabrmi;

import trabrmi.servidor.RegistroNomesServidor;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.reflect.generics.visitor.Reifier;
import trabrmi.servidor.InstanciaBancoServidor;

/**
 *
 * @author udesc
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        FrmInicial inicio = new FrmInicial();
        inicio.setVisible(true);*/
        
        try {
            LocateRegistry.createRegistry(1099);
            System.setProperty("java.rmi.server.hostname", "10.20.128.34");
            
            String endereco = "10.20.128.35:1099";
            
            //Externo
            InstanciaBancoServidor banco1 = new InstanciaBancoServidor(endereco, "10.20.128.34");
            Thread t1 = new Thread(banco1);
            t1.start();
            
            Banco agencia = (Banco) Naming.lookup("//"+endereco+"/Bancos");
            
            agencia.novaConta(5);
            agencia.deposito(5, 200);
            
            agencia.novaConta(6);
            agencia.deposito(5, 200);
            agencia.novaConta(8);
        } catch (NotBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
