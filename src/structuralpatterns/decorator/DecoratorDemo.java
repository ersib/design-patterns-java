package structuralpatterns.decorator;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.IntStream;

// Decorator - adding behaviour without altering the class itself

class MagicString {
	
	private String string;
	
	public MagicString(String string) {
		this.string = string;
	}
	
	public long getNumberOfVowels() {
		return string.chars()
				.mapToObj(c -> (char) c)
				.filter(c -> "aeiou".contains(c.toString()))
				.count();
	}
	
	@Override
	public String toString() {
		return string;
	}
	
	////////

	public IntStream chars() {
		return string.chars();
	}

	public IntStream codePoints() {
		return string.codePoints();
	}

	public int length() {
		return string.length();
	}

	public boolean isEmpty() {
		return string.isEmpty();
	}

	public char charAt(int index) {
		return string.charAt(index);
	}

	public int codePointAt(int index) {
		return string.codePointAt(index);
	}

	public int codePointBefore(int index) {
		return string.codePointBefore(index);
	}

	public int codePointCount(int beginIndex, int endIndex) {
		return string.codePointCount(beginIndex, endIndex);
	}

	public int offsetByCodePoints(int index, int codePointOffset) {
		return string.offsetByCodePoints(index, codePointOffset);
	}

	public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
		string.getChars(srcBegin, srcEnd, dst, dstBegin);
	}

	public void getBytes(int srcBegin, int srcEnd, byte[] dst, int dstBegin) {
		string.getBytes(srcBegin, srcEnd, dst, dstBegin);
	}

	public byte[] getBytes(String charsetName) throws UnsupportedEncodingException {
		return string.getBytes(charsetName);
	}

	public byte[] getBytes(Charset charset) {
		return string.getBytes(charset);
	}

	public byte[] getBytes() {
		return string.getBytes();
	}

	public boolean contentEquals(StringBuffer sb) {
		return string.contentEquals(sb);
	}

	public boolean contentEquals(CharSequence cs) {
		return string.contentEquals(cs);
	}

	public boolean equalsIgnoreCase(String anotherString) {
		return string.equalsIgnoreCase(anotherString);
	}

	public int compareTo(String anotherString) {
		return string.compareTo(anotherString);
	}

	public int compareToIgnoreCase(String str) {
		return string.compareToIgnoreCase(str);
	}

	public boolean regionMatches(int toffset, String other, int ooffset, int len) {
		return string.regionMatches(toffset, other, ooffset, len);
	}

	public boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len) {
		return string.regionMatches(ignoreCase, toffset, other, ooffset, len);
	}

	public boolean startsWith(String prefix, int toffset) {
		return string.startsWith(prefix, toffset);
	}

	public boolean startsWith(String prefix) {
		return string.startsWith(prefix);
	}

	public boolean endsWith(String suffix) {
		return string.endsWith(suffix);
	}

	public int indexOf(int ch) {
		return string.indexOf(ch);
	}

	public int indexOf(int ch, int fromIndex) {
		return string.indexOf(ch, fromIndex);
	}

	public int lastIndexOf(int ch) {
		return string.lastIndexOf(ch);
	}

	public int lastIndexOf(int ch, int fromIndex) {
		return string.lastIndexOf(ch, fromIndex);
	}

	public int indexOf(String str) {
		return string.indexOf(str);
	}

	public int indexOf(String str, int fromIndex) {
		return string.indexOf(str, fromIndex);
	}

	public int lastIndexOf(String str) {
		return string.lastIndexOf(str);
	}

	public int lastIndexOf(String str, int fromIndex) {
		return string.lastIndexOf(str, fromIndex);
	}

	public String substring(int beginIndex) {
		return string.substring(beginIndex);
	}

	public String substring(int beginIndex, int endIndex) {
		return string.substring(beginIndex, endIndex);
	}

	public CharSequence subSequence(int beginIndex, int endIndex) {
		return string.subSequence(beginIndex, endIndex);
	}

	public String concat(String str) {
		return string.concat(str);
	}

	public String replace(char oldChar, char newChar) {
		return string.replace(oldChar, newChar);
	}

	public boolean matches(String regex) {
		return string.matches(regex);
	}

	public boolean contains(CharSequence s) {
		return string.contains(s);
	}

	public String replaceFirst(String regex, String replacement) {
		return string.replaceFirst(regex, replacement);
	}

	public String replaceAll(String regex, String replacement) {
		return string.replaceAll(regex, replacement);
	}

	public String replace(CharSequence target, CharSequence replacement) {
		return string.replace(target, replacement);
	}

	public String[] split(String regex, int limit) {
		return string.split(regex, limit);
	}

	public String[] split(String regex) {
		return string.split(regex);
	}

	public String toLowerCase(Locale locale) {
		return string.toLowerCase(locale);
	}

	public String toLowerCase() {
		return string.toLowerCase();
	}

	public String toUpperCase(Locale locale) {
		return string.toUpperCase(locale);
	}

	public String toUpperCase() {
		return string.toUpperCase();
	}

	public String trim() {
		return string.trim();
	}

	public char[] toCharArray() {
		return string.toCharArray();
	}

	public String intern() {
		return string.intern();
	}
	
}

