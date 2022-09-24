package Testers;

import code.Commit;

public class CommitTester {
	
	public static void main (String [] args) {
		
		Commit testCommit = new Commit("testing", "elliot", null);
		
		testCommit.writeToFile();
		
	}
}
