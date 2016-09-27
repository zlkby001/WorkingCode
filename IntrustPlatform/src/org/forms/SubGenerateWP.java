package org.forms;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class SubGenerateWP extends javax.swing.JDialog {

	/**
	* Auto-generated main method to display this JDialog
	*/
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {				
				SubGenerateWP inst = new SubGenerateWP();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	
	public SubGenerateWP() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setVisible(false);
			setResizable(false);
			getContentPane().setLayout(null);
			getContentPane().setBackground(Color.WHITE);
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			this.setTitle("\u767d\u540d\u5355\u751f\u6210\u4e2d\uff0c\u8bf7\u7a0d\u7b49...");
			//this.setModal(true);
			this.setSize(300, 5);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
