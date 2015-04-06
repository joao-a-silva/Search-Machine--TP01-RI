/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indexing;

import Common.Weight;
import Common.*;
import PreProcessing.*;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    Weight w;
    HashSet<String> stopWords;
    ReadFiles f;

    public Indexer() throws FileNotFoundException {
        this.vocabulary = new HashMap<>();
        this.f = new ReadFiles();
        StopWords s = new StopWords();
        this.stopWords = s.getStopWords();
        w = new Weight();

    }

    public void indexing(String path) throws FileNotFoundException, IOException {

        for (String s : f.listFiles(path)) {
            String nameDoc = f.pathToTitle(s).replace(".txt", "");
            System.out.println("Processing File: " + s);
            // read the file 
            String contentFile = f.getContentFile(s).toString();
            // tokenizer the file
            StringTokenizer terms = new StringTokenizer(contentFile);

            while (terms.hasMoreTokens()) {
                // for each term in tokens
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
        generateWeightsDocs(path);

        f = null;
        stopWords.clear();
    }

    public HashMap<String, ArrayList<Documents>> getVocabulary() {
        return vocabulary;
    }

    public static int getTotalDocs() {
        return totalDocs;
    }

    private void generateWeightsDocs(String path) throws IOException {
        StringBuilder outVectorWeights = new StringBuilder();

        for (String s : f.listFiles(path)) {

            String nameDoc = f.pathToTitle(s).replace(".txt", "");
            int numTerm = 0;
            Set<String> terms = Indexer.vocabulary.keySet();
            double[] weight = this.getVector();

            for (String term : terms) {
//                System.out.println(term);

                ArrayList<Documents> docsTerm = Indexer.vocabulary.get(term);

                for (Documents d : docsTerm) {
                    if (d.getName().equals(nameDoc)) {
                        d.setFrqTF(w.getTF(d.getFrqTF()));
                        // calculates the IDF, the TF-IDF and save in the vector weigths
                        double idf = w.getIDF(Indexer.getTotalDocs(), docsTerm.size());
                        double tfidf = w.getTFIDF(d.getFrqTF(), idf);
                        weight[numTerm] = tfidf;
                        break;                    }

                }
                numTerm++;

//                System.out.println("");

            }
            outVectorWeights.append(nameDoc + ", ");

            for (int i = 0; i < weight.length; i++) {
                outVectorWeights.append(weight[i]).append(", ");
            }
            outVectorWeights.append(this.vectorialProduct(weight)).append("\n");

        }
        f.writeFile("weightsDocs.txt", outVectorWeights.toString());

    }

    private double[] getVector() {
        double[] vector = new double[Indexer.vocabulary.size()];

        for (int i = 0; i < vector.length; i++) {
            vector[i] = 0.0;
        }
        return vector;
    }

    private double vectorialProduct(double[] v) {
        double aux = 0.0;

        for (int i = 0; i < v.length; i++) {
            aux += Math.pow(v[i], 2);
        }

        return Math.sqrt(aux);
    }

}
