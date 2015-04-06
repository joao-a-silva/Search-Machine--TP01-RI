/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ranking;

import Common.ReadFiles;
import Common.StopWords;
import Indexing.Documents;
import Indexing.Indexer;
import Common.Weight;
import java.io.FileNotFoundException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 *
 * @author joao
 */
public class Ranking {

    HashMap<String, Double> queryVocabulary;
    Weight w;
    HashSet<String> stopWords;
    double[] weightQueryVector;
    TreeSet<Results> ranking = new TreeSet<Results>();

    public Ranking() throws FileNotFoundException {
        w = new Weight();
        this.queryVocabulary = new HashMap<>();
        StopWords s = new StopWords();
        this.stopWords = s.getStopWords();
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
        weightQueryVector = getVector();

        Set<String> keys = queryVocabulary.keySet();

        for (String key : keys) {
//            System.out.println("");
            Set<String> termsVoc = Indexer.vocabulary.keySet();
            int positionTerm = 0;

            for (String term : termsVoc) {
                if (term.equals(key)) {
                    double idf = w.getIDF(Indexer.getTotalDocs(), Indexer.vocabulary.get(key).size());
                    weightQueryVector[positionTerm] = w.getTFIDF(queryVocabulary.get(key), idf);
                }
                positionTerm++;
            }
        }
    }

    public TreeSet<Results> generateRank() throws FileNotFoundException {
        ReadFiles f = new ReadFiles();

        String content = f.getContentFile("weightsDocs.txt").toString();
        String[] linhas = content.split("\n");

        for (int i = 0; i < linhas.length; i++) {
            double rank = 0.0;

            String[] weightDocs = linhas[i].split(",");

            for (int j = 0; j < weightQueryVector.length; j++) {
                rank = rank + weightQueryVector[j] * Double.parseDouble(weightDocs[j + 1]);
            }

            double aux = rank / Double.parseDouble(weightDocs[weightDocs.length - 1]);

            ranking.add(new Results(weightDocs[0], aux));
        }
        return ranking;

    }

    public String prinRank() {
        StringBuilder out = new StringBuilder();

        Iterator<Results> iterator = ranking.descendingIterator();
        while (iterator.hasNext()) {
            Results d = iterator.next();
            out.append( d.nameArq + ",");

        }
        return out.toString();
    }

    private double[] getVector() {
        double[] vector = new double[Indexer.vocabulary.size()];

        for (int i = 0; i < vector.length; i++) {
            vector[i] = 0.0;
        }
        return vector;
    }

}
