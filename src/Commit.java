import static org.junit.jupiter.api.Assertions.*;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;    
import java.util.concurrent.TimeUnit;

public class Commit {
	
	private String pTree = null;
	// this boolean will be helpful later
	boolean pTreeNull = true;
	
	private String summary;
	private String author;
	private String date;
	private Commit next = null;
	private Commit previous = null;
	
	public Commit(String pTree, String summary, String author, Commit parent) {
		
		// check if pTree is null
		if (pTree != null) {
			this.pTree = pTree;
			pTreeNull = false;
		}
		
		// set the date
		this.date = getDate();
		
		this.summary = summary;
		
		
	}
	
	public String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime currentDateTime = LocalDateTime.now();  

		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String stringDate = currentDateTime.format(formatter);
		return stringDate;
	}
	
	
	public String createSha1() {
		String toSha = pTree + "\n";
		if (previous != null) {
			toSha += "objects/" + previous.toString()+ "\n";
		}
		toSha += author+ "\n";
		toSha += date+ "\n";
		toSha += summary + "\n";
		
		return GFG.encryptThisString(toSha);
	}
	
	
	public String toString() {
		return createSha1();
	}
	
	public void writeToFile() {
		String fileName = "./objects/" + createSha1();
		
		String content = pTree + "\n";
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
