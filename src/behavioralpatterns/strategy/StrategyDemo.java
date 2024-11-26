package behavioralpatterns.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

enum OutputFormat {
	MARKDOWN,
	HTML
}

// list in html: <ul><li></li></ul>
// list in markdown:
// * item1
// * itme2

interface ListStrategy {
	
	default void start(StringBuilder sb) {};
	
	void addItem(StringBuilder sb, String item);
	
	default void end(StringBuilder sb) {};
}


class MarkdownListStrategy implements ListStrategy {

	@Override
	public void addItem(StringBuilder sb, String item) {
		sb.append(" * ").append(item).append(System.lineSeparator());
	}
	
}


class HtmlListStrategy implements ListStrategy {

	public void start(StringBuilder sb) {
		sb.append("<ul>").append(System.lineSeparator());
	}
	
	public void addItem(StringBuilder sb, String item) {
		sb.append(" <li>")
		  .append(item)
		  .append("</li>")
		  .append(System.lineSeparator());
	}
	
	
	public void end(StringBuilder sb) {
		sb.append("</ul>").append(System.lineSeparator());
	}
	
}

class TextProcessor {
	
	private StringBuilder sb = new StringBuilder();
	private ListStrategy listStrategy;
	
	public TextProcessor(OutputFormat format) {
		setOutputFormat(format);
	}

	public void setOutputFormat(OutputFormat format) {
		switch(format) {
			case MARKDOWN:
				listStrategy = new MarkdownListStrategy();
				break;
			case HTML:
				listStrategy = new HtmlListStrategy();
				break;
		}
	}
	
	public void appendList(List<String> items) {
		listStrategy.start(sb);
		for (String item: items)
			listStrategy.addItem(sb, item);
		listStrategy.end(sb);
	}
	
	public void clear() {
		sb.setLength(0);
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
	
}

class TextProcessorStatic<LS extends ListStrategy> {
	
	private StringBuilder sb = new StringBuilder();
	private ListStrategy listStrategy;
	
	public TextProcessorStatic(Supplier<? extends LS> ctor) {
		listStrategy = ctor.get();
	}
	
	public void appendList(List<String> items) {
		listStrategy.start(sb);
		for (String item: items)
			listStrategy.addItem(sb, item);
		listStrategy.end(sb);
	}
	
	public void clear() {
		sb.setLength(0);
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
	
}

public class StrategyDemo {

	public static void main(String[] args) {
		TextProcessor tp = new TextProcessor(OutputFormat.MARKDOWN);
		tp.appendList(Arrays.asList("red", "green", "blue"));
		System.out.println(tp);
		
		tp.clear();
		tp.setOutputFormat(OutputFormat.HTML);
		tp.appendList(Arrays.asList("cat", "dog", "hourse"));
		System.out.println(tp);
		
		TextProcessorStatic<MarkdownListStrategy> tps = new TextProcessorStatic<>(MarkdownListStrategy::new);
		tps.appendList(Arrays.asList("A", "B", "C"));
		System.out.println(tps);
		
		
		TextProcessorStatic<HtmlListStrategy> tps2 = new TextProcessorStatic<>(HtmlListStrategy::new);
		tps2.appendList(Arrays.asList("D", "E", "F"));
		System.out.println(tps2);

	}

}
