/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ranking;

import Common.StopWords;
import Indexing.Documents;
import Indexing.Indexer;
import Indexing.Weight;
import java.io.FileNotFoundException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 *
 * @author joao
 */
public class Ranking {

    HashMap<String, double[]> weightsDocsVectors;
    HashMap<String, Double> queryVocabulary;
    HashMap<String, Integer> positionTerms;
    TreeSet<Documents> rank;
    Weight w;
    HashSet<String> stopWords;
    double[] weightQueryVector;

    public Ranking() throws FileNotFoundException {
        w = new Weight();
        rank = new TreeSet<>();
        this.queryVocabulary = new HashMap<>();
        this.weightsDocsVectors = new HashMap<>();
        weightQueryVector = this.getVector();
        this.positionTerms = new HashMap<>();
        StopWords s = new StopWords();
        this.stopWords = s.getStopWords();
    }

    public void generateWeightsDocs() {
        int numTerms = 0;

        Set<String> terms = Indexer.vocabulary.keySet();

        ArrayList<Documents> documentsPerTerm;
        //For each term of the vocabulary
        for (String termVoc : terms) {
            //if term != null, get the docs tf-idf wights to this term
            if (termVoc != null) {
                positionTerms.put(termVoc, numTerms);
//                System.out.println("key: " + termVoc + " Doc: ");
                documentsPerTerm = Indexer.vocabulary.get(termVoc);
//                System.out.println("Termo: " + termVoc);

                for (int i = 0; i < documentsPerTerm.size(); i++) {
                    //for each document associate with term, calculates the TF and
                    // save it
                    Documents d = documentsPerTerm.get(i);
                    d.setFrqTF(w.getTF(d.getFrqTF()));
                    // calculates the IDF, the TF-IDF and save in the vector weigths
                    double idf = w.getIDF(Indexer.getTotalDocs(), documentsPerTerm.size());
                    double tfidf = w.getTFIDF(d.getFrqTF(), idf);
                    if (weightsDocsVectors.containsKey(d.getName())) {
                        weightsDocsVectors.get(d.getName())[numTerms] = tfidf;
                    } else {
                        weightsDocsVectors.put(d.getName(), this.getVector());
                        weightsDocsVectors.get(d.getName())[numTerms] = tfidf;
                    }
                }
            }
            numTerms++;
        }

//        Set<String> docs = weightsDocsVectors.keySet();
//        for (String key : docs) {
//            if (key != null) {
//                double[] aux2 = weightsDocsVectors.get(key);
//                System.out.println("Doc -> " + key);
//                for (int i = 0; i < aux2.length; i++) {
//                    System.out.print("  " + aux2[i]);
//                }
//                System.out.println("\n");
//            }
//        }
        System.out.println("");
    }

    public void generateWeightsQuery(String query) {
        StringTokenizer terms = new StringTokenizer(query);

        while (terms.hasMoreTokens()) {
            String term = terms.nextToken().trim().toLowerCase();
            
            String auxTerm = Normalizer.normalize(term, Normalizer.Form.NFD);
            term = auxTerm.replaceAll("[^\\p{ASCII}]", "");
        // remove pontuation
            auxTerm = term.replaceAll("\\p{Punct}", " ");
            term = auxTerm.replaceAll("\\s+", " ");

            if (!stopWords.contains(term)) {
                if (queryVocabulary.containsKey(term)) {
                    double freq = queryVocabulary.get(term);
                    queryVocabulary.put(term, freq + 1.0);
                } else {
                    queryVocabulary.put(term, 1.0);
                }
            }

        }

        Set<String> keys = queryVocabulary.keySet();

        for (String key : keys) {
//            System.out.println("");
            if (key != null) {
                if (Indexer.vocabulary.containsKey(key)) {
                    double idf = w.getIDF(Indexer.getTotalDocs(), Indexer.vocabulary.get(key).size());
                    weightQueryVector[positionTerms.get(key)] = w.getTFIDF(queryVocabulary.get(key), idf);
                }

            }

        }

//        for (int i = 0; i < weightQueryVector.length; i++) {
//            System.out.println("query vecto:"+ weightQueryVector[i]);
//            
//        }
//        System.out.println("");
    }

    public void generateRank() {
        Set<String> docs = weightsDocsVectors.keySet();
        ArrayList<Documents> rankDocs = new ArrayList<>();
        double[] auxD;

        for (String doc : docs) {
            if (doc != null) {

                auxD = weightsDocsVectors.get(doc);
                double vetorialProduct = 0, sumDoc = 0;

                for (int i = 0; i < auxD.length; i++) {
                    vetorialProduct += auxD[i] * weightQueryVector[i];
                    sumDoc += Math.pow(auxD[i], 2);
                }

                double similarity = vetorialProduct / Math.sqrt(sumDoc);
                rankDocs.add(new Documents(doc, similarity));
            }
        }
        System.out.println("");
        for (int i = 0; i < rankDocs.size(); i++) {
            System.out.println(rankDocs.get(i).getName() + " --> " + rankDocs.get(i).getFrqTF());

        }
    }

    public void prinRank() {

//        Iterator<Documents> iterator = rank.iterator();
//        while (iterator.hasNext()) {
//            Documents d = iterator.next();
//            System.out.println("Doc:" + d.getName() + "\tRank:" + d.getFrqTF());
//        }
    }

    private double[] getVector() {
        double[] vector = new double[Indexer.vocabulary.size()];

        for (int i = 0; i < vector.length; i++) {
            vector[i] = 0.0;
        }
        return vector;
    }
    
    

}
