package code;

import java.io.File;
import java.util.HashMap;

public class Git {
	
	public File branches; //branch name : sha1 
	public Index writer;
	
	private Commit prevCommit = null;
	private HashMap<String, String> branchesMap = new HashMap<String, String>();

	public Git () {
		
		//have one index that handles all the adding and removing on blob level
		writer = new Index(); //this makes object folder
		
		GitUtils.makeFile("branches", "");
	}
	
	//dont know what these comments mean but i will keep them
	
	//add last tree to index file in last position
	//pass data to commit and let it handle all the problems
	//String indexData = GitUtils.fileToString("index");
	//indexData = "tree";
	
	public void addFile(String fileName) {
		//TODO check the curIn list to see if it is a duplicate
		
		writer.add(fileName);
	}
	
	public void deleteFile(String fileName) {
		writer.remove(fileName); //doesn't check for bad input
	}
	
	public void editFile(String fileName) {
		writer.edit(fileName);
	}
	
	public void commitChanges(String summary, String author) {
		
		//lets the commit use index file, updates pointers the clears for next commit
		Commit c = new Commit(summary, author, prevCommit);
		prevCommit = c;
		writer.clearIndex();
		
		//updates head file
		GitUtils.makeFile("./head", c.commitName);
		
	}
	
	public void makeBranch(String branchName, String summary, String author) {
		Commit c = new Commit(summary, author, prevCommit);
		GitUtils.appendToFile("branches", c.commitName + "\n");
		
		branchesMap.put(branchName, c.commitName);
		
	}
	
	public void switchBranch(String branchName) {
		//TODO check for bad input
		
		//check if branch exists
		//point head to it	
		String branchSha = branchesMap.get(branchName);
		GitUtils.makeFile("head", branchSha);
		
	}
}
