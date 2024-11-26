import java.net.URISyntaxException;
import java.net.URL;

public class MainProgram {

	public static void main(String[] args) {
		
		LocationPrinter lp = new LocationPrinter();
		try {
			lp.printCurrentLocation();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
