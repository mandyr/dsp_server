package dsp_server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileItem {
	
	private String filename;
	private String folderPath;
	private File file; 
	private DataInputStream dataInputStream;
	private boolean eofReached;
	
	public FileItem(String folderPath, String filename) {
		this.filename = filename;
		this.folderPath = folderPath;
	}
	
	public String getName() {
		return this.filename;
	}
	
	public void openFile() throws FileNotFoundException {
		// opens a file called name
		
		String filePath = folderPath + File.separator + filename;
		file = new File(filePath);
	
		try {
			dataInputStream = new DataInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw e;
		}		
	}

	public int getB() throws IOException {
		//Gets a byte from the currently open file
		int c = 0;
		try {
			c = dataInputStream.read();
			if(c==-1) eofReached = true;
		} catch (IOException e) {
			throw e;
		}
		return c;
	}
	
	public boolean closeFile() throws IOException {
	// closes the open file 
	try {
		dataInputStream.close();
		System.out.println("Original file closed");
	} catch (IOException e) {
		throw e;
	}
	    return false;
	}

}
