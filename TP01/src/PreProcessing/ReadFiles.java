/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PreProcessing;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author joao
 */
public class ReadFiles {

    public ArrayList<String> listFiles(String path) throws FileNotFoundException {
        ArrayList<String> listFiles = new ArrayList<>();
        File dir = new File(path);
        File[] files = dir.listFiles();
        String exit = "";
        if (files != null) {
            int length = files.length;

            for (int i = 0; i < length; ++i) {
                File f = files[i];
                exit += f.getName() + ", ";
                listFiles.add(f.getAbsolutePath());
            }
        }
        for (String f : listFiles) {
            System.out.println(f);            
        }
        
        return listFiles;

    }

    public StringBuilder getContentFile(String path) throws FileNotFoundException {
        StringBuilder out = new StringBuilder();

        try {
            FileReader arq = new FileReader(path);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            while (linha != null) {
                out.append(linha).append("\n");
                linha = lerArq.readLine();
            }
            
            arq.close();
        } catch (IOException e) {
             System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        return out;
    }

}
