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

    HashMap<String, HashMap<String, Integer>> index = new HashMap<>();

    public void indexing(String path) throws FileNotFoundException {

        ReadFiles files = new ReadFiles();
        int numDoc = 0;
        for (String s : files.listFiles(path)) {
            System.out.println("Processing File: " + s);

            String contentFile = this.preProcessing(files.getContentFile(s).toString());

            StringTokenizer terms = new StringTokenizer(contentFile);

            while (terms.hasMoreTokens()) {

                String term = this.preProcessing(terms.nextToken());
                String nameDoc = "doc" + numDoc;

                if (!index.containsKey(term)) {
                    HashMap<String, Integer> docs = new HashMap<>();
                    docs.put(nameDoc, 1);
                    index.put(term, docs);
                } else {
                    if (index.get(term).containsKey(nameDoc)) {
                        int numOcorr = index.get(term).get(nameDoc) + 1;
                        index.get(term).put(nameDoc, numOcorr);
                    } else {
                        index.get(term).put(nameDoc, 1);
                    }
                }
            }

            numDoc += 1;

        }
        System.out.println("" + GeraWeights());
    }

    public String GeraWeights() {

        Set<String> keys = index.keySet();

        for (String key : keys) {
            if (key != null) {
                Set<String> keyDocs = index.get(key).keySet();
                for (String doc : keyDocs) {
                    if (doc != null) {
                        System.out.println(index.get(key).get(doc));
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

        return 0.0;
    }

    public double getITF(int freq, int numDocs) {
        return 0.0;
    }
}
