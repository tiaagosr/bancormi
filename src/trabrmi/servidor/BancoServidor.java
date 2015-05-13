package trabrmi.servidor;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import trabrmi.Banco;

/**
 *
 * @author udesc
 */
public class BancoServidor extends UnicastRemoteObject implements Banco{

    private HashMap<Integer, Conta> contas;
    
    BancoServidor() throws RemoteException{
        this.contas = new HashMap<>();
    }
    
    @Override
    public boolean conectaConta(int conta) throws RemoteException {        
        return contas.containsKey(conta);
    }

    @Override
    public int novaConta(int numero) throws RemoteException {
        if(contas.containsKey(numero)){
            return -1;
        }
        
        Conta tmpConta = new Conta(numero);
        contas.put(numero, tmpConta);
        
        return numero;        
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