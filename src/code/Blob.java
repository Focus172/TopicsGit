package code;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Blob {

	private String content = "";
	public String sha;
	
	public Blob (String filePath) {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath)); 
			while (reader.ready()) { content += (char)reader.read(); }
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		sha = GitUtils.toSha(content);
	
		/*
		System.out.print("The content of the file is: ");
		System.out.println(content);
		System.out.println("\n\n");
		System.out.print("The SHA1 hash is: ");
		System.out.println(sha);
		*/
		
		String fileName = "./objects/" + sha;
		GitUtils.makeFile(fileName, content);
		
	}
	
}