// Dynamic Decorator Example

interface Shape {
	String info();
}

class Circle implements Shape {

	private float radius;
	
	public Circle() {
	}
	
	public Circle(float radius) {
		this.radius = radius;
	}
	
	void resize(float factor) {
		this.radius *= factor;
	}

	@Override
	public String info() {
		return "A circle of radius " + radius;
	}
	
}

class Square implements Shape {
	
	private float side;
	
	public Square() {
	}
	
	public Square(float side) {
		this.side = side;
	}

	@Override
	public String info() {
		return "A square of side " + side;
	}
	
}

class ColoredShape implements Shape {
	
	private Shape shape;
	private String color;

	public ColoredShape(Shape shape, String color) {
		this.shape = shape;
		this.color = color;
	}

	@Override
	public String info() {
		return shape.info() + " has the color " + color;
	}
	
}

class TransparentShape implements Shape {
	
	private Shape shape;
	private int transperency;

	public TransparentShape(Shape shape, int transperency) {
		this.shape = shape;
		this.transperency = transperency;
	}

	@Override
	public String info() {
		return shape.info() + " has the transperency " + transperency;
	}
	
}

// Static Decorator Example

class StaticColoredShape<T extends Shape> implements Shape {
	
	private Shape shape;
	private String color;
	
	public StaticColoredShape(Supplier<? extends T> ctor, String color) {
		shape = ctor.get();
		this.color = color;
	}

	@Override
	public String info() {
		return shape.info() + " has the color " + color;
	}
	
}

class StaticTransparentShape<T extends Shape> implements Shape {
	
	private Shape shape;
	private int transparency;
	
	public StaticTransparentShape(Supplier<? extends T> ctor, int transparency) {
		shape = ctor.get();
		this.transparency = transparency;
	}

	@Override
	public String info() {
		return shape.info() + " has the transparency " + transparency;
	}
	
}


// Adapter Decorator Example

class MyStringBuilder {
	
	private StringBuilder sb;
	
	public MyStringBuilder() {
		sb = new StringBuilder();
	}
	
	public MyStringBuilder(String str) {
		sb = new StringBuilder(str);
	}
	
	public MyStringBuilder concat(String str) {
		return new MyStringBuilder(sb.toString().concat(str));
	}
	
	public MyStringBuilder appendLine(String str) {
		sb.append(str).append(System.lineSeparator());
		return this;
	}
	
	public String toString() {
		return sb.toString();
	}
	
	///////////////////////

	public int length() {
		return sb.length();
	}

	public int hashCode() {
		return sb.hashCode();
	}

	public int capacity() {
		return sb.capacity();
	}

	public void ensureCapacity(int minimumCapacity) {
		sb.ensureCapacity(minimumCapacity);
	}

	public boolean equals(Object obj) {
		return sb.equals(obj);
	}

	public IntStream chars() {
		return sb.chars();
	}

	public MyStringBuilder append(Object obj) {
		sb.append(obj);
		return this;
	}

	public MyStringBuilder append(String str) {
		sb.append(str);
		return this;
	}

	public MyStringBuilder append(StringBuffer sb) {
		this.sb.append(sb);
		return this;
	}

	public void trimToSize() {
		sb.trimToSize();
	}

	public void setLength(int newLength) {
		sb.setLength(newLength);
	}

	public IntStream codePoints() {
		return sb.codePoints();
	}

	public MyStringBuilder append(CharSequence s) {
		sb.append(s);
		return this;
	}

	public MyStringBuilder append(CharSequence s, int start, int end) {
		sb.append(s, start, end);
		return this;
	}

	public MyStringBuilder append(char[] str) {
		sb.append(str);
		return this;
	}

	public MyStringBuilder append(char[] str, int offset, int len) {
		sb.append(str, offset, len);
		return this;
	}

	public MyStringBuilder append(boolean b) {
		sb.append(b);
		return this;
	}

	public MyStringBuilder append(char c) {
		sb.append(c);
		return this;
	}

	public MyStringBuilder append(int i) {
		sb.append(i);
		return this;
	}

	public MyStringBuilder append(long lng) {
		sb.append(lng);
		return this;
	}

	public MyStringBuilder append(float f) {
		sb.append(f);
		return this;
	}

	public MyStringBuilder append(double d) {
		sb.append(d);
		return this;
	}

	public MyStringBuilder appendCodePoint(int codePoint) {
		sb.appendCodePoint(codePoint);
		return this;
	}

