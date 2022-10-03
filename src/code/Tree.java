package code;

import java.util.ArrayList;

public class Tree {
	
	public String treeName;
	
	public Tree(ArrayList<String> inputs) {
		
		//creates content variable
		String content = "";
		
		//pairs should be in format of type : sha1 : fileName.extesion
		for (String str : inputs) { content += str + "\n"; } //need to make sure finalnew line is not there
		
		//puts content through sh1 to get file name
		treeName = GitUtils.toSha(content);
		
		// write new file with sha1 as the name
		GitUtils.makeFile("./objects/" + treeName, content);
		
	}
	
}


