package structuralpatterns.composite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

// Composite - being able to handle individual object and group of objects in a similar fashion / behaviour

class GraphicObject {
	
	protected String name = "Group";
	public String color;
	public List<GraphicObject> children = new ArrayList<>();
	
	public GraphicObject() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		print(sb, 0);
		return sb.toString();
	}
	
	private void print(StringBuilder stringBuilder, int depth) {
		stringBuilder.append(String.join("", Collections.nCopies(depth, "*")))
			.append(depth > 0 ? " " : "")
			.append((color==null || color.isEmpty()) ? "" : (color+" "))
			.append(getName())
			.append(System.lineSeparator());
		for (GraphicObject child: children) {
			child.print(stringBuilder, depth+1);
		}
	}
	
}

class Circle extends GraphicObject {
	
	public Circle(String color) {
		name = "Circle";
		this.color = color;
	}
	
}

class Square extends GraphicObject {
	
	public Square(String color) {
		name = "Square";
		this.color = color;
	}
	
}

// NEURAL NETWORKS EXAMPLE

interface SomeNeurons extends Iterable<Neuron> {
	
	default void connectTo(SomeNeurons other) {
		if (this == other)
			return;
		for (Neuron from: this) {
			for (Neuron to: other) {
				from.out.add(to);
				to.in.add(from);
			}
		}
	}
}

class Neuron implements SomeNeurons {
	
	public ArrayList<Neuron> in, out;

	@Override
	public void forEach(Consumer<? super Neuron> action) {
		action.accept(this);
	}
	
	@Override
	public Iterator<Neuron> iterator() {
		return Collections.singleton(this).iterator();
	}
	
	@Override
	public Spliterator<Neuron> spliterator() {
		return Collections.singleton(this).spliterator();
	}
	
}

class NeuronLayer extends ArrayList<Neuron> implements SomeNeurons {
	
}

// Exercise

interface ValueContainer extends Iterable<Integer> {
    
    default int getValue() {
        int sum = 0;
        for (Integer val : this)
            sum += val;
        return sum;
    }
    
}

class SingleValue implements ValueContainer
{
  public int value;

// please leave this constructor as-is
  public SingleValue(int value)
  {
    this.value = value;
  }
  
  @Override
  public Iterator<Integer> iterator() {
		return Collections.singleton(new Integer (this.value)).iterator();
  }
  
}

class ManyValues extends ArrayList<Integer> implements ValueContainer
{

}


class MyList extends ArrayList<ValueContainer>
{
    // please leave this constructor as-is
  public MyList(Collection<? extends ValueContainer> c)
  {
    super(c);
  }

  public int sum()
  {
      int sum = 0;
      for (ValueContainer vc: this )
        sum = sum + vc.getValue();
      return sum;
  }
}

public class CompositeDemo {

	public static void main(String[] args) {
		
		
		SingleValue five = new SingleValue(5);
		
		ManyValues values = new ManyValues();
		values.add(2);
		values.add(5);
		
		SingleValue seven = new SingleValue(7);
		
		MyList myList = new MyList(Arrays.asList(five, seven, values));
		System.out.println(myList.sum());
		
		//runNeuralNetworkExample();
		
		//runGraphicObjectExample();
	}

	private static void runNeuralNetworkExample() {
		Neuron neuron = new Neuron();
		Neuron neuron2 = new Neuron();
		
		NeuronLayer layer = new NeuronLayer();
		NeuronLayer layer2 = new NeuronLayer();
		neuron.connectTo(neuron2);
		neuron.connectTo(layer);
		layer.connectTo(neuron);
		layer.connectTo(layer2);
	}

	private static void runGraphicObjectExample() {
		GraphicObject drawing = new GraphicObject();
		drawing.setName("My drawing");
		drawing.children.add(new Circle("red"));
		drawing.children.add(new Square("blue"));
		
		GraphicObject group = new GraphicObject();
		group.children.add(new Circle("blue"));
		drawing.children.add(group);
		
		System.out.println(drawing);
	}

}
