package creationalpatterns.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Tests {
	
	@Test
	public void singletonTotalPopulationTest() { 
		// This is not a correct unit test. It is more like an integration test because it does not test only record finder 
		// but also queries the real database 
		System.out.println("Running singleton test");
		SingletonRecordFinder rf = new SingletonRecordFinder();
		List<String> names = Arrays.asList("London", "New York", "Rome");
		int tp = rf.getTotalPopulation(names);
		assertEquals(12000000, tp);
	}
	
	
	@Test
	public void dependentPopulationTest() {
		DummyDatabase db = new DummyDatabase();
		ConfigurableRecordFinder crf = new ConfigurableRecordFinder(db);
		int totalPopulation = crf.getTotalPopulation(Arrays.asList("alpha", "beta"));
		assertEquals(3, totalPopulation);
	}
}
