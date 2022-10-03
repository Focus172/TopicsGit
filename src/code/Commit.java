package code;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;    

public class Commit {
	
	private String summary;
	private String author;
	private String date;
	//private Commit nextCommit = null;
	private String nextCommit = "";
	private Commit prevCommit = null;
	
	public Tree pTree;
	public String fileName;
	public String treeName;
	
	public Commit(String summary, String author, Commit parent) {
		//doesn't check for bad input
		
		
		
		//----
		//SHA FOR NAME ONLY TAKES PARENT NOT CHILD
		//----
		
		
		
		
		this.date = getDate();
		this.summary = summary;
		this.author = author;
		
		this.pTree = makeTree();
		treeName = pTree.treeName;
		
		prevCommit = parent;
		if (prevCommit != null) { parent.setNext(this.getFileName()); }
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
		ArrayList<String> added = new ArrayList<String>();
		ArrayList<String> deleted = new ArrayList<String>();
		
		//grabs info form index file
		try {
			BufferedReader br = new BufferedReader(new FileReader("index"));
			while (br.ready()) {
				String line = br.readLine();

				if (line.substring(0, 8).equals("*deleted*")) {
					deleted.add(line);
				} else {
					added.add(line);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//adds data in form of 
		//type : sha1  fileName.extension
		
		//use the added deleted and edited list to construct final list to be passed to tree
		
		//clears index file
		GitUtils.makeFile("index", "");
		
		return new Tree(added);
	}
	
	public void writeToFile() {
		
		String content = "";
		if (pTree == null) { content = "\n"; }
		else { content = treeName + "\n"; }
		
		if (prevCommit != null) { content += "objects/" + prevCommit.fileName + "\n"; }
		else { content += "\n"; }
		if (!nextCommit.equals("")) { content += "objects/" + nextCommit + "\n"; }
		else { content += "\n"; }
		
		content += author + "\n";
		content += date + "\n";
		content += summary;
		
		fileName = getFileName();
		String filePath = "./objects/" + fileName;
		
		GitUtils.makeFile(filePath, content);
		
	}
	
	public String getFileName() {
		String content = "";
		if (pTree == null) { content = "\n"; }
		else { content = treeName + "\n"; }
		
		if (prevCommit != null) { content += "objects/" + prevCommit.fileName + "\n"; }
		else { content += "\n"; }
		
		content += author + "\n";
		content += date + "\n";
		content += summary;
		
		return GitUtils.toSha(content);
	}
	
	public void setPrevious(Commit newPrevious) { prevCommit = newPrevious; } //writeToFile();
	public void setNext(String newNext) { nextCommit = newNext; writeToFile(); } //writeToFile();

}
