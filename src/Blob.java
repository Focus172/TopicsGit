import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Blob {

	private String content;
	private String sha;
	
	public Blob (String filePath) throws IOException {
		
		this.content = "";
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		while (reader.ready()) {
			content += (char)reader.read();
		}
		
		sha = GFG.encryptThisString(content);
	
		System.out.print("The content of the file is: ");
		System.out.println(content);
		System.out.println("\n\n");
		System.out.print("The SHA1 hash is: ");
		System.out.println(sha);
		
		
		String fileName = "./objects/" + sha;
		
		
		
		Path filePathToWrite = Paths.get(fileName);
		try {
			Files.writeString(filePathToWrite, content, StandardCharsets.ISO_8859_1);
		}
		catch (IOException exception) {
			System.out.println("Write failed");
		}
	}
}
