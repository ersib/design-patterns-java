package creationalpatterns.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class BasicSingletion {
	
	private int value;
	
	private BasicSingletion() {
	}
	
	private static final BasicSingletion instance = new BasicSingletion();
	
	public static BasicSingletion getInstance() {
		return instance;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}

class StaticBlockSingleton {
	
	private StaticBlockSingleton() throws IOException {
		System.out.println("Singleton is initializing");
		File.createTempFile(",", ".");
	}
	
	private static StaticBlockSingleton instance;
	
	static {
		try {
			instance = new StaticBlockSingleton();
		} catch(Exception e) {
			System.out.println("Failed to create Singletion");
		}
	}
	
	public static StaticBlockSingleton getInstance() {
		return instance;
	}
}

// Multiton example

enum Subsystem {
	PRIMARY, AUXILARY, BACKUP
}

class Printer {
	
	private static Map<Subsystem, Printer> instances = new HashMap<>();
	private static int instanceCount = 0;
	
	private Printer() {
		instanceCount++;
		System.out.println("A total of " + instanceCount + " instances are created so far");
	}
	
	public static Printer get(Subsystem ss) {
		if (instances.containsKey(ss)) {
			return instances.get(ss);
		} 
		
		Printer instance = new Printer();
		instances.put(ss, instance);
		return instance;
	}
}

//

interface Database {
	int getPopulation(String name);
}

class SingletonDatabase implements Database {
	
	private Dictionary<String, Integer> capitals = new Hashtable<>();
	private static int instanceCount = 0;
	
	public static int getCount() {
		return instanceCount;
	}
	
	private SingletonDatabase() {
		instanceCount++;
		System.out.println("Initializing database");
		try {
			String locationPath = SingletonDatabase.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			File f = new File(locationPath);
			Path fullPath = Paths.get(f.getPath(),"capitals.txt");
			List<String> lines = Files.readAllLines(fullPath);
			lines.forEach(l -> {
				String[] parts = l.split("-");
				capitals.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static final SingletonDatabase INSTANCE = new SingletonDatabase();
	
	public static SingletonDatabase getInstance() {
		return INSTANCE;
	}
	
	public int getPopulation(String cityName) {
		return capitals.get(cityName);
	}
	
}

class SingletonRecordFinder {
	
	public int getTotalPopulation(List<String> names) {
		int result = 0;
		for (String name: names) {
			result += SingletonDatabase.getInstance().getPopulation(name);
		}
		return result;
	}
	
}

class ConfigurableRecordFinder {
	
	private Database database;
	
	public ConfigurableRecordFinder(Database db) {
		this.database = db;
	}
	
	public int getTotalPopulation(List<String> names) {
		int result = 0;
		for (String name: names) {
			result += database.getPopulation(name);
		}
		return result;
	}
	
}

class DummyDatabase implements Database {
	private Dictionary<String, Integer> data = new Hashtable<>();
	
	public DummyDatabase() {
		data.put("alpha", 1);
		data.put("beta", 2);
		data.put("gamma", 3);
	}

	@Override
	public int getPopulation(String name) {
		return data.get(name);
	}
}

public class SingletonDemo {
	
	// Singleton - a component which is instantiated only once
	
	public static void main(String[] args) {
		
		//runBasicSingletionExample();
		
		//StaticBlockSingleton sbs = StaticBlockSingleton.getInstance();
		
		runMultitonExample();
		 
	}

	private static void runMultitonExample() {
		Printer main = Printer.get(Subsystem.PRIMARY);
		Printer aux = Printer.get(Subsystem.AUXILARY);
		Printer aux2 = Printer.get(Subsystem.AUXILARY);
	}

	private static void runBasicSingletionExample() {
		BasicSingletion singleton = BasicSingletion.getInstance();
		singleton.setValue(314);
		System.out.println(singleton.getValue());
	}
	

}
