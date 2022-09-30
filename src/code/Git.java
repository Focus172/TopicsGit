package code;

import java.io.File;

public class Git {
	
	public File head; //points to most recent commit
	public File branches; //branch name : sha1 
	public Index writer;
	
	private Commit prev = null;

	public Git () {
		
		//have one index that handles all the adding and removing on blob level
		writer = new Index(); //this makes object folder
		
	}
	
	public void addFile(String fileName) {
		writer.add(fileName);
	}
	
	public void deleteFile(String fileName) {
		writer.remove(fileName); //doesn't check for bad input
		//removeFile(fileName);
	}
	
	public void editFile(String oldFile, String newFile) {
		//add *edited* fileName to index
		removeFile(oldFile);
		addFile(newFile);
	}
	
	private void removeFile(String fileName) {
		//remove the file
	}
	
	public void commitChanges(String summary, String author) {
		//add last tree to index file in last position
		//pass data to commit and let it handle all the problems
		//String indexData = GitUtils.fileToString("index");
		//indexData = "tree";
		
		Commit c = new Commit(summary, author, prev);
		prev = c;
		
		//make a new commit to store said changes
		
		//update head
		GitUtils.makeFile("head", GitUtils.toSha(GitUtils.toSha(c.fileName)));
	}
	
	public void switchBranch(String branchName) {
		//check if branch exists
		//point head to it	
	}
}
