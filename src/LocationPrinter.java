import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocationPrinter {
	
	public void printCurrentLocation() throws URISyntaxException {
		System.out.println(this.getClass().getClassLoader());
		URL url = this.getClass().getClassLoader().getResource("resources/assetTestFile.txt");
		System.out.println("uri " + url.toURI());
		
		Path path = Paths.get(url.toURI());
		System.out.println("path " + path);
		
		String pathString = path.toString();
		System.out.println("pathString " + pathString);
		
		String myOldPath = this.getClass().getClassLoader().getResource("resources/assetTestFile.txt").getPath();
		System.out.println("myOldPath " + myOldPath);
		
		
		try {
			byte[] fontBytes = Files.readAllBytes(path);
			System.out.println("font bytes " + fontBytes.length);
			
			
			FileInputStream fs = new FileInputStream(myOldPath);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
