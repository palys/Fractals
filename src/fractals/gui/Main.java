package fractals.gui;

import javax.swing.SwingUtilities;

import fractals.logic.PropertiesReader;

public class Main {
	
	private final static PropertiesReader props = new PropertiesReader();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				new MainWindow(props.getPropertyValue("MainWindowTitle")).createAndShow();
				
			}
			
		});
	}
}
