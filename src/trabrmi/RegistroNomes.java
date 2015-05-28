/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trabrmi;

import java.util.List;

/**
 *
 * @author udesc
 */
public interface RegistroNomes {
    public List<String> getServidores();
    
    public void registraServidor(String endereco);
    
    public void setMestre(String endereco);
    
    public String getMestre();
}
