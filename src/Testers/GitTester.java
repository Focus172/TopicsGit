package Testers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import code.Git;
import code.GitUtils;

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

	/*
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	*/

	@Test
	void addFiles() {
		test.addFile("test1.txt");
		
		assertTrue(GitUtils.toSha(GitUtils.fileToString("index")).equals(GitUtils.toSha("blob : test1.txt 5b44ab9e81020836b46fb2e846451a717aebae75")));
		
		test.addFile("test2.txt");
		test.commitChanges("I made this with crack", "joe");
		
		
		
		test.addFile("test3.txt");
		//test.addFile("test1.txt");
		
		test.commitChanges("made while high on meth", "dfghjk");
		
		//test.deleteFile("test1.txt");
		
		
		
		
		
		//test.addFile("test1.txt"); //I dont know if this should work
	}

}
