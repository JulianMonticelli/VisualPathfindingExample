/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package graphsearchalgorithms;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * @author Julian
 */
public class AStarGraphPanel extends GraphPanel implements Runnable {

    public AStarGraphPanel(GSAPanel panelReference) {
        super(panelReference);
    }

    @Override
    public void run() {
        
        running = true;
        
        
        int visitedNodes = 0;
        
        if (currentStartTileX < 0 || currentEndTileX < 0) {
            running = false;
            return;
        }
        
        // make a HashSet???
        boolean visited[][] = new boolean[nodes.length][nodes[0].length];
        
        int dist[][] = new int[nodes.length][nodes[0].length];
        GraphNode path[][] = new GraphNode[nodes.length][nodes[0].length];
        
        for (int x = 0; x < nodes.length; x++) {
            for (int y = 0; y < nodes[0].length; y++) {
                dist[x][y] = Integer.MAX_VALUE;
            }
        }
        
        dist[currentStartTileX][currentStartTileY] = 0;
        
        PriorityQueue<DistancedNode> nodeQueue = new PriorityQueue<>();
        //LinkedList<GraphNode> nodeQueue = new LinkedList<>();
        
        nodeQueue.add(new DistancedNode(nodes[currentStartTileX][currentStartTileY],
                getDistanceFromEnd(currentStartTileX, currentStartTileY)));
        
        boolean found = false;
        
        while (!nodeQueue.isEmpty() && !found) {
            
            
            
            if (!running) {
                return;
            }
            
            
            // Increment visited nodes
            visitedNodes++;
            
            GraphNode currentNode = nodeQueue.poll().node;
            visited[currentNode.xPos][currentNode.yPos] = true;
            
            if (currentNode.state != GraphNode.START && currentNode.state != GraphNode.FINISH) {
                currentNode.updateState(GraphNode.SEARCHED);
            }
                
            int currentDist = dist[currentNode.xPos][currentNode.yPos];
            
            LinkedList<GraphNode> neighborList = currentNode.getNeighborList();
            
            for (GraphNode gn : neighborList) {
                
                double aStarDist = getDistanceFromEnd(gn.xPos, gn.yPos);
                
                int nodeDist = dist[gn.xPos][gn.yPos];
                
                if (nodeDist > currentDist + 1) {
                    dist[gn.xPos][gn.yPos] = currentDist + 1;
                    nodeDist = dist[gn.xPos][gn.yPos];
                    path[gn.xPos][gn.yPos] = currentNode;
                }
                
                
                if (!visited[gn.xPos][gn.yPos] && !gn.isBlocked()) {
                    if (gn.xPos != currentEndTileX || gn.yPos != currentEndTileY) {
                        gn.updateState(GraphNode.SEEN);
                    }
                    nodeQueue.add(new DistancedNode(gn, aStarDist));
                    visited[gn.xPos][gn.yPos] = true;
                }
                
                if (gn.xPos == currentEndTileX && gn.yPos == currentEndTileY) {
                    found = true;
                }
            }
            
            
            repaint();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                //
            }
        }
        
        // Update path
        GraphNode currentNode = path[currentEndTileX][currentEndTileY];
        
        
        while (found && currentNode != nodes[currentStartTileX][currentStartTileY]) {
            currentNode.updateState(GraphNode.PATH);
            currentNode = path[currentNode.xPos][currentNode.yPos];
        }
        
        repaint();
        
        running = false;
    }
    
    private double getDistanceFromEnd(int tileX, int tileY) {
        int xDiff = Math.abs(currentEndTileX - tileX);
        int yDiff = Math.abs(currentEndTileY - tileY);
        
        return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
    }

}
