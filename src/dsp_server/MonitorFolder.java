// Server version of MonitorFolder

package dsp_server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class MonitorFolder implements ReadFolders {

	private String folderPath = "H:\\DistributedSysProg\\dsp_server\\folderRemote";
	
	private ArrayList<File> filesList = null;
	private ArrayList<FileItem> movingFiles = null;
	
	private boolean contentsOfFolderChanged = false;
	private boolean eofReached;
	
	public MonitorFolder() {
		filesList = new ArrayList<File>();
		movingFiles = new ArrayList<FileItem>();
	}
	
	@Override
	public boolean checkBool() {
		return false;
	}
	
	public boolean checkBoolEOF() {
		return eofReached;
	}
	
	public boolean checkBoolFolder() {
		return contentsOfFolderChanged;
	}

	@Override
	public String[] getNames() {
		// returns the names of all the files in folder1
		File[] files = new File(folderPath).listFiles();
		String[] result = new String[files.length];
			
		int counter = 0;
			for(int i=0;i< files.length;i++) {
				File file = files[i];
				if(file.isFile()) {
					filesList.add(file);
					result[counter] = file.getName();
					counter++;
				}	
			}
			
		return result;

	}

	@Override
	public boolean openFile(String name) {
		// opens a file called name
		
		FileItem newFileItem = new FileItem(folderPath, name);
		movingFiles.add(newFileItem);
		
		try {
			newFileItem.openFile();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public byte getB(String name) {
		//Gets a byte from the currently open file
		int c = 0;
		try {
			c = getFileItemByName(name).getB();
		    if (c== -1) eofReached = true;
		} catch (IOException e) {
		 e.printStackTrace();
		}
	    return (byte)c;
	}

	@Override
	public boolean closeFile(String name) {
		// closes the open file 
		try {
			getFileItemByName(name).closeFile();
			System.out.println("Original file closed");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkForChange() {
		// sets a bool if a file has been added
		boolean result = false;

		File[] listOfFiles = new File(folderPath).listFiles();
		  for (File fileEntry : listOfFiles) {
		    if (!fileContainedInOldList(fileEntry)) {
		   		filesList.add(fileEntry);
		   		result = true;
		       }
		  }
			    
		try {
		 Thread.sleep(Configuration.getInstance().getCheckInterval());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			    
		return result;
	}
	
	private boolean fileContainedInOldList(File file) {
		boolean result = false;
		for(File f : filesList) {
			if(f.getName().equals(file.getName())) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	private FileItem getFileItemByName(String name) {
		for(FileItem fileItem : movingFiles){
			if(fileItem.getName().equals(name)) {
				return fileItem;
			}
		}
		return null;
		
	}
	
}
