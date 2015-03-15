/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indexing;

import PreProcessing.*;
import java.io.FileNotFoundException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import jdk.nashorn.internal.parser.Token;

/**
 *
 * @author joao
 */
public class Indexer {

    HashMap<String, HashMap<String, Documents>> index = new HashMap<>();
    int totalDocs = 0;

    public void indexing(String path) throws FileNotFoundException {

        ReadFiles files = new ReadFiles();

        for (String s : files.listFiles(path)) {
            System.out.println("Processing File: " + s);

            String contentFile = this.preProcessing(files.getContentFile(s).toString());

            StringTokenizer terms = new StringTokenizer(contentFile);

            while (terms.hasMoreTokens()) {

                String term = this.preProcessing(terms.nextToken());
                String nameDoc = "doc" + totalDocs;

                if (!index.containsKey(term)) {
                    HashMap<String, Documents> docs = new HashMap<>();
                    docs.put(nameDoc, new Documents(1));
                    index.put(term, docs);
                } else {
                    if (index.get(term).containsKey(nameDoc)) {
                        int numOcorr = index.get(term).get(nameDoc).getFreq() + 1;
                        index.get(term).put(nameDoc, new Documents(numOcorr));
                    } else {
                        index.get(term).put(nameDoc, new Documents(1));
                    }
                }
            }

            totalDocs += 1;

        }
        System.out.println("" + GeraWeights());
    }

    public String GeraWeights() {

        Set<String> keys = index.keySet();

        for (String key : keys) {

            if (key != null) {
                Set<String> keyDocs = index.get(key).keySet();
                System.out.print(key+ "-");
                int numDocsOcorr = index.get(key).size();
                System.out.println(numDocsOcorr);
                for (String doc : keyDocs) {
                    if (doc != null) {
                        int freq = index.get(key).get(doc).getFreq();
                        double tf = this.getTF(freq);
                        double idf =this.getIDF(numDocsOcorr);
                        double tfidf = this.getTFIDF(freq, tf, idf);
                        index.get(key).get(doc).setTF(tf);
                        index.get(key).get(doc).setIDF(idf);
                        System.out.println("Freq= "+ freq + "; TF = "+ index.get(key).get(doc).getTF()+"; ITF = "+
                                index.get(key).get(doc).getIDF()+"; TFIDF = "+ tfidf );                    

                    }
                }
            }

        }
        return null;
    }

    public String preProcessing(String s) {
        String out = s.replaceAll("\\.", "");
        return out.replaceAll("\\,", "").toLowerCase();

        //return Normalizer.normalize(s, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
    }

    public double getTF(int freq) {

        if (freq > 0) {
            return 1 + Math.log(freq) / Math.log(2);
        } else {
            return 0.0;
        }
    }

    public double getIDF(int numDocsOcorr) {        
        return Math.log((double)totalDocs /(double) numDocsOcorr) / Math.log(2);     
        
    }

    private double getTFIDF(int freq, double tf, double idf) {
       if (freq > 0)
           return tf * idf;
       else
           return 0.0;
    }
}
