package trabrmi.servidor;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.TreeMap;

/**
 *
 * @author udesc
 */
public class GerenciaContas implements Serializable{

    private TreeMap<Integer, Conta> contas = new TreeMap<>();
    
    GerenciaContas() throws RemoteException{
    }
    
    public int contaSize(){
        return contas.size();
    }
    
    public boolean conectaConta(int conta) throws RemoteException {        
        return contas.containsKey(conta);
    }

    public int novaConta(int numeroConta) throws RemoteException {
        Conta tmpConta = new Conta(numeroConta);
        contas.put(numeroConta, tmpConta);
        
        return numeroConta;        
    }

    public double saque(int conta, double qtd) throws RemoteException {
       Conta tmpConta = contas.get(conta);
       return tmpConta.subSaldo(qtd);
    }

    public double deposito(int conta, double qtd) throws RemoteException {
        Conta tmpConta = contas.get(conta);
        return tmpConta.addSaldo(qtd);
    }

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

    public double saldo(int conta) throws RemoteException {
        Conta tmpConta = contas.get(conta);
        
        if(tmpConta == null){
            return -1;
        }
        
        return tmpConta.getSaldo();
    }
    
}