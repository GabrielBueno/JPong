package resource;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Resource {
	public static File loadResource(String filename) {
		return new File(Resource.getResourceAbsolutePath(filename));
	}
	
	public static InputStream loadResourceAsStream(String filename) throws FileNotFoundException {
		return new FileInputStream(Resource.getResourceAbsolutePath(filename));
	}
	
	public static String getResourceAbsolutePath(String filename) {
		return Resource.class.getResource(filename).getPath();
	}
}
