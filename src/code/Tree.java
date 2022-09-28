package code;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Tree {
	
	public Tree(ArrayList<String> inputs) {
		
		//creates content variable
		String content = "";
		
		//pairs should be in format of type : sha1 : fileName.extesion
		for (String str : inputs) { content += str + "\n"; }
		
		//puts content through sh1 to get file name
		String contentHash = GitUtils.toSha(content);
		
		// write new file with sha1 as the name
		Path treeHash = Paths.get("./objects/" + contentHash);
		try { Files.writeString(treeHash, content, StandardCharsets.ISO_8859_1); }
		catch (IOException e) { e.printStackTrace(); }
	}
}


