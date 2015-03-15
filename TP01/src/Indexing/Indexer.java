/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Indexing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import jdk.nashorn.internal.parser.Token;
import PreProcessing.*;
import java.io.FileNotFoundException;

/**
 *
 * @author joao
 */
public class Indexer {
    
    
   HashMap<Token, ArrayList<Documents>> index = new HashMap<>();
  
  
  public void indexing(String path) throws FileNotFoundException{
      
      ReadFiles files = new ReadFiles();
      
      for (String s : files.listFiles(path)){
          StringBuilder contentFile = files.getContentFile(s);
          
          StringTokenizer tokens = new StringTokenizer(contentFile.toString());
          
          while (tokens.hasMoreTokens()) {
            System.out.println(tokens.nextToken());
        }
      }
      
      
      
  }
    
    
    
    
}
