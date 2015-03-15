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
    
    private int freq;
    private double TF;
    private double IDF;

    Documents(int i) {
       this.freq = i;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
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
