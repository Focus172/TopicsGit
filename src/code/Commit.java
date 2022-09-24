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
	private Commit next = null;
	private Commit previous = null;
	
	public Commit(String summary, String author, Commit parent) {
		
		this.date = getDate();
		this.summary = summary;
		
		this.pTree = makeTree();
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
		
		return new Tree(aL);
	}
	
	
	public String createSha1() {
		String toSha = "";
		if (pTree == null) {
			toSha = "\n";
		} else {
			toSha = pTree + "\n";
		}
		
		
		if (previous != null) {
			toSha += "objects/" + previous.toString()+ "\n";
		}
		toSha += author + "\n";
		toSha += date + "\n";
		toSha += summary + "\n";
		
		return GFG.encryptThisString(toSha);
	}
	
	
	public String toString() {
		return createSha1();
	}
	
	public void writeToFile() {
		String fileName = "./objects/" + createSha1();
		
		String content = "";
		if (pTree == null) {
			content = "\n";
		} else {
			content = pTree + "\n";
		}
		
		if (previous != null) {
			content += "objects/" + previous.toString()+ "\n";
		}
		if (next != null) {
			content += "objects/" + next.toString()+ "\n";
		}
		content += author+ "\n";
		content += date+ "\n";
		content += summary + "\n";
		
		Path filePathToWrite = Paths.get(fileName);
		try {
			Files.writeString(filePathToWrite, content, StandardCharsets.ISO_8859_1);
		}
		catch (IOException exception) {
			System.out.println("Write failed");
		}
	}
	
	// I wasn't sure if we were supposed to write these methods... 
	public void setPrevious(Commit newPrevious) {
		previous = newPrevious;
	}
	
	public void setNext(Commit newNext) {
		next = newNext;
	}
	
}
