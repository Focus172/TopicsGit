import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Index {
	
	private HashMap<String, String> filePaths = new HashMap<String, String>();
	
	public Index () {
		
		System.out.println("starting");
		File f1 = new File("/Honors Topics Git/objects");
		
		boolean bool = f1.mkdirs();
		
		if (bool) {
			System.out.println("finished");
	
		}
		else {
			System.out.println("failed");
		}
		
		Path filePathToWrite = Paths.get("./objects/index");
		try {
			Files.writeString(filePathToWrite, "", StandardCharsets.ISO_8859_1);
		}
		catch (IOException exception) {
			System.out.println("Write failed");
		}
	}
	
	
	

}
