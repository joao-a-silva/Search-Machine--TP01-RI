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

    String content, titleDoc;

    public void preProcessing(String path) throws FileNotFoundException, IOException {
        ReadFiles files = new ReadFiles();
        StringBuilder out = new StringBuilder();

        for (String file : files.listFiles(path)) {
            titleDoc = files.pathToTitle(file);
            //read content file
            content = files.getContentFile(file).toString().toLowerCase();
            //remove special characters
            removeSpecialCharacters();
            //read stop words and replace then; 
            removeStopWords();

            files.writeFile("docsToIndex/" + titleDoc, content);
        }

    }

    private void removeStopWords() throws FileNotFoundException {
        ReadFiles stop = new ReadFiles();
        String[] stopWords = stop.getContentFile("stopwords.txt").toString().split("\n");
        //for each stop word, remove it from content file
        for (String stopsW1 : stopWords) {//    
            content = content.replaceAll(stopsW1, " ");
        }
    }

    private void removeSpecialCharacters() {
        // remove special characters
        String text = Normalizer.normalize(content, Normalizer.Form.NFD);
        text = text.replaceAll("[^\\p{ASCII}]", "");
        // remove pontuation
        content = text.replaceAll("\\p{Punct}", " ");
        content = content.replaceAll("\\s+", " ");

    }

    

}
