package Testers;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import code.Git;
//import code.GitUtils;

class GitTester {

	public static Git test;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		test = new Git();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		//test.delete();
	}

	@Test
	void addFiles() {
		//assertTrue(GitUtils.toSha(GitUtils.fileToString("index")).equals(GitUtils.toSha("blob : test1.txt 5b44ab9e81020836b46fb2e846451a717aebae75")));
		
		test.addFile("test1.txt");
		
		test.commitChanges("Commit 1 for testing", "insert author");
		
		test.addFile("test2.txt");
		test.addFile("test3.txt");	
		test.commitChanges("dos", "Nelson Mandela");
		
		test.addFile("test4.txt");
		test.addFile("test5.txt");	
		test.commitChanges("com 3th", "My Schitzo Friends");
		
		test.addFile("test6.txt");
		test.commitChanges("4st commit", "no author provided");
		
		test.deleteFile("test2.txt");
		test.commitChanges("testing deleting", "jonny luck");
		
		test.addFile("test4.txt");
		test.deleteFile("test6.txt");
		test.commitChanges("garbanzo add", "me");
		
		test.deleteFile("test4.txt");
		test.deleteFile("test3.txt");
		test.addFile("test6.txt");
		
		test.commitChanges("final check", "baaby jes"); 
		//should have 1, 5 and 6 in it
		
		test.addFile("test4.txt");
		test.addFile("test3.txt");
		
		test.commitChanges("redundant add", "the nessisary");
		
		test.editFile("test5.txt");
		test.commitChanges("test edit", "the worker");
		
		
	}

}
