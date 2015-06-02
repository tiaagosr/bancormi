/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabrmi.servidor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabrmi.RegistroNomes;

/**
 *
 * @author tiagosr
 */
public class InstanciaBancoServidor extends UnicastRemoteObject implements Runnable, InstanciaBanco{
    protected GerenciaContas dataBanco;
    protected String endLocal;
    protected RegistroNomes registro;
    protected List<InstanciaBanco> servidores;
    protected InstanciaBanco servidorMestre;
    protected int idMestre, idLocal;
    
    public InstanciaBancoServidor(String endRegistro, String endLocal) throws RemoteException{
        this.endLocal = endLocal;
        
        try {
            this.criaInstancia(endRegistro);
        } catch (NotBoundException ex) {
            Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void criaInstancia(String endRegistro) throws NotBoundException, MalformedURLException, RemoteException, IOException, ClassNotFoundException{
        //Cadastra-se no servidor de nomes
        registro = (RegistroNomes) Naming.lookup("//"+endRegistro+"/RegistroNomes");
        this.idMestre = this.registro.getIdMestre();
        
        Naming.rebind("//"+endLocal+"/Banco", this);
        
        idLocal = this.registro.registraServidor(this.endLocal);
        this.conectaServidores(); //Cria proxy para os outros servidores
        
        if(this.idMestre > -1 && idMestre != idLocal){ //Caso existir já existir servidor mestre online
            System.out.println("Sincronizando!");
            this.sincronizarServidor();
        }else{
            this.dataBanco = new GerenciaContas();
        }
        
        System.out.println("Contas: "+this.dataBanco.contaSize());
    }
    
    protected void conectaServidores() throws NotBoundException, MalformedURLException, RemoteException{
        List<String> endServidores = this.registro.getServidores();
        this.servidores = new ArrayList<>();
        
        for(String endServidor: endServidores){
            this.conectaServidor(endServidor);
        }
    }
    
    public void conectaServidor(String endServidor) throws NotBoundException, MalformedURLException, RemoteException{
        if(!endServidor.equals(endLocal)){
            System.out.println("Server: //"+endServidor+"/Banco");
            InstanciaBanco stump = (InstanciaBanco) Naming.lookup("//"+endServidor+"/Banco");
            this.servidores.add(stump);
        }
    }
    
    @Override
    public boolean isAlive() throws RemoteException{
        return true;
    }
    
    @Override
    public byte[] getBancoInstancia() throws IOException, RemoteException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        
        oos.writeObject(this.dataBanco);
        return bos.toByteArray();
    }
    
    protected void sincronizarServidor() throws IOException, ClassNotFoundException{
        Random genRandom = new Random();
        System.out.println("Servidores: "+servidores.size());
        int servidorOrigem = genRandom.nextInt(servidores.size());
        
        byte[] clone = servidores.get(servidorOrigem).getBancoInstancia();
        ByteArrayInputStream bis = new ByteArrayInputStream(clone);
        ObjectInputStream ois = new ObjectInputStream(bis);
        
        this.dataBanco = (GerenciaContas) ois.readObject();
    }
    
    @Override
    public GerenciaContas getBanco() throws RemoteException{
        return this.dataBanco;
    }
    
    public void setMestre(String mestreEnd, int mestreId) throws RemoteException, NotBoundException, MalformedURLException{
        this.servidorMestre = (InstanciaBancoServidor) Naming.lookup("//"+mestreEnd+"/Banco");
        this.idMestre = mestreId;
        System.out.println("Novo mestre: "+mestreEnd);
        
    }

    protected void substituiMestre(){
        int mestre = -1;
        
        try {
            mestre = registro.novoMestre(this.idMestre);
        } catch (RemoteException ex) {
            System.out.println("Falha ao conectar-se com o registro");
        }
        
        if(mestre == this.idLocal){
            System.out.println("EU SOU O NOVO MESTRE :D");
            for(InstanciaBanco servidor: servidores){
                try {
                    servidor.setMestre(this.endLocal, this.idLocal);
                } catch (RemoteException ex) {
                    Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NotBoundException ex) {
                    Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            boolean mestreOff = false;
            
            //Verifica se algum servidor está offline e remove-o da lista
            for(InstanciaBanco servidor: servidores){
                try {
                    servidorMestre.isAlive();
                } catch (RemoteException ex) {
                    System.out.println("Servidor offline encontrado, removendo-o da lista");
                    if(servidor == servidorMestre){
                        mestreOff = true;
                    }
                    servidores.remove(servidor);
                }
            }
            
            //Se o servidor mestre foi removido da lista
            if(mestreOff == true){
                System.out.println("Servidor mestre inacessível, elegendo novo mestre");
                this.substituiMestre();
            }
        }   
    }

    protected void replicaConta(int numeroConta) throws RemoteException {
        System.out.println("Replicando conta!");
        for(InstanciaBanco servidor: servidores){
            servidor.getBanco().novaConta(numeroConta);
        }
    }

    protected void replicaSaque(int conta, double qtd) throws RemoteException {
        for(InstanciaBanco servidor: servidores){
            servidor.getBanco().saque(conta, qtd);
        }
    }

    protected void replicaDeposito(int conta, double qtd) throws RemoteException {
        for(InstanciaBanco servidor: servidores){
            servidor.getBanco().deposito(conta, qtd);
        }
    }

    protected void replicaTransfere(int contaOrigem, int contaDest, double qtd) throws RemoteException {
        for(InstanciaBanco servidor: servidores){
            servidor.getBanco().transfere(contaOrigem, contaDest, qtd);
        }
    }

    @Override
    public boolean conectaConta(int conta) throws RemoteException {
        return this.getBanco().conectaConta(conta);
    }

    @Override
    public int novaConta(int numeroConta) throws RemoteException {
        int resultado = this.getBanco().novaConta(numeroConta);
        
        if(this.idMestre == this.idLocal){
            this.replicaConta(numeroConta);
        }
        return resultado;
        
    }

    @Override
    public double saque(int conta, double qtd) throws RemoteException {
        double resultado = this.getBanco().saque(conta, qtd);
        
        if(this.idMestre == this.idLocal){
            this.replicaSaque(conta, qtd);
        }
        return resultado;
    }

    @Override
    public double deposito(int conta, double qtd) throws RemoteException {
        double resultado = this.getBanco().deposito(conta, qtd);
        
        if(this.idMestre == this.idLocal){
            this.replicaDeposito(conta, qtd);
        }
        return resultado;
    }

    @Override
    public double transfere(int contaOrigem, int contaDest, double qtd) throws RemoteException {
        double resultado = this.getBanco().transfere(contaOrigem, contaDest, qtd);
        
        if(this.idMestre == this.idLocal){
            this.replicaTransfere(contaOrigem, contaDest, qtd);
        }
        return resultado;
    }

    @Override
    public double saldo(int conta) throws RemoteException {
        return this.getBanco().saldo(conta);
    }
    
}
