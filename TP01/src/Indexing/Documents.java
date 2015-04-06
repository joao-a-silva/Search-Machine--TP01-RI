/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Indexing;

/**
 *
 * @author joao
 */
public class Documents {
    
    private String name;
    private double frqTF;

    Documents(String name) {
        this.name = name;
        this.frqTF = 1.0;
    }

    public Documents(String name, double sim) {
        this.name = name;
        this.frqTF = sim;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFrqTF() {
        return frqTF;
    }

    public void setFrqTF(double frqTF) {
        this.frqTF = frqTF;
    }


        
}
