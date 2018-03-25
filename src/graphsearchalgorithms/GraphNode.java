/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package graphsearchalgorithms;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Julian
 */
class GraphNode {
    
    public static final int UNSEARCHED = 0;
    public static final int SEARCHED = 1;
    public static final int BLOCKED = 2;
    public static final int START = 3;
    public static final int FINISH = 4;
    public static final int PATH = 5;
    public static final int SEEN = 6;
    
    
    public GraphNode up = null;
    public GraphNode down = null;
    public GraphNode left = null;
    public GraphNode right = null;
    public GraphNode upLeft = null;
    public GraphNode upRight = null;
    public GraphNode downLeft = null;
    public GraphNode downRight = null;
    
    public int xPos;
    public int yPos;
    
    public int state;
    
    public GraphNode() {
        state = UNSEARCHED;
    }
    
    public void initializeNode(GraphNode[][] nodes, int x, int y) {
        xPos = x;
        yPos = y;
        
        // Initialize left-oriented neighbors
        if (xPos > 0) {
            left = nodes[x-1][y];
            
            if (yPos > 0) {
                upLeft = nodes[x-1][y-1];
            }
            
            if (yPos < nodes[xPos].length-1) {
                downLeft = nodes[x-1][y+1];
            }
            
        }
        
        // Initializee right-oriented neighbors
        if (xPos < nodes.length - 1) {
            right = nodes[x+1][y];
            
            if (yPos > 0) {
                upRight = nodes[x+1][y-1];
            }
            
            if (yPos < nodes[xPos].length-1) {
                downRight = nodes[x+1][y+1];
            }
        }
        
        // Initialize up and down neighbors
        if (yPos > 0) {
            up = nodes[x][y-1];
        }

        if (yPos < nodes[xPos].length-1) {
            down = nodes[x][y+1];
        }
    }
    
    public LinkedList<GraphNode> getNeighborList() {
        LinkedList<GraphNode> neighborList = new LinkedList<>();
        
        if (up != null) {
            neighborList.add(up);
        }
        if (down != null) {
            neighborList.add(down);
        }
        if (left != null) {
            neighborList.add(left);
        }
        if (right != null) {
            neighborList.add(right);
        }
        if (upLeft != null) {
            neighborList.add(upLeft);
        }
        if (upRight != null) {
            neighborList.add(upRight);
        }
        if (downLeft != null) {
            neighborList.add(downLeft);
        }
        if (downRight != null) {
            neighborList.add(downRight);
        }
        
        return neighborList;
    }
    
    public void draw(Graphics g, int cellSize) {
        
        // Determine cell color
        switch (state) {
            case UNSEARCHED:
                g.setColor(Color.WHITE);
                break;
            case SEARCHED:
                g.setColor(Color.CYAN);
                break;
            case BLOCKED:
                g.setColor(Color.BLACK);
                break;
            case START:
                g.setColor(Color.RED);
                break;
            case FINISH:
                g.setColor(Color.GREEN);
                break;
            case PATH:
                g.setColor(Color.YELLOW);
                break;
            case SEEN:
                g.setColor(Color.ORANGE);
            default:
                // Do nothing ?
        }
        
        g.fillRect(xPos*cellSize, yPos*cellSize, cellSize, cellSize);
        
        // Draw cell outline
        g.setColor(Color.GRAY);
        g.drawRect(xPos*cellSize, yPos*cellSize, cellSize, cellSize);
    }
    
    public boolean isBlocked() {
        return state == BLOCKED;
    }
    
    public void updateState(int state) {
        this.state = state;
    }
    
    
}