	public char charAt(int index) {
		return sb.charAt(index);
	}

	public StringBuilder delete(int start, int end) {
		return sb.delete(start, end);
	}

	public StringBuilder deleteCharAt(int index) {
		return sb.deleteCharAt(index);
	}

	public StringBuilder replace(int start, int end, String str) {
		return sb.replace(start, end, str);
	}

	public StringBuilder insert(int index, char[] str, int offset, int len) {
		return sb.insert(index, str, offset, len);
	}

	public int codePointAt(int index) {
		return sb.codePointAt(index);
	}

	public StringBuilder insert(int offset, Object obj) {
		return sb.insert(offset, obj);
	}

	public StringBuilder insert(int offset, String str) {
		return sb.insert(offset, str);
	}

	public StringBuilder insert(int offset, char[] str) {
		return sb.insert(offset, str);
	}

	public StringBuilder insert(int dstOffset, CharSequence s) {
		return sb.insert(dstOffset, s);
	}

	public StringBuilder insert(int dstOffset, CharSequence s, int start, int end) {
		return sb.insert(dstOffset, s, start, end);
	}

	public StringBuilder insert(int offset, boolean b) {
		return sb.insert(offset, b);
	}

	public int codePointBefore(int index) {
		return sb.codePointBefore(index);
	}

	public StringBuilder insert(int offset, char c) {
		return sb.insert(offset, c);
	}

	public StringBuilder insert(int offset, int i) {
		return sb.insert(offset, i);
	}

	public StringBuilder insert(int offset, long l) {
		return sb.insert(offset, l);
	}

	public StringBuilder insert(int offset, float f) {
		return sb.insert(offset, f);
	}

	public StringBuilder insert(int offset, double d) {
		return sb.insert(offset, d);
	}

	public int indexOf(String str) {
		return sb.indexOf(str);
	}

	public int indexOf(String str, int fromIndex) {
		return sb.indexOf(str, fromIndex);
	}

	public int codePointCount(int beginIndex, int endIndex) {
		return sb.codePointCount(beginIndex, endIndex);
	}

	public int lastIndexOf(String str) {
		return sb.lastIndexOf(str);
	}

	public int lastIndexOf(String str, int fromIndex) {
		return sb.lastIndexOf(str, fromIndex);
	}

	public StringBuilder reverse() {
		return sb.reverse();
	}

	public int offsetByCodePoints(int index, int codePointOffset) {
		return sb.offsetByCodePoints(index, codePointOffset);
	}

	public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
		sb.getChars(srcBegin, srcEnd, dst, dstBegin);
	}

	public void setCharAt(int index, char ch) {
		sb.setCharAt(index, ch);
	}

	public String substring(int start) {
		return sb.substring(start);
	}

	public CharSequence subSequence(int start, int end) {
		return sb.subSequence(start, end);
	}

	public String substring(int start, int end) {
		return sb.substring(start, end);
	}	
	
}

// EXERCISE

class Bird
{
  public int age;

  public String fly()
  {
    return age < 10 ? "flying" : "too old";
  }
}

class Lizard
{
  public int age;

  public String crawl()
  {
    return (age > 1) ? "crawling" : "too young";
  }
}

class Dragon
{
    private Bird bird = new Bird();
    private Lizard lizard = new Lizard();
  private int age;
  public void setAge(int age)
  {
    bird.age = age;
    lizard.age = age;
  }
  public String fly()
  {
    return bird.fly();
  }
  public String crawl()
  {
    return lizard.crawl();
  }
}


public class DecoratorDemo {

	public static void main(String[] args) {
		
		MagicString ms = new MagicString("Hipopotamus");
		System.out.println(String.format("%s has %d vowels", ms, ms.getNumberOfVowels()));
		
		Circle circle = new Circle(10);
		System.out.println(circle.info());
		ColoredShape blueSquare = new ColoredShape(new Square(14), "blue");
		System.out.println(blueSquare.info());
		TransparentShape greenTransparentShape = new TransparentShape(new ColoredShape(new Circle(5), "green"), 50);
		System.out.println(greenTransparentShape.info());
		
		System.out.println("\nThe static decorator example");
		
		StaticColoredShape<Square> blueStaticSquare = new StaticColoredShape(() -> new Square(14), "blue");
		System.out.println(blueStaticSquare.info());
		
		StaticTransparentShape<StaticColoredShape<Circle>> greenStaticTransparentShape 
		     = new StaticTransparentShape(
		    		 (   () -> new StaticColoredShape(
		    				 () -> new Circle(5), "green"
		    				 )
		    		 ), 50
		    	);
		
		System.out.println(greenStaticTransparentShape.info());
		
		System.out.println("\nThe adapter decorator example");
		MyStringBuilder msb = new MyStringBuilder();
		msb.append("Hello").appendLine(" World");
		System.out.println(msb.concat("and this too"));
	}

}
