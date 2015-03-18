/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PreProcessing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;

/**
 *
 * @author joao
 */
public class PreProcessing {

    String content;
    int numDocs = 0;

    public void preProcessing(String path) throws FileNotFoundException, IOException {

        ReadFiles files = new ReadFiles();
        StringBuilder out = new StringBuilder();

        for (String file : files.listFiles(path)) {
            numDocs++;
            content = files.getContentFile(file).toString().toLowerCase();
            //read stop words and replace then; 
            removeStopWords();
            //remove special characters
            removeSpecialCharacters();
            
            files.writeFile("docsToIndex/doc"+numDocs+".txt", content);
        }

    }

    private void removeStopWords() throws FileNotFoundException {
        ReadFiles stop = new ReadFiles();

        String[] stopWords = stop.getContentFile("stopwords.txt").toString().split("\n");

        for (String stopsW1 : stopWords) {
            System.out.println(stopsW1);
//            System.out.println("["+stopsW1+"]");
            stopsW1 = " "+ stopsW1+ " ";
            content = content.replaceAll(stopsW1, " ");
//            System.out.println(content);
        }
    }

    private void removeSpecialCharacters() {
        // remove special characters
        String text = Normalizer.normalize(content, Normalizer.Form.NFD);
        text = text.replaceAll("[^\\p{ASCII}]", "");        

        // remove pontuation
        content = text.replaceAll("\\p{Punct}", " ");
        content = content.replaceAll("\\s+"," ");
        System.out.println(content);
    }

}
