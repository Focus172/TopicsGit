package code;

public class Blob {
	
	public String sha;

	private String content;

	public Blob (String filePath) {
		
		//doesn't currently handle bad input
		
		//gets file contents to string
		content = GitUtils.fileToString(filePath);

		//gets name of file as sha1 
		sha = GitUtils.toSha(content);
		String fileName = "./objects/" + sha;
		GitUtils.makeFile(fileName, content);
		
	}
	
}
