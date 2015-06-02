package trabrmi.servidor;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.TreeMap;
import trabrmi.Banco;

/**
 *
 * @author udesc
 */
public class BancoServidor extends UnicastRemoteObject implements Banco, Serializable{

    private TreeMap<Integer, Conta> contas = new TreeMap<>();
    
    BancoServidor() throws RemoteException{
    }
    
    @Override
    public boolean conectaConta(int conta) throws RemoteException {        
        return contas.containsKey(conta);
    }

    @Override
    public int novaConta(int numeroConta) throws RemoteException {
        Conta tmpConta = new Conta(numeroConta);
        contas.put(numeroConta, tmpConta);
        
        return numeroConta;        
    }

    @Override
    public double saque(int conta, double qtd) throws RemoteException {
       Conta tmpConta = contas.get(conta);
       return tmpConta.subSaldo(qtd);
    }

    @Override
    public double deposito(int conta, double qtd) throws RemoteException {
        Conta tmpConta = contas.get(conta);
        return tmpConta.addSaldo(qtd);
    }

    @Override
    public double transfere(int contaOrigem, int contaDest, double qtd) throws RemoteException {
        Conta tmpOrigem = contas.get(contaOrigem);
        Conta tmpDest = contas.get(contaDest);
        double saldoOrigem;
        
        if(tmpDest == null){
            return -1;
        }
        
        if((saldoOrigem = tmpOrigem.subSaldo(qtd)) < 0){
            return -2;
        }
        
        tmpDest.addSaldo(qtd);
        return saldoOrigem;
    }

    @Override
    public double saldo(int conta) throws RemoteException {
        Conta tmpConta = contas.get(conta);
        
        if(tmpConta == null){
            return -1;
        }
        
        return tmpConta.getSaldo();
    }
    
}