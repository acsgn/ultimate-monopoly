package ui;

import javax.swing.JPanel;
import javax.swing.JButton;

public class JControls extends JPanel {

	/**
	 * Create the panel.
	 */
	public JControls() {
		setLayout(null);
		
		JButton rollDiceButton = new JButton("Roll Dice");
		rollDiceButton.setBounds(12, 854, 276, 60);
		add(rollDiceButton);
		
		JButton buildBuildingButton = new JButton("Build Building");
		buildBuildingButton.setBounds(12, 781, 276, 60);
		add(buildBuildingButton);
		
		JButton buyPropertyButton = new JButton("Buy Property");
		buyPropertyButton.setBounds(12, 708, 276, 60);
		add(buyPropertyButton);
		
		JButton acknowledgeActionButton = new JButton("Acknowledge Action");
		acknowledgeActionButton.setBounds(12, 927, 276, 60);
		add(acknowledgeActionButton);
		
		JInfoPane infoArea = new JInfoPane();
		infoArea.setBounds(12,334,276,276);
		add(infoArea);

	}
}
