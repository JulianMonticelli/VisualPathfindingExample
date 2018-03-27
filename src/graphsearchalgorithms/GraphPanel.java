/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package graphsearchalgorithms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author Julian
 */
class GraphPanel extends JPanel {
    
    private static final int BLOCK_BUTTON = 0xFC; // ABSOLUTELY arbitrary
    
    protected static final long DELAY = 25L;
    
    GSAPanel mainPanelReference;
    
    Random rand;
    
    public static final int DEFAULT_CELL_SIZE = 15;
    
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    
    public int cellSize;
    
    GraphNode[][] nodes;
    
    int currentStartTileX = -1;
    int currentStartTileY = -1;

    int currentEndTileX = -1;
    int currentEndTileY = -1;
    
    volatile boolean running = false;
    
    public GraphPanel(GSAPanel panelReference) {
        
        mainPanelReference = panelReference;
        
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        
        this.setFocusable(true);
        
        this.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               int mouseButton = e.getButton();
               if (SwingUtilities.isMiddleMouseButton(e)) {
                   mouseButton = BLOCK_BUTTON;
               }
               mainPanelReference.onClick(e.getX(), e.getY(), mouseButton);
           }
        });
        
        this.setBackground(Color.BLACK);
        
        cellSize = DEFAULT_CELL_SIZE;

        initializeNodes(cellSize);
        
        rand = new Random();
        
    }
    
    public void initializeNodes(int cellSize) {
        nodes = new GraphNode[WIDTH / cellSize][HEIGHT / cellSize];
        
        for (int x = 0; x < nodes.length; x++) {
            for (int y = 0; y < nodes[x].length; y++) {
                nodes[x][y] = new GraphNode();
            }
        }
        
        for (int x = 0; x < nodes.length; x++) {
            for (int y = 0; y < nodes[x].length; y++) {
                nodes[x][y].initializeNode(nodes, x, y);
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (GraphNode[] gnArray : nodes) {
            for (GraphNode gn : gnArray) {
                gn.draw(g, cellSize);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }
    
    
    /***************************************************************************
     * Handles a click event from one of the graph panels
     * @param x x-coordinate of mouseclick
     * @param y y-coordinate of mouseclick
     * @param clickType the type of the click received as a KeyEvent integer
     */
    public void onClick(int x, int y, int clickType) {
        
        int tileX = x / cellSize;
        int tileY = y / cellSize;
        
        if (clickType == MouseEvent.BUTTON1 && !running) {
            if (currentStartTileX >= 0 && currentStartTileY >= 0) {
                if (tileX == currentStartTileX && tileY == currentStartTileY) {
                    currentStartTileX = -1;
                    currentStartTileY = -1;
                    nodes[tileX][tileY].updateState(GraphNode.UNSEARCHED);
                }
                else {
                    nodes[currentStartTileX][currentStartTileY].updateState(GraphNode.UNSEARCHED);
                    nodes[tileX][tileY].updateState(GraphNode.START);
                    currentStartTileX = tileX;
                    currentStartTileY = tileY;
                }
                
                // Deal with overwriting end tiles
                if (tileX == currentEndTileX && tileY == currentEndTileY) {
                    currentEndTileX = -1;
                    currentEndTileY = -1;
                }
            }
            else {
                nodes[tileX][tileY].updateState(GraphNode.START);
                currentStartTileX = tileX;
                currentStartTileY = tileY;
            }
        }
        
        else if (clickType == MouseEvent.BUTTON3 && !running) {
            if (currentEndTileX >= 0 && currentEndTileY >= 0) {
                if (tileX == currentEndTileX && tileY == currentEndTileY) {
                    currentEndTileX = -1;
                    currentEndTileY = -1;
                    nodes[tileX][tileY].updateState(GraphNode.UNSEARCHED);
                }
                else {
                    nodes[currentEndTileX][currentEndTileY].updateState(GraphNode.UNSEARCHED);
                    nodes[tileX][tileY].updateState(GraphNode.FINISH);
                    currentEndTileX = tileX;
                    currentEndTileY = tileY;
                }
                
                // Deal with overwriting start tiles
                if (tileX == currentStartTileX && tileY == currentStartTileY) {
                    currentStartTileX = -1;
                    currentStartTileY = -1;
                }
            }
            else {
                nodes[tileX][tileY].updateState(GraphNode.FINISH);
                currentEndTileX = tileX;
                currentEndTileY = tileY;
            }
        }
        
        else if (clickType == BLOCK_BUTTON && !running) {
            if (tileX == currentEndTileX && tileY == currentEndTileY) {
                currentEndTileX = -1;
                currentEndTileY = -1;
            }
            else if (tileX == currentStartTileX && tileY == currentStartTileY) {
                currentStartTileX = -1;
                currentStartTileY = -1;
            }
            
            GraphNode gn = nodes[tileX][tileY];
            if (gn.isBlocked()) {
                gn.updateState(GraphNode.UNSEARCHED);
            } else {
                gn.updateState(GraphNode.BLOCKED);
            }

            
        }
        
        repaint();
        
    }
    
    
    
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    
    /***************************************************************************
     * Sets a given percentage of all graph nodes to be blocked.
     * @param percentage a percentage chance from 0.0 to 1.0
     */
    public void setBlockPercentage(double percentage) {
        for (GraphNode[] gnArray : nodes) {
            for (GraphNode gn : gnArray) {
                if (rand.nextFloat() <= percentage) {
                    gn.updateState(GraphNode.BLOCKED);
                }
                else {
                    gn.updateState(GraphNode.UNSEARCHED);
                }
            }
        }
    }
    
    
    public void clearPanel() {
        running = false;
        
        currentStartTileX = currentStartTileY = currentEndTileX = currentEndTileY = -1;
        
        for (GraphNode[] gnArray : nodes) {
            for (GraphNode gn : gnArray) {
                gn.updateState(GraphNode.UNSEARCHED);
            }
        }
        
        repaint();
        
    }
    
}
