package code;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;    

public class Commit {
	
	private Tree pTree;
	
	private String summary;
	private String author;
	private String date;
	private Commit nextCommit = null;
	private Commit prevCommit = null;
	public String fileName;
	
	public Commit(String summary, String author, Commit parent) {
		this.date = getDate();
		this.summary = summary;
		this.pTree = makeTree();
		writeToFile();
	}
	
	public String getDate() {
		LocalDateTime currentDateTime = LocalDateTime.now();  
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String stringDate = currentDateTime.format(formatter);
		return stringDate;
	}
	
	public Tree makeTree() {
		ArrayList<String> aL = new ArrayList<String>();
		
		//aL.add(thing);
		//add all relvant files and trees
		
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
		Path filePathToWrite = Paths.get(filePath);
		
		try { Files.writeString(filePathToWrite, content, StandardCharsets.ISO_8859_1); }
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public void setPrevious(Commit newPrevious) { prevCommit = newPrevious; }
	
	public void setNext(Commit newNext) { nextCommit = newNext; }

}
