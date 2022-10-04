package code;
import java.io.File;
import java.util.HashMap;

public class Index {
	
	public HashMap<String, Blob> filePaths = new HashMap<String, Blob>();
	public HashMap<String, Blob> currentIn = new HashMap<String, Blob>();
	
	public Index () {
		init();
	}
	
	public void init() {
		
		// Create directory for objects folder
		new File("objects").mkdirs();
		
		// make index file
		GitUtils.makeFile("index", "");
	}
	
	public void add(String fileName) {
		
		// make the new blob if it doesnt already exist
		
		Blob b = filePaths.get(fileName);;
		if (b == null) {
			b = new Blob(fileName);
			filePaths.put(fileName, b);
			currentIn.put(fileName, b);
		}
		
		//adds index text
		if (GitUtils.fileToString("index").equals("")) {
			//this could be parsed by deliminating over " "
			GitUtils.appendToFile("index", "blob : " + b.sha + " " + fileName);
		} else {
			GitUtils.appendToFile("index", "\nblob : " + b.sha + " " + fileName);
		}

	}
	
	public void remove(String fileName) {
	    
	    // delete the file by recording it index
		//doesn't delete from map in case it is referenced again
	    Blob b = filePaths.get(fileName);
	    currentIn.remove(fileName);
	    
	    //records change in index file
	    if (GitUtils.fileToString("index").equals("")) {
			//this could be parsed by deliminating over " "
			GitUtils.appendToFile("index", "*deleted* blob : " + b.sha + " " + fileName);
		} else {
			GitUtils.appendToFile("index", "\n*deleted* blob : " + b.sha + " " + fileName);
		}
	 	
	}
	
public void edit(String fileName) {
	    
	    Blob b = filePaths.get(fileName);
	    //i dont know if this is supposed to keep it in index
	    
	    //records change in index file
	    if (GitUtils.fileToString("index").equals("")) {
			//this could be parsed by deliminating over " "
			GitUtils.appendToFile("index", "*edited* blob : " + b.sha + " " + fileName);
		} else {
			GitUtils.appendToFile("index", "\n*edited* blob : " + b.sha + " " + fileName);
		}
	 	
	}
	
	public void clearIndex() {
		GitUtils.makeFile("index", "");
	}
}
