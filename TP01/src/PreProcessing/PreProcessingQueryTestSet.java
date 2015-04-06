/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PreProcessing;

import Common.ReadFiles;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author joao
 */
public class PreProcessingQueryTestSet {

    public void preProcessing(String path) throws FileNotFoundException, IOException {

        ReadFiles files = new ReadFiles();

        String content = files.getContentFile(path).toString();
        String[] querys = content.split("QN");
        StringBuilder outQuerys = new StringBuilder();
        StringBuilder outDocsOcurr = new StringBuilder();

        for (int i = 1; i < querys.length; i++) {
            
            int initRD, end, initQuery, endQuery;
            
            initQuery = querys[i].indexOf("\nQU ");
            endQuery = querys[i].indexOf("\nNR ");
//            System.out.println((querys[i].substring(initQuery+3,endQuery)));
            
            outQuerys.append(querys[i].substring(initQuery+3,endQuery).replaceAll("\n", "").replace("\\s+", " ")).append("\n");
            
            initRD = querys[i].indexOf("\nRD ");
            
            outDocsOcurr.append(generateSetofDocuments(querys[i].substring(initRD+3)));
            
        }
        
        files.writeFile("querysEvaluation.txt", outQuerys.toString());
        files.writeFile("setDocuments.txt", outDocsOcurr.toString());
    }
    
    private String generateSetofDocuments(String s){
        StringBuilder aux = new StringBuilder();
        
        String auxS = s.replaceAll("\\s+", " ");
        String [] numDocs = auxS.split(" ");
        
        for (int i = 1; i < numDocs.length -1; i+=2) {
            aux.append(numDocs[i]).append(",");            
        }
        aux.append("\n");
        
        return aux.toString() ;
    }

}
