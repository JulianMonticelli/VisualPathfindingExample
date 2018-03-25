/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */
package graphsearchalgorithms;

import javax.swing.JFrame;

/**
 *
 * @author Julian
 */
public class GraphSearchAlgorithms extends JFrame {

    
    GSAPanel gsaPanel;
    
    public GraphSearchAlgorithms() {
        this.setTitle("Graph Search Algorithm Simulator (Pathfinding) by Julian Monticelli");
        
        
        gsaPanel = new GSAPanel();
        this.add(gsaPanel);
        
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.pack();
        this.setVisible(true);
        
    }
    
    
    
    public void start() {
        
    }
    
    public static void main(String[] args) {
         GraphSearchAlgorithms gsa = new GraphSearchAlgorithms();
         gsa.start();
    }
    
}
