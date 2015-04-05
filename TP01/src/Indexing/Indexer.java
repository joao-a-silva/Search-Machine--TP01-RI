/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indexing;

import Common.*;
import PreProcessing.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 *
 * @author joao
 */
public class Indexer {

    public static HashMap<String, ArrayList<Documents>> vocabulary;
     static int totalDocs = 0;
    
    HashSet<String> stopWords;
    ReadFiles f;

    public Indexer() throws FileNotFoundException {
        this.vocabulary = new HashMap<>();
        this.f = new ReadFiles();
        StopWords s = new StopWords();
        this.stopWords = s.getStopWords();        
    }

    public void indexing(String path) throws FileNotFoundException {
        

        for (String s : f.listFiles(path)) {
            String nameDoc = f.pathToTitle(s).replaceAll(" ", "");
            System.out.println("Processing File: " + s);

            String contentFile = f.getContentFile(s).toString();
            StringTokenizer terms = new StringTokenizer(contentFile);

            while (terms.hasMoreTokens()) {

                String term = terms.nextToken().trim();
                ArrayList<Documents> docs;

                if (!stopWords.contains(term)) {
                    if (vocabulary.containsKey(term)) {
                        int numDocs = vocabulary.get(term).size() - 1;
                        if (vocabulary.get(term).get(numDocs).getName().equals(nameDoc)) {
                            double freq = vocabulary.get(term).get(numDocs).getFrqTF() + 1;
                            vocabulary.get(term).get(numDocs).setFrqTF(freq);
                        } else {
                            vocabulary.get(term).add(new Documents(nameDoc));
                        }
                    } else {
                        docs = new ArrayList<>();
                        docs.add(new Documents(nameDoc));
                        vocabulary.put(term, docs);
                    }
                }
            }

            totalDocs += 1;
        }
        
        f = null;
        stopWords.clear();
    }

    public HashMap<String, ArrayList<Documents>> getVocabulary() {
        return vocabulary;
    }

    public static int getTotalDocs() {
        return totalDocs;
    }

       

}
