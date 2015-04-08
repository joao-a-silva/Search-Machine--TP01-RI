/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation;

import Common.ReadFiles;
import Ranking.Ranking;
import Ranking.Results;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author joao
 */
public class Evaluation {

    Double[] recall = new Double[11];
    Double[] auxPrecision = new Double[11];

    public Double[] evaluationQuery(String q, String docs) throws FileNotFoundException {
        Ranking r = new Ranking();
        r.generateWeightsQuery(q);
        this.generateRecallPrecision(r.generateRank(), this.generateSetRelevantsDocs(docs));
        return auxPrecision;
    }

    public void generateRecallPrecision(TreeSet<Results> rank, Set<String> relevants) {
        ArrayList<Double[]> recalPrecision = new ArrayList<>();
        int sizeRank = 1;
        int sizeRelevants = relevants.size();
        double recall, precision, rINTERa = 0;
        Iterator<Results> iterator = rank.descendingIterator();

        while (iterator.hasNext()) {
            String r = iterator.next().getNameArq().replaceFirst("0*", "");

            if (relevants.contains(r)) {
                rINTERa++;
                recall = rINTERa / sizeRelevants;
                precision = rINTERa / sizeRank;
                Double[] aux = new Double[2];
                aux[0] = recall * 100;
                aux[1] = precision * 100;
                recalPrecision.add(aux);
            }
            sizeRank++;

        }
        generateInterpolation(recalPrecision);
    }

    private void generateInterpolation(ArrayList<Double[]> recalPrecision) {

        recall[0] = 0.0;
        double maxPrecision;
        int i = 0;
        int j = 1;
        for (; j < recall.length; j++) {
            maxPrecision = recalPrecision.get(i)[1];

            while (recalPrecision.get(i)[0] < j * 10.0 && i < recalPrecision.size() - 1) {
                if (recalPrecision.get(i)[1] > maxPrecision) {
                    maxPrecision = recalPrecision.get(i)[1];
                }
                i++;
            }

            if (recalPrecision.get(i)[1] > maxPrecision) {
                maxPrecision = recalPrecision.get(i)[1];
            }
            recall[j] = recalPrecision.get(i)[0];
            auxPrecision[j] = maxPrecision;

        }

        if (recalPrecision.get(recalPrecision.size() - 1)[0] < 100.0) {
            j = (int) Math.round(recalPrecision.get(recalPrecision.size() - 1)[0] / 10);
            for (int k = j; k < recall.length; k++) {
                recall[k] = k * 10.0;
                auxPrecision[k] = 0.0;
            }
        }

        auxPrecision[0] = auxPrecision[1];

    }

    private Set<String> generateSetRelevantsDocs(String string) {
        Set<String> out = new HashSet<>();
        String[] docs = string.split(",");

        for (String string1 : docs) {
            out.add(string1);
        }

        return out;
    }

}
