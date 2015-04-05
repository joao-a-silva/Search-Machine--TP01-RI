/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indexing;

/**
 *
 * @author joaoantoniosilva
 */
public class Weight {
    
    // Calculus the TF
    public double getTF(double freq) {
        if (freq > 0) {
            return 1 + Math.log(freq) / Math.log(2.0);
        } else {
            return 0.0;
        }
    }

    // Calculus the IDF
    public double getIDF(int totalDocs, int numDocsOcorr) {        
        return Math.log((double)totalDocs /(double) numDocsOcorr) / Math.log(2.0);             
    }

    //Caucuslus the TF-IDF
    public double getTFIDF(double tf, double idf) {
       if (tf > 0)
           return tf * idf;
       else
           return 0.0;
    }
    
}
