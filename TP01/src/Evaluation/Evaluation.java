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

    public void executeAllQuerys() throws FileNotFoundException {

        ReadFiles f = new ReadFiles();

        String[] querys = f.getContentFile("querysEvaluation.txt").toString().split("\n");
        String[] setsDocs = f.getContentFile("setDocuments.txt").toString().split("\n");

        for (int i = 0; i < querys.length; i++) {
            System.out.println("processing query " + i);
            String query = querys[i].replaceAll("\\s+", " ");
            Set<String> docs = generateSetDocs(setsDocs[i]);
            executeQuery(query, docs);
//            System.out.println( r.prinRank());
        }

    }

    public void executeQuery(String q, Set<String> docs) throws FileNotFoundException {
        Ranking r = new Ranking();
        r.generateWeightsQuery(q);
        this.evaluationquery(r.generateRank(), docs);

    }

    public void evaluationquery(TreeSet<Results> rank, Set<String> relevants) {
        ArrayList<Double[]> recalPrecision = new ArrayList<>();
        int sizeRank = 1;
        int sizeRelevants = relevants.size();
        double recall, precision, rINTERa = 0;
        Iterator<Results> iterator = rank.iterator();

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

        for (int i = 0; i < recalPrecision.size(); i++) {
            System.out.println(recalPrecision.get(i)[0]);
            System.out.println(recalPrecision.get(i)[1]);
            System.out.println("");

        }

    }

    private Set<String> generateSetDocs(String string) {
        Set<String> out = new HashSet<>();

        String[] docs = string.split(",");

        for (String string1 : docs) {
            out.add(string1);
        }

        return out;
    }

    private void generateInterpolation(ArrayList<Double[]> recalPrecision) {

        Double[][] recPrec = new Double[11][2];

        recPrec[0][0] = 0.0;
        recPrec[0][1] = recalPrecision.get(0)[1];

        if (recalPrecision.get(recalPrecision.size() - 1)[0] != 100.0) {
            int i = 0;
            for (; i < recalPrecision.size(); i++) {
                recPrec[i + 1][0] = recalPrecision.get(i)[0];
                recPrec[i + 1][1] = recalPrecision.get(i)[1];
            }
            if (i < 11) {
                i++;
                for (; i < recPrec.length; i++) {
                    recPrec[i][0] = i * 10.0;
                    recPrec[i][1] = 0.0;
                }
            }
        } else {
            int auxj = 1;
            for (int i = 0; i < recalPrecision.size(); i++) {
                
                while(auxj*10.0 <= recalPrecision.get(i)[0]){
                    recPrec[auxj][0] = auxj*10.0;
                    recPrec[auxj][1] = recalPrecision.get(i)[1];
                    auxj++;
                }
                
                
            }

        }
        
        for (int i = 0; i < recPrec.length; i++) {
            System.out.println(recPrec[i][0]+" -> "+ recPrec[i][1]);
            
        }

    }

}
