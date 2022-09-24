package Testers;
import java.io.IOException;

import code.Index;

public class IndexTester {

	public static void main(String[] args) throws IOException {
		
		Index test = new Index();
		test.init();
		
		test.add("test.txt");
		test.add("test2.txt");
		
		//test.remove("test.txt");
	}

}
