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
    private int freq;
    private double TF;
    private double IDF;

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTF() {
        return TF;
    }

    public void setTF(double TF) {
        this.TF = TF;
    }

    public double getIDF() {
        return IDF;
    }

    public void setIDF(double IDF) {
        this.IDF = IDF;
    }

    
    
    
    
    
}
