package trabrmi.servidor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tiagosr
 */
public class Conta{
    
    private double saldo;
    private int numero;

    public Conta(int numero){
        this.numero = numero;
        this.saldo = 0;
    }
    
    /**
     * @return the saldo
     */
    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double quantia){
        this.saldo = quantia;
    }
    
    public double addSaldo(double quantia){
        this.setSaldo(this.getSaldo()+quantia);
        return this.getSaldo();
    }
    
    public double subSaldo(double quantia){
        double saldo = this.getSaldo() - quantia;
        if(saldo >= 0){
            this.setSaldo(saldo);
            return saldo;
        }
        return -1;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
}
