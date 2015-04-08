/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation;

import Common.ReadFiles;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author joaoantoniosilva
 */
public class Batch {

    double[] average = new double[11];
    Evaluation e = new Evaluation();

    public void executeAllQuerys() throws FileNotFoundException {

        ReadFiles f = new ReadFiles();

        String[] querys = f.getContentFile("querysEvaluation.txt").toString().split("\n");
        String[] setsDocs = f.getContentFile("setDocuments.txt").toString().split("\n");

        for (int i = 0; i < querys.length; i++) {
            System.out.println("processing query " + i);
            String query = querys[i].replaceAll("\\s+", " ");
            Double[] evaluationQuery = e.evaluationQuery(query, setsDocs[i]);
            this.sun(evaluationQuery);
        }
        this.printAverage();
    }

    
    public void printAverage() {
        for (int i = 0; i < average.length; i++) {
            System.out.println("Rev:" + i * 10 + " Prec: " + average[i] / 100);
        }
    }


    private void sun(Double[] evaluationQuery) {
        for (int i = 0; i < evaluationQuery.length; i++) {
            average[i] += evaluationQuery[i];
            
        }
    }

}
