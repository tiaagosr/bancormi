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
            InstanciaBancoServidor banco = new InstanciaBancoServidor("localhost", "localhost");
            System.out.println("Conectando...");
            Banco agencia = (Banco) Naming.lookup("//localhost/Bancos");
            System.out.println("Conectou!");
            agencia.novaConta(5);
            agencia.deposito(5, 200);
            System.out.println("Saldo: "+agencia.saldo(5));
            InstanciaBancoServidor banco2 = new InstanciaBancoServidor("localhost", "localhost/alt");
            agencia.novaConta(6);
            System.out.println("meleca");
        } catch (NotBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
