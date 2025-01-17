package code;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDateTime;    

public class Commit {
	
	private String summary;
	private String author;
	private String date;
	private String nextCommit = ""; //uses string for simplicity
	private Commit prevCommit = null;
	
	public Tree pTree;
	public String treeName = "";
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
	
	//blobs looks like - blob : sha1 file
	//trees looks like - tree : sha1
	
	public Tree makeTree() {
		ArrayList<String> added = new ArrayList<String>();
		ArrayList<String> deleted = new ArrayList<String>();
		ArrayList<String> edited = new ArrayList<String>();
		
		//grabs info form index file
		ArrayList<String> content = GitUtils.getLines("index");
		
		//puts each entry into list according to what should be done to it
		//also trims inputs now that it is known what they do
		for (String curStr : content) {
			if (curStr.charAt(0) != '*') { added.add(curStr); }
			else if (curStr.substring(0, 9).equals("*deleted*")) { deleted.add(curStr.substring(10)); } //may be off by one
			else { edited.add(curStr.substring(9)); }
		}
		
		System.out.println(deleted.toString());
		
		//attempt 1
		
		
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
		
		
		//attempt 2
		
		//checking will envolve seeing if it exists directly
		//for each removed object see if it is on the current add list and if it is remove it
		//if not go to the current tree and check in their
		//weather it is or isn't add the blobs (that arn't the removed object) and updates the tree
		
		//added
		//deleted
		//edited
		
		ArrayList<String> finalList = added;
		if (prevCommit == null) { return new Tree(finalList); }
		//i think this should get the tree withing the tree
		String currentTree = prevCommit.treeName;
		
		System.out.println(" - " + currentTree);
		
		deleted.addAll(edited);
		
		for (String del : deleted) {
			//try to remove it from current list
			if (finalList.remove(del)) {
				//do something
			} else {
				boolean notFound = true;
				while(!currentTree.equals("") && notFound) {
					System.out.println(" : " + currentTree);
					
					//add all items of current tree that are not the thing
					//if the thing is found then set notFound to false
					ArrayList<String> curBlobs = getBlobs("objects/" + currentTree);
					for (String blob : curBlobs) {
						if (del.equals(blob)) {
							notFound = false;
						} else if (!finalList.contains(blob)){
							finalList.add(blob);
						}
					}
					
					
					
					//and update currentCommit to most recent
					currentTree = getTree("objects/" + currentTree);
					
					System.out.println(" > " + currentTree);
					
				}
			}
		}
		
		for (String edit : edited) {
			finalList.add(edit);
		}
		
		if (!currentTree.equals(""))
			finalList.add("tree : " + currentTree);
		
		return new Tree(finalList);
	}
	
	private String getTree(String curTree) {
		ArrayList<String> lines = GitUtils.getLines(curTree);
		for (String line : lines) {
			if (line.startsWith("tree")) { return line.substring(line.indexOf(":")+2, line.indexOf(":")+42); }
		}
		
		return "";
	}
	
	private ArrayList<String> getBlobs(String treeName) {
		//TODO check for bad input
		
		//gets lines from tree file
		ArrayList<String> lines = GitUtils.getLines(treeName);
		ArrayList<String> retList = new ArrayList<String>();
				
		//if any line starts with blob add it to list
		for (String line : lines) {
			if (line.startsWith("blob")) { retList.add(line); }
		}
				
		//if there is no tree return nothing
		return retList;
		
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
		
		//SHA FOR NAME ONLY TAKES PARENT NOT CHILD
		
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
	
	public void setPrevious(Commit newPrevious) { prevCommit = newPrevious; }
	public void setNext(String newNext) { nextCommit = newNext; }

}
