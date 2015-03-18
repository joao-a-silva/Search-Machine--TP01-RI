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
import java.util.regex.Pattern;

/**
 *
 * @author joaoantoniosilva
 */
public class PreProcessingCFC {
     int numDocs = 0;

    public void preProcessing(String path) throws FileNotFoundException, IOException {

        ReadFiles files = new ReadFiles();
       
        for (String file : files.listFiles(path)) {
                                           
            String content = files.getContentFile(file).toString();

            String[] paper = content.split("\n\n");

            for (int i = 0; i < paper.length; i++) {
                StringBuilder out = new StringBuilder();
                numDocs++;

                int init;
                int end;
                
                if (paper[i].indexOf("\nAU ")> 0) {
                    init = paper[i].indexOf("\nAU ");
                } else {
                    init = paper[i].indexOf("\nTI ");
                }
//                System.out.println(init);
                if (paper[i].indexOf("\nRF ")> 0) {
                    end = paper[i].indexOf("\nRF ");
                } else {
                    end = paper[i].lastIndexOf(".");
                }
//                System.out.println(end);
                out.append(paper[i].substring(init, end));
                
                files.writeFile("docs/doc"+numDocs+".txt",out.toString());
            }
            
        }

    }

    

}
