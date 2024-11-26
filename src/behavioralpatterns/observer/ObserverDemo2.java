package behavioralpatterns.observer;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

class Event<TArgs> {
	
	private int count = 0;
	
	private Map<Integer, Consumer<TArgs>> handlers = new HashMap<>();
	
	public Subscription addHandler(Consumer<TArgs> handler) {
		int id = count;
		handlers.put(count++, handler);
		return new Subscription(this, id);
	}
	
	public void fire(TArgs args) {
		for (Consumer<TArgs> handler: handlers.values()) {
			handler.accept(args);
		}
	}
	
	public class Subscription implements AutoCloseable {

		private Event<TArgs> event;
		private int id;
		
		public Subscription(Event<TArgs> event, int id) {
			super();
			this.event = event;
			this.id = id;
		}

		@Override
		public void close() {
			this.event.handlers.remove(id);
		}
		
	}
}


class PropertyChangedEventArgs {
	public Object source;
	public String propertyName;
	public Object newValue;
}


class Student {
	public Event<ProperyChangedEventArgs> propertyChanged = new Event<>();
	
	private int gpa;

	public int getGpa() {
		return gpa;
	}

	public void setGpa(int gpa) {
		if (this.gpa == gpa) return;
		
		boolean oldFailed = failedClass();
		
		this.gpa = gpa;
		propertyChanged.fire(new ProperyChangedEventArgs(this, "gpa", gpa));
		
		if (oldFailed == failedClass()) return;
		propertyChanged.fire(new ProperyChangedEventArgs(this, "failedClass", failedClass()));
	}
	
	public boolean failedClass() {
		return gpa < 2;
	}
}

// exercise 

class Game
{
    private List<Rat> rats = new ArrayList<>();
    
    public void addRat(Rat rat) {
        this.rats.add(rat);
        updateAttackValue();
    }
    
   public void removeRat() {
       if (this.rats.size() > 0)
            this.rats.remove(0);
        updateAttackValue();
    }
    
    private void updateAttackValue() {
        for(Rat r : rats)
          r.attack = rats.size();
    }
}

class Rat implements Closeable
{
  private Game game;
  public int attack = 1;

  public Rat(Game game)
  {
    game.addRat(this);
  }

  @Override
  public void close()
  {
    game.removeRat();
  }
}

public class ObserverDemo2 {

	public static void main(String[] args) {
		Student student = new Student();
		Event<ProperyChangedEventArgs>.Subscription sub = student.propertyChanged.addHandler(x -> {
			System.out.println("Student's " + x.propertyName + " has changed to " + x.newValue);
		});
		student.setGpa(8);
		student.setGpa(3);
		student.setGpa(4);
		student.setGpa(1);
		sub.close();
		student.setGpa(5);
		
		student.setGpa(7);
	}

}
