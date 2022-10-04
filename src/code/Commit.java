package code;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;    

public class Commit {
	
	private String summary;
	private String author;
	private String date;
	private String nextCommit = ""; //uses string for simplicity
	private Commit prevCommit = null;
	
	public Tree pTree;
	public String treeName;
	public String commitName;
	
	public Commit(String summary, String author, Commit parent) {
		//doesn't check for bad input
		
		//assigning variable
		this.date = getDate();
		this.summary = summary;
		this.author = author;
		this.prevCommit = parent;
		this.commitName = getCommitName();
		
		this.pTree = makeTree();
		treeName = pTree.treeName;
		
		
		if (prevCommit != null) {
			prevCommit.setNext(commitName);
			prevCommit.writeToFile();
		}
		
		writeToFile();
	}
	
	public String getDate() {
		LocalDateTime currentDateTime = LocalDateTime.now();  
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String stringDate = currentDateTime.format(formatter);
		return stringDate;
	}
	
	//index looks like file : sha1
	//tree looks like type : file sha1
	
	public Tree makeTree() {
		ArrayList<String> added = new ArrayList<String>();
		ArrayList<String> deleted = new ArrayList<String>();
		ArrayList<String> edited = new ArrayList<String>();
		
		//grabs info form index file
		String[] content = GitUtils.fileToString("index").split("\n");
		
		//puts each entry into list according to what should be done to it
		//also trims inputs now that it is known what they do
		for (String curStr : content) {
			if (curStr.charAt(0) != '*') { added.add(curStr); }
			else if (curStr.substring(0, 8).equals("*deleted*")) { deleted.add(curStr.substring(9)); } //may be off by one
			else { edited.add(curStr.substring(8)); }
		}
		
		//for each entry in deleted go to the tree that is the parent of that node
		//add each file that that tree references (unless it is part of deleted)
		//find the tree that references that tree
		//repeat the process until you a tree that has no parent
		
		//this has a few problems which are:
		//	are adding a file you already have
		//	are adding a file staged for deletion
		//	when deleting down tree of a tree you have make sure to delete it when you find it
		
		//the translation:
		//hold a list of added files that starts as only the files expressly added
		//have a "deepest valid tree" variable
		//run trough each entry in deleted
		//first check if it is in your added list (it could have gotten there many ways)
		//if it is delete it and move on
		//if it isn't call a method to find what its parent tree is
		//store the tree that that file references in memory
		//run through the tree backwards until adding each file that isn't staged for deletion to added you
		//when you find the deepest valid tree add its children then stop
		//update the deepest valid tree to the one you have stored in memory
		//do this for all deleted members then add your deepest valid tree to the list
		//pass that to the tree maker
		
		
		//lets give it a go
		
		
		
		//make a return list with each nonduplicate add
		if (prevCommit == null) { return new Tree(added); }
		
		ArrayList<String> finalList = added;
		String deepestTreeName = prevCommit.treeName;
		
		//deleted.addAll(edited);
		
		//navigate through the tree and check if each entry is part 
		for (String del : deleted) {
			
			//try to delete item and if it fails continue
			if (!finalList.remove(del)) {
				
				//gets the parentTree of current deletion
				String curTree = findParentTree(del);
				String tempTree = ""; //should get the tree referenced in cur tree
				
				while (!curTree.equals(deepestTreeName)) {
					//add all items of cur tree if they are not already present
					curTree = findParentTree(curTree);
				}
				
				//updating for future loops
				deepestTreeName = tempTree;
				
			}
			
		}
		
		/*
		for (String curAdd : edited) {
			finalList.add(curAdd);
		}
		*/
		
		
		//adds the reference to the tree that covers the most with no conflict
		finalList.add("tree : " + deepestTreeName);
		
		//passes final list to make the tree 
		return new Tree(finalList);
	}
	
	private String findParentTree(String indexEntry) {
		return recursiveTreeFinder(prevCommit.treeName, indexEntry);
	}
	
	private String recursiveTreeFinder(String curFile, String searchedTerm) {
		//TODO really really really needs to check for bad input as that is base case
		
		//gets current file in form of array of lines
		String[] lines = GitUtils.fileToString(curFile).split("\n");
		
		ArrayList<String> blobs = new ArrayList<String>();
		String tree = "";
		//ArrayList trees = new ArrayList<String>();
		
		for (String line : lines) {
			if (line.startsWith("blob")) { blobs.add(line); }
			else { tree = line; }
			//else {trees.add(line); }
		}
		
		if (blobs.contains(searchedTerm)) { return curFile; }
		else {
			//i think there are cases in which there can be two or more trees
			//for (String tree : trees) {
				String treeLocation = tree.substring(tree.indexOf(":") + 1, tree.indexOf(":") + 41);
				return recursiveTreeFinder(treeLocation, searchedTerm);
			//}
		}
		
	}
	
	
	public void writeToFile() {
		
		String content = "";
		if (pTree == null) { content = "\n"; }
		else { content = treeName + "\n"; }
		
		if (prevCommit != null) { content += prevCommit.commitName + "\n"; }
		else { content += "\n"; }
		
		if (!nextCommit.equals("")) { content += nextCommit + "\n"; }
		else { content += "\n"; }
		
		content += author + "\n";
		content += date + "\n";
		content += summary;
		
		GitUtils.makeFile("./objects/" + commitName, content);
		
	}
	
	//with how this code seems nearly redundant it feels as if it could be better
	private String getCommitName() {
		
		//----
		//SHA FOR NAME ONLY TAKES PARENT NOT CHILD
		//----
		
		String content = "";
		
		if (pTree == null) { content = "\n"; }
		else { content = treeName + "\n"; }
		
		if (prevCommit != null) { content += prevCommit.commitName + "\n"; }
		else { content += "\n"; }
		
		content += author + "\n";
		content += date + "\n";
		content += summary;
		
		return GitUtils.toSha(content);
	}
	
	public void setPrevious(Commit newPrevious) { prevCommit = newPrevious; } //writeToFile();
	public void setNext(String newNext) { nextCommit = newNext; } //writeToFile();

}
