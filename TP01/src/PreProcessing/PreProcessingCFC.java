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
import java.util.regex.Pattern;

/**
 *
 * @author joaoantoniosilva
 */
public class PreProcessingCFC {

    String nomeDoc;

    public void preProcessing(String path) throws FileNotFoundException, IOException {

        ReadFiles files = new ReadFiles();

        for (String file : files.listFiles(path)) {
            int init, end, initTitle, endTitle;
            
            String content = files.getContentFile(file).toString();
            String[] paper = content.split("\n\n");

            for (int i = 0; i < paper.length; i++) {
                StringBuilder out = new StringBuilder();
               
                initTitle = paper[i].indexOf("\nRN ");

                endTitle = paper[i].indexOf("\nAN ");
//                
//                System.out.println(paper[i].substring(initTitle, endTitle));
                nomeDoc = paper[i].substring(initTitle+3, endTitle).replaceAll("\\.", "");
                System.out.println(nomeDoc);
                if (paper[i].indexOf("\nAU ") > 0) {
                    init = paper[i].indexOf("\nAU ");
                } else {
                    init = paper[i].indexOf("\nTI ");
                }
//                System.out.println(init);
                if (paper[i].indexOf("\nRF ") > 0) {
                    end = paper[i].indexOf("\nRF ");
                } else {
                    end = paper[i].lastIndexOf(".");
                }
//                System.out.println(end);
                out.append(paper[i].substring(init, end));

                files.writeFile("docs/" + nomeDoc + ".txt", out.toString().replaceAll("\nAU |\nTI |\nSO |\nMJ |\nMN |\nAB |\nEX ", " " ));
            }

        }

    }

}
