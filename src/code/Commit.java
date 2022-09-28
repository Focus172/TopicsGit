package code;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;    

public class Commit {
	
	private String summary;
	private String author;
	private String date;
	private Commit nextCommit = null;
	private Commit prevCommit = null;
	
	public Tree pTree;
	public String fileName;
	public String treeName;
	
	public Commit(String summary, String author, Commit parent) {
		this.date = getDate();
		this.summary = summary;
		this.pTree = makeTree();
		treeName = pTree.treeName;
		writeToFile();
		
	}
	
	public String getDate() {
		LocalDateTime currentDateTime = LocalDateTime.now();  
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String stringDate = currentDateTime.format(formatter);
		return stringDate;
	}
	
	//index looks like file : sha1
	//tree looks like type : file sha1
	
	public Tree makeTree() {
		ArrayList<String> aL = new ArrayList<String>();
		
		String content = "";
		try {
    		BufferedReader reader = new BufferedReader(new FileReader(new File (fileName))); 
    		while (reader.ready()) {
    			content = reader.readLine();
    			aL.add(content);
    		}
    		reader.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
		
		//grabs info form index file
		
		//adds data in form of 
		//type : sha1  fileName.extension
		//aL.add(thing);
		
		//clears index file
		
		//add all relevant files and trees
		
		return new Tree(aL);
	}
	
	public void writeToFile() {
		
		String content = "";
		if (pTree == null) { content = "\n"; }
		else { content = pTree + "\n"; }
		
		if (prevCommit != null) { content += "objects/" + prevCommit.fileName + "\n"; }
		if (nextCommit != null) { content += "objects/" + nextCommit.fileName + "\n"; }
		
		content += author + "\n";
		content += date + "\n";
		content += summary + "\n";
		
		fileName = GitUtils.toSha(content);
		String filePath = "./objects/" + fileName;
		
		GitUtils.makeFile(filePath, content);
		
	}
	
	public void setPrevious(Commit newPrevious) { prevCommit = newPrevious; }
	public void setNext(Commit newNext) { nextCommit = newNext; }

}
