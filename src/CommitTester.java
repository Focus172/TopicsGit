
public class CommitTester {
	
	public static void main (String [] args) {
		
		Commit testCommit = new Commit("treeStr", "testing", "elliot", null);
		
		testCommit.writeToFile();
		
	}
}
