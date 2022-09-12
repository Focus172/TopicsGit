import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Index {
	
	private HashMap<String, Blob> filePaths = new HashMap<String, Blob>();
	
	public Index () {
		
		// Create directory for objects folder
		File f1 = new File("/Honors Topics Git/objects");
		
		boolean bool = f1.mkdirs();
		
		// print statements for debugging
		if (bool) {
			System.out.println("folder created!");
	
		}
		else {
			System.out.println("failed");
		}
		
		// make index file
		Path filePathToWrite = Paths.get("./objects/index");
		try {
			Files.writeString(filePathToWrite, "", StandardCharsets.ISO_8859_1);
		}
		catch (IOException exception) {
			System.out.println("Write failed");
		}
	}
	
	public void addBlob(String fileName) throws IOException {
		
		// make the new blob
		Blob newBlob = new Blob(fileName);
		
		String sha = newBlob.getSha();
		
		// add the key/value pair of the original file name and the sha1 string to our dictionary
		filePaths.put(fileName, newBlob);
		
		FileWriter myWriter = new FileWriter("./objects/index");
		myWriter.write(fileName + " -> " + newBlob.getSha());
		myWriter.close();
		
	}
	
	
	

}
