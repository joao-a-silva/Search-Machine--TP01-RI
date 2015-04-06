/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ranking;

/**
 *
 * @author joao
 */
public class Results implements Comparable<Results>{
    
    String nameArq;
    double rank;

    public Results(String nameArq, double rank) {
        this.nameArq = nameArq;
        this.rank = rank;
    }    
    
    @Override
    public int compareTo(Results o) {
        
        if (this.rank > o.rank) {
            return 1;
        }
        if (this.rank < o.rank) {
            return -1;
        }
        return 0;
         
    }

    public String getNameArq() {
        return nameArq;
    }

    public void setNameArq(String nameArq) {
        this.nameArq = nameArq;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }
    
    
    
}
