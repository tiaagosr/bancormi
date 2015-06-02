/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trabrmi;

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

        try {
            LocateRegistry.createRegistry(1099);
            RegistroNomesServidor reg = new RegistroNomesServidor("localhost");
            
            InstanciaBancoServidor banco = new InstanciaBancoServidor("localhost", "localhost/0");
            Thread t = new Thread(banco);
            t.start();
            Banco agencia = (Banco) Naming.lookup("//localhost/Bancos");
            
            agencia.novaConta(5);
            agencia.deposito(5, 200);
            
            InstanciaBancoServidor banco1 = new InstanciaBancoServidor("localhost", "localhost/1");
            Thread t1 = new Thread(banco1);
            t1.start();
            
            agencia.novaConta(6);
            agencia.deposito(5, 200);
            
            InstanciaBancoServidor banco2 = new InstanciaBancoServidor("localhost", "localhost/2");
            Thread t2 = new Thread(banco2);
            t2.start();
            
            agencia.novaConta(8);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Naming.unbind("//localhost/0/Banco");
            t.stop();
            
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Naming.unbind("//localhost/1/Banco");
            t1.stop();
            
            System.out.println("FIM");
        } catch (NotBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
