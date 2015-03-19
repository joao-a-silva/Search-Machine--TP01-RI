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
            String nameDoc = files.pathToTitle(s);
            System.out.println("Processing File: " + s);

            String contentFile = files.getContentFile(s).toString();

            StringTokenizer terms = new StringTokenizer(contentFile);

            while (terms.hasMoreTokens()) {

                String term = terms.nextToken();
                           
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
        Weight w = new Weight();

        for (String key : keys) {

            if (key != null) {
                Set<String> keyDocs = index.get(key).keySet();
//                System.out.print(key+ "-");
                int numDocsOcorr = index.get(key).size();

                for (String doc : keyDocs) {
                    if (doc != null) {
                        //obtains file frequency
                        int freq = index.get(key).get(doc).getFreq();
                        //calculos of the tf weight
                        double tf = w.getTF(freq);
                        //calculus of the IDF weight
                        double idf = w.getIDF(numDocsOcorr, totalDocs);
                        //calculus of the TF-IDF weights
                        double tfidf = w.getTFIDF(freq, tf, idf);
                        index.get(key).get(doc).setTF(tf);
                        index.get(key).get(doc).setIDF(idf);
//                        System.out.println("Freq= "+ freq + "; TF = "+ index.get(key).get(doc).getTF()+"; ITF = "+
//                                index.get(key).get(doc).getIDF()+"; TFIDF = "+ tfidf );                    

                    }
                }
            }

        }
        return null;
    }

    
    
}
