package creationalpatterns;

import java.util.ArrayList;
import java.util.Collections;

class HtmlElement {
	
	public String name, text;
	public ArrayList<HtmlElement> elements = new ArrayList();
	private final int indentSize = 2;
	private final String NEW_LINE = System.lineSeparator();
	
	public HtmlElement() {
	}
	
	public HtmlElement(String name, String text) {
		this.name = name;
		this.text = text;
	}
	
	@Override
	public String toString() {
		return toStringImpl(0);
	}
	
	private String toStringImpl(int indent) {
		StringBuilder sb = new StringBuilder();
		String indentation = String.join("", Collections.nCopies(indent * indentSize, " "));
		sb.append(String.format("%s<%s>%s", indentation, name, NEW_LINE));
		
		if (text != null && !text.isEmpty()) {
			sb.append(String.join("", Collections.nCopies((indent+1) * indentSize, " ")))
			  .append(text)
			  .append(NEW_LINE);
		}
		
		for(HtmlElement element: elements) {
			sb.append(element.toStringImpl(indent+1));
		}
		
		sb.append(String.format("%s</%s>%s", indentation, name, NEW_LINE));
		
		return sb.toString();
	}
	
}

class HtmlElementBuilder {
	private String rootName;
	private HtmlElement root = new HtmlElement();
	
	public HtmlElementBuilder(String rootName) {
		this.rootName = rootName;
		this.root.name = rootName;
	}
	
	public HtmlElementBuilder addChild(String childName, String childText) {
		this.root.elements.add(new HtmlElement(childName, childText));
		return this;
	}
	
	public void clear() {
		root = new HtmlElement();
		root.name = rootName;
	}
	
	@Override
	public String toString() {
		return root.toString();
	}
	
}

// SECOND EXAMPLE

class Person {
	public String name, position;

	@Override
	public String toString() {
		return "Person [name=" + name + ", position=" + position + "]";
	}
}

class PersonBuilder<SELF extends PersonBuilder<SELF>> {
	protected Person person = new Person();
	
	public SELF withName(String name) {
		person.name = name;
		return self();
	}
	
	protected SELF self() {
		return (SELF) this;
	}
	
	public Person build() {
		return person;
	}
}

class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> {
	public EmployeeBuilder worksAt(String position) {
		person.position = position;
		return self();
	}
	
	@Override
	protected EmployeeBuilder self() {
		return this;
	}
}

// THIRD EXAMPLE - FACETED BUILDER

class Student {
	
	public String streetAddress, postcode, city;
	
	public String university, program;
	public float GPA;
	
	@Override
	public String toString() {
		return "Student [streetAdress=" + streetAddress + ", postcode=" + postcode + ", city=" + city + ", university="
				+ university + ", program=" + program + ", GPA=" + GPA + "]";
	}
	
}

class StudentBuilder {
	protected Student student = new Student();
	
	public StudentAddressBuilder lives() {
		return new StudentAddressBuilder(student);
	}
	
	public StudentEducationBuilder studies() {
		return new StudentEducationBuilder(student);
	}
	
	public Student build() {
		return student;
	}
}

class StudentAddressBuilder extends StudentBuilder {
	
	public StudentAddressBuilder(Student student) {
		this.student = student;
	}
	
	public StudentAddressBuilder at(String streetAddress) {
		student.streetAddress = streetAddress;
		return this;
	}
	
	public StudentAddressBuilder withPostcode(String postcode) {
		student.postcode = postcode;
		return this;
	}
	
	public StudentAddressBuilder in(String city) {
		student.city = city;
		return this;
	}
}

class StudentEducationBuilder extends StudentBuilder {
	
	public StudentEducationBuilder(Student student) {
		this.student = student;
	}
	
	public StudentEducationBuilder at(String university) {
		student.university = university;
		return this;
	}
	
	public StudentEducationBuilder withProgram(String program) {
		student.program = program;
		return this;
	}
	
	public StudentEducationBuilder withGPA(float GPA) {
		student.GPA = GPA;
		return this;
	}
}


// EXERCISE

class CodeBuilder
{
    
    public String className;
    private StringBuilder sb = new StringBuilder();
    
    public CodeBuilder(String className)
    {
        this.className = className;
         sb.append(String.format("public class %s\n", className));
         sb.append("{\n");
    }
    
    public CodeBuilder addField(String name, String type)
    {
       sb.append(String.format("  public %s %s;\n", type, name));
       return this;
    }
    
    @Override
    public String toString()
    {
       sb.append("}");
       return sb.toString();
    }
}

public class BuilderDemo {

	
	public static void main(String[] args) {
		//startHtmlElementBuilderDemo();
		
		//startPersonGenericsRecursiveBuilderDemo();
		
		//startFacetedBuilderDemo();
		
		startExerciseDemo();
	}

	private static void startFacetedBuilderDemo() {
		StudentBuilder sb = new StudentBuilder();
		Student student = sb.lives()
								.at("London Road 342")
								.in("London")
								.withPostcode("DG324J")
							.studies()
								.at("London Public University")
								.withProgram("Computer Engineering")
								.withGPA(3.5f)
							.build();
		System.out.println(student);
	}

	private static void startPersonGenericsRecursiveBuilderDemo() {
		EmployeeBuilder pb = new EmployeeBuilder();
		Person john = pb.withName("John")
						.worksAt("Software Developer")
						.build();
		System.out.println(john);
	}

	private static void startHtmlElementBuilderDemo() {
		HtmlElementBuilder builder = new HtmlElementBuilder("ul");
		builder.addChild("li", "hello").addChild("li", "word").addChild("li", "!!!");
		System.out.println(builder);
	}
	
	private static void startExerciseDemo() {
		CodeBuilder cb = new CodeBuilder("Person").addField("name", "String").addField("age", "int");
		System.out.println(cb);
	}

}
