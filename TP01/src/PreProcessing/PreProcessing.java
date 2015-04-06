/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PreProcessing;

import Common.ReadFiles;
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
    ReadFiles stop = new ReadFiles();
    
    public void preProcessing(String path) throws FileNotFoundException, IOException {
        ReadFiles files = new ReadFiles();        
        int numFile = 0;
        for (String file : files.listFiles(path)) {
            titleDoc = files.pathToTitle(file).replace(".txt", "").replaceAll("\\s+", "");
            int auxTitleDoc = Integer.parseInt(titleDoc);
            titleDoc = auxTitleDoc+".txt";
//            System.out.println(titleDoc);
            
            
            //read content file
            content = files.getContentFile(file).toString().toLowerCase();
            //remove special characters
            removeSpecialCharacters();
            removeHTMLTags();
            files.writeFile("docsToIndex/" + titleDoc, content);
            numFile++;
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

    private void removeHTMLTags() {
       content = content.replaceAll("\\<.*?>","");
    }

}
