/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package graphsearchalgorithms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Julian
 */
public class OptionsPanel extends JPanel {
    
    GSAPanel mainPanelReference;
    
    JButton startButton;
    JButton clearButton;
    
    
    public OptionsPanel(GSAPanel gsaPanelRef) {
        mainPanelReference = gsaPanelRef;
        this.setPreferredSize(new Dimension(200, 600));
        this.setLayout(new FlowLayout());
        
        
        startButton = new JButton("Start");
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanelReference.runGraphAlgorithms();
            }
        });
        
        this.add(startButton);
        
        clearButton = new JButton("Clear");
        
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanelReference.clearPanels();
            }
        });
        
        this.add(clearButton);
        
        
    }
    
    
    

}
