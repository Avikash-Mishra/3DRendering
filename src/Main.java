import java.io.File;

import javax.swing.JFileChooser;

public class Main {
	private File file;
	
	public Main() {
		loadData();
	}

	private void loadData() {
		JFileChooser openFile = new JFileChooser();
		openFile.showOpenDialog(null);
		File folder = new File(openFile.getSelectedFile().toString());
		this.file = folder;
	}
	
	

	public static void main(String[] args) {
		new Main();
	}

}
