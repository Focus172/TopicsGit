import java.io.File;
import java.util.HashMap;

public class Index {
	
	private HashMap<String, String> filePaths = new HashMap<String, String>();
	
	public Index () {
		
		System.out.println("starting");
		File f1 = new File("/Honors Topics Git/testFolder");
		
		boolean bool = f1.mkdirs();
		
		if (bool) {
			System.out.println("finished");
	
		}
		else {
			System.out.println("failed");
		}
	}
	
	
	

}
