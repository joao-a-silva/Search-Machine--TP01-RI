/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Common;

import java.util.HashSet;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;

/**
 *
 * @author joao
 */
public class StopWords {
    
    ReadFiles f = new ReadFiles();
    
    public HashSet<String> getStopWords() throws FileNotFoundException{
        String stopsList = f.getContentFile("stopwords.txt").toString();
        
        HashSet<String> stops = new HashSet<>(); 
        
        StringTokenizer terms;
        terms = new StringTokenizer(stopsList);
        
        while (terms.hasMoreTokens()) {
            String term = terms.nextToken().trim();
            if (!stops.contains(term)){
                stops.add(term);
            }
        }         
        return stops;
    }
    
}
