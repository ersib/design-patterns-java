package structuralpatterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

interface Drivable {
	void drive();
}


class Car implements Drivable {
	
	Driver driver;
	
	public Car (Driver driver) {
		this.driver = driver;
	}
	
	public void drive() {
		System.out.println("Driving the car ...");
	}
	
}

class Driver {
	public int age;
	
	public Driver(int age) {
		this.age = age;
	}
}

class CarProxy extends Car {

	public CarProxy(Driver driver) {
		super(driver);
	}
	
	@Override
	public void drive() {
		if (driver.age >= 18) 
			super.drive();
		else 
			System.out.println("Drive too young!");
	}
}

// Property Proxy - Example: want to log every change to a class attribute

class Property<T> {
	private T value;
	
	public Property(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		// logging
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Property other = (Property) obj;
		return Objects.equals(value, other.value);
	}
	
}

class Creature {
	private Property<Integer> agility = new Property<>(10);
	
	public void setAgility(int value) {
		agility.setValue(value);
	}
	
	public int getAgility() {
		return agility.getValue();
	}
}

// Dynamic proxy for logging

interface Human {
	void walk();
	void talk();
}

class Person implements Human {

	@Override
	public void walk() {
		System.out.println("I am walking");
	}

	@Override
	public void talk() {
		System.out.println("I am talking");
	}
	
}

class LoggingHandler implements InvocationHandler {

	private final Object target;
	
	private Map<String, Integer> calls = new HashMap<>();
	
	public LoggingHandler(Object target) {
		this.target = target;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String methodName = method.getName();
		if(methodName.contains("toString"))
			return calls.toString();
		calls.merge(methodName, 1, Integer::sum);
		return method.invoke(target, args);
	}
	
}

public class ProxyDemo {

	@SuppressWarnings("unchecked")
	public static <T> T withLogging(T target, Class<T> intrface) {
		return (T) Proxy.newProxyInstance(
				intrface.getClassLoader(),  
				new Class<?>[] { intrface}, 
				new LoggingHandler(target)
		);
	}
	
	public static void main(String[] args) {
		Car car = new Car(new Driver(12));
		car.drive();
		Car carProxy = new CarProxy(new Driver(12));
		carProxy.drive();
		
		Creature creature = new Creature();
		creature.setAgility(12);
		
		Person person = new Person();
		Human logged = withLogging(person, Human.class);
		logged.talk();
		logged.walk();
		logged.walk();
		System.out.println(logged);
		

	}

}
