package behavioralpatterns.mediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


class Person {
	public String name;
	public ChatRoom room;
	private List<String> chatLog = new ArrayList<>();
	
	public Person(String name) {
		this.name = name;
	}	
	
	public void receive(String sender, String message) {
		String s = sender + ": '" + message + "'";
		System.out.println("[" + name + "'s chat session] " + s);
		chatLog.add(s);
	}
	
	public void say(String message) {
		room.broadcast(name, message);
	}
	
	public void privateMessage(String who, String message) {
		room.message(name, who, message);
	}
}

class ChatRoom {
	private List<Person> people = new ArrayList<>();
	
	public void join(Person p) {
		String joinMsg = p.name + " joins the room";
		broadcast("room", joinMsg);
		p.room = this;
		people.add(p);
	}
	
	public void broadcast(String source, String message) {
		for (Person person: people) {
			if (!person.name.equals(source))
				person.receive(source, message);
		}
	}
	
	public void message(String source, String destionation, String message) {
		people.stream()
			.filter(p->p.name.equals(destionation))
			.findFirst()
			.ifPresent(p -> p.receive(source, message));
	}
	
}

///////

class EventBroker extends Observable {
	
	protected void subscribeActual(Observer observer) {
		
	}
	
}


/// exercise 

class Participant
{
    
  public int value = 0;
  public int saidValue = -1;
  
  private Mediator mediator;
  
  public Participant(Mediator mediator)
  {
    this.mediator = mediator;
    this.mediator.people.add(this);
  }

  public void say(int n)
  {
    this.saidValue = n;  
    this.mediator.broadcast(this, n);
    this.saidValue = -1;
  }
}

class Mediator
{
   List<Participant> people = new ArrayList<>();
   
   public void broadcast (Participant source, int n) {
       for (Participant p: people) {
           if (p.saidValue == -1)
                p.value += n;
       }
   }
   
}
public class MediatorDemo {
	
	public static void main(String[] args) {
		System.out.println("Started");
		
		//runChatRoomExample();
		
	}

	private static void runChatRoomExample() {
		ChatRoom room = new ChatRoom();
		
		Person john = new Person("John");
		Person ben = new Person("Ben");
		
		room.join(john);
		room.join(ben);
		
		john.say("Hi everybody");
		ben.say("Hey John");
		
		Person simon = new Person("Simon");
		room.join(simon);
		simon.say("hi everyone!");
		
		ben.privateMessage("Simon", "Welcome Simon!");
	}
}


