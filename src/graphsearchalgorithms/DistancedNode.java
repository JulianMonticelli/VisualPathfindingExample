/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package graphsearchalgorithms;

import java.util.Comparator;

/**
 * @author Julian
 */
public class DistancedNode implements Comparable<DistancedNode> {

    GraphNode node;
    double distance;
    
    public DistancedNode(GraphNode gn, double totalDistance) {
        node = gn;
        distance = totalDistance;
    }

    @Override
    public int compareTo(DistancedNode other) {
        
        if (this.distance < other.distance) {
            return -1;
        }
        else if (this.distance > other.distance) {
            return 1;
        }
        else {
            return 0;
        }
    }
    
}
