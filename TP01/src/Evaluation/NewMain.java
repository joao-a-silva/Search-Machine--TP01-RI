/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Evaluation;

import Ranking.Results;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author joao
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        TreeSet<Results> res = new TreeSet<Results>();
        res.add(new Results("d123", 1));
        res.add(new Results("d84", 2));
        res.add(new Results("d56", 3));
        res.add(new Results("d6", 4));
        res.add(new Results("d8", 5));
        res.add(new Results("d9", 6));
        res.add(new Results("d511", 7));
        res.add(new Results("d129", 8));
        res.add(new Results("d187", 9));
        res.add(new Results("d25", 10));
        res.add(new Results("d38", 11));
        res.add(new Results("d48", 12));
        res.add(new Results("d250", 13));
        res.add(new Results("d113", 14));
        res.add(new Results("d3", 15));
        
        Set<String> relevant = new HashSet<String>();
        relevant.add("d3");
        relevant.add("d5");
        relevant.add("d9");
        relevant.add("d25");
        relevant.add("d39");
        relevant.add("d44");
        relevant.add("d56");
        relevant.add("d71");
        relevant.add("d89");
        relevant.add("d123");
        
        Evaluation e = new Evaluation();
        
        e.evaluationquery(res, relevant);
        
        TreeSet<Results> res2 = new TreeSet<Results>();
        res2.add(new Results("d425", 1));
        res2.add(new Results("d87", 2));
        res2.add(new Results("d56", 3));
        res2.add(new Results("d32", 4));
        res2.add(new Results("d124", 5));
        res2.add(new Results("d615", 6));
        res2.add(new Results("d512", 7));
        res2.add(new Results("d129", 8));
        res2.add(new Results("d4", 9));
        res2.add(new Results("d130", 10));
        res2.add(new Results("d193", 11));
        res2.add(new Results("d715", 12));
        res2.add(new Results("d810", 13));
        res2.add(new Results("d5", 14));
        res2.add(new Results("d3", 15));
        
        Set<String> relevant2 = new HashSet<String>();
        relevant2.add("d3");
        relevant2.add("d56");
        relevant2.add("d129");
        System.out.println("\n\n");
        
        e.evaluationquery(res2, relevant2);
                
    }
    
}
