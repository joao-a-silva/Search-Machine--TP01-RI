/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indexing;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 *
 * @author joaoantoniosilva
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String text = "João Antonio da Silva?\t;'";
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = text.replaceAll("[^\\p{ASCII}]", "");
        System.out.println(text);
        
        String temp = Normalizer.normalize(text, java.text.Normalizer.Form.NFD);
        System.out.println(temp.replaceAll("\\p{Punct}",""));
//        System.out.println(Normalizer.normalize(temp, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));  
//        System.out.println( temp.replaceAll("[^\\p{ASCII}]",""));


    }

}
