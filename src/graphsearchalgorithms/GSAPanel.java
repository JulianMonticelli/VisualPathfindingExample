/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package graphsearchalgorithms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * @author Julian
 */
public class GSAPanel extends JPanel {

    private OptionsPanel optionsPanel;
    
    private DijkstraGraphPanel dijkstraPanel;
    private AStarGraphPanel aStarPanel;
    
    Thread[] graphThreads = new Thread[2];
    
    //private DualGraphPanel dualGraphPanel;
    
    public GSAPanel() {
        this.setPreferredSize(new Dimension(1406, 600));
        this.setLayout(new BorderLayout(2, 0));
        
        optionsPanel = new OptionsPanel(this);
        dijkstraPanel = new DijkstraGraphPanel(this);
        aStarPanel = new AStarGraphPanel(this);
        
        this.add(BorderLayout.WEST, optionsPanel);
        this.add(BorderLayout.CENTER, dijkstraPanel);
        this.add(BorderLayout.EAST, aStarPanel);

//        repaint();
        
        dijkstraPanel.repaint();
        aStarPanel.repaint();
    }    
    
    /***************************************************************************
     * Sends a click event between both of the graph panels
     * @param x x-coordinate of mouseclick
     * @param y y-coordinate of mouseclick
     * @param clickType the mouse button ID
     */
    public void onClick(int x, int y, int clickType) {
        dijkstraPanel.onClick(x, y, clickType);
        aStarPanel.onClick(x, y, clickType);
    }    
    
    public void stopGraphAlgorithms() {
        dijkstraPanel.setRunning(false);
        aStarPanel.setRunning(false);
    }
    
    public void runGraphAlgorithms() {
        graphThreads[0] = new Thread(dijkstraPanel);
        graphThreads[1] = new Thread(aStarPanel);
        
        for (Thread t : graphThreads) {
            t.start();
        }
    }
    
    public void clearPanels() {
        dijkstraPanel.clearPanel();
        aStarPanel.clearPanel();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        dijkstraPanel.repaint();
        aStarPanel.repaint();
    }

}
