package creationalpatterns.prototype;

import java.io.Serializable;
import java.io.SerializablePermission;
import java.util.Arrays;

class Address implements Cloneable {
	public String streetName;
	public int houseNumber;
	
	public Address(String streetName, int houseNumber) {
		this.streetName = streetName;
		this.houseNumber = houseNumber;
	}

	@Override
	public String toString() {
		return "Address [streetName=" + streetName + ", houseNumber=" + houseNumber + "]";
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Address(this.streetName, this.houseNumber);
	}
	
}

class Person implements Cloneable {
	public String[] name;
	public Address address;
	
	public Person(String[] name, Address address) {
		this.name = name;
		this.address = address;
	}

	@Override
	public String toString() {
		return "Person [name=" + Arrays.toString(name) + ", address=" + address + "]";
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Person(this.name.clone(), (Address) this.address.clone());
	}
	
}

class EmploymentInformation {
	public String company, position;
	public double salary;
	
	public EmploymentInformation(String company, String position, double salary) {
		this.company = company;
		this.position = position;
		this.salary = salary;
	}
	
	public EmploymentInformation(EmploymentInformation other) {
		this(other.company, other.position, other.salary);
	}

	@Override
	public String toString() {
		return "EmploymentInformation [company=" + company + ", position=" + position + ", salary=" + salary + "]";
	}

}

class Employee {
	public String name;
	public EmploymentInformation employmentInfo;
	
	public Employee(String name, EmploymentInformation employmentInfo) {
		this.name = name;
		this.employmentInfo = employmentInfo;
	}
	
	public Employee(Employee other) {
		this(other.name, new EmploymentInformation(other.employmentInfo));
	}
	
	@Override
	public String toString() {
		return "Employee [name=" + name + ", employmentInfo=" + employmentInfo + "]";
	}
	
}

class Foo implements Serializable {
	public String stuff, whatever;

	public Foo(String stuff, String whatever) {
		super();
		this.stuff = stuff;
		this.whatever = whatever;
	}

	@Override
	public String toString() {
		return "Foo [stuff=" + stuff + ", whatever=" + whatever + "]";
	}
	
}

public class PrototypeDemo {

	public static void main(String[] args) throws Exception {
		
		//runClonableExample();
		
		runCopyConstructureExample();
	}

	private static void runClonableExample() throws CloneNotSupportedException {
		Person john = new Person(new String[] {"John", "Smith"}, new Address("London Road", 123));
		Person jim = (Person) john.clone();
		jim.name[0] = "Jim";
		jim.address.houseNumber = 789;
		System.out.println(john);
		System.out.println(jim);
	}
	
	private static void runCopyConstructureExample() {
		Employee john = new Employee("John", new EmploymentInformation("TESLA", "Engineer", 210000));
		Employee chris = new Employee(john);
		chris.name = "Chris";
		chris.employmentInfo.position = "QA";
		System.out.println(john);
		System.out.println(chris);
	}
	
	private static void runSerializationCopyExample() {
		Foo foo = new Foo("test", "variable");
		Foo foo2 = new Foo("",""); //Foo foo2 = SerializationUtils.roundtrip(foo);
		foo2.whatever = "attribute";
		System.out.println(foo);
		System.out.println(foo2);
	}

}
