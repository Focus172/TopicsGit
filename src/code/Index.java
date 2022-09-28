package code;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class Index {
	
	public HashMap<String, Blob> filePaths = new HashMap<String, Blob>();
	
	private String indexText = "";
	
	public Index () {
		init();
	}
	
	public void init() {
		
		// Create directory for objects folder
		new File("./objects").mkdirs();
		
		// make index file
		GitUtils.makeFile("./index", "");
	}
	
	public void add(String fileName) {
		
		// make the new blob if it doesnt already exist
		Blob b = null;
		if (filePaths.containsKey(fileName)) {
			b = filePaths.get(fileName);
		} else { 
			b = new Blob(fileName);
			filePaths.put(fileName, b);
		}
		
		//adds index text
		indexText += "blob : " + fileName + " " + b.sha;
		
		// rewrite the index
		rewriteIndex();

	}
	
	public void remove(String fileName) {
	    
	    // delete the file by recording it index
	    Blob b = filePaths.get(fileName);
	    
	    //records change in index file
	    indexText += fileName + " : " + b.sha;

	 	// rewrite the index
	 	rewriteIndex(); //this can be made better as there is no need for rewriting now, only appenbding and clearing
	 	
	}
	
	public void clearIndex() {
		indexText = "";
		rewriteIndex();
	}
	
	private void rewriteIndex () {
		
		//name.substring(0, name.indexOf(':')) + " : " + filePaths.get(name).sha +name.substring(name.indexOf(':')+1) + "\n";
		
		new File ("index").delete();
		GitUtils.makeFile("index", indexText);
	}

}
