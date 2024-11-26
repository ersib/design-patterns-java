package behavioralpatterns.chainofresponsibility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

class Creature {
	public String name;
	public int attack, defense;
	
	public Creature(String name, int attack, int defense) {
		super();
		this.name = name;
		this.attack = attack;
		this.defense = defense;
	}

	@Override
	public String toString() {
		return "Creature [name=" + name + ", attack=" + attack + ", defense=" + defense + "]";
	}
	
}

class CreatureModifier {
	
	protected Creature creature;
	protected CreatureModifier next;
	
	public CreatureModifier(Creature creature) {
		this.creature = creature;
	}
	
	public void add(CreatureModifier cm) {
		if (next != null) {
			next.add(cm);
		} else {
			next = cm;
		}
	}
	
	public void handle() {
		if (next != null) 
			next.handle();
	}
	
}

class DoubleAttackModifier extends CreatureModifier {

	public DoubleAttackModifier(Creature creature) {
		super(creature);
	}
	
	@Override
	public void handle() {
		System.out.println("Doubling " + creature.name + " 's attack");
		creature.attack *= 2;
		super.handle();
	}
	
}

class IncreaseDefenseModifier extends CreatureModifier {

	public IncreaseDefenseModifier(Creature creature) {
		super(creature);
	}
	
	@Override
	public void handle() {
		System.out.println("Increasing " + creature.name + " 's defense");
		creature.defense += 5;
		super.handle();
	}
	
}

class NoBonusModifier extends CreatureModifier {

	public NoBonusModifier(Creature creature) {
		super(creature);
	}
	
	@Override
	public void handle() {
		System.out.println("No bonus for you!");
	}
}

// Command Query Separation - a combination of several patterns

// observer
class Event<Args> {
	
	private int index = 0;
	
	private Map<Integer, Consumer<Args>> handlers = new HashMap<>();
	
	public int subscribe(Consumer<Args> handler) {
		int i = index;
		handlers.put(index++, handler);
		return i;
	}
	
	public void unsubscribe(int key) {
		handlers.remove(key);
	}
	
	public void fire (Args args) {
		for (Consumer<Args> handler : handlers.values()) {
			handler.accept(args);
		}
	}
	
}

class Query {
	
	public String avatarName;
	enum Argument {
		ATTACK, DEFESE
	}
	public Argument argument;
	public int result;
	
	public Query(String avatarName, Argument argument, int result) {
		this.avatarName = avatarName;
		this.argument = argument;
		this.result = result;
	}
	
}

class Game {
	
	public Event<Query> queries = new Event<>();

}

class Avatar {
	
	private Game game;
	public String name;
	public int baseAttack, baseDefense;
	
	public Avatar(Game game, String name, int baseAttack, int baseDefense) {
		this.game = game;
		this.name = name;
		this.baseAttack = baseAttack;
		this.baseDefense = baseDefense;
	}
	
	int getAttack() {
		Query query = new Query(name, Query.Argument.ATTACK, baseAttack);
		game.queries.fire(query);
		return query.result;
	}
	
	int getDefense() {
		Query query = new Query(name, Query.Argument.DEFESE, baseDefense);
		game.queries.fire(query);
		return query.result;
	}

	@Override
	public String toString() {
		return "Avatar [name=" + name + ", attack=" + getAttack() + ", defense=" + getDefense() + "]";
	}
	
}

class AvatarModifier {
	
	protected Game game;
	protected Avatar avatar;
	
	public AvatarModifier(Game game, Avatar avatar) {
		this.game = game;
		this.avatar = avatar;
	}
	
}

class DoubleAttackAvatarModifier extends AvatarModifier implements AutoCloseable {

	private final int token;
	
	public DoubleAttackAvatarModifier(Game game, Avatar avatar) {
		super(game, avatar);
		token = game.queries.subscribe(q -> {
			if (q.avatarName.equals(avatar.name) && q.argument == Query.Argument.ATTACK) {
				q.result *= 2;
			}
		});
	}

	@Override
	public void close() {
		game.queries.unsubscribe(token);
	}
	
}

class IncreaseDefenseAvatarModifier extends AvatarModifier {

	private final int token;
	
	public IncreaseDefenseAvatarModifier(Game game, Avatar avatar) {
		super(game, avatar);
		token = game.queries.subscribe(q -> {
			if (q.avatarName.equals(avatar.name) && q.argument == Query.Argument.DEFESE) {
				q.result += 5;
			}
		});
	}
	
}

// EXERCISE

abstract class Monster
{
   int baseAttack = 1, baseDefense = 1;	
  public abstract int getAttack();
  public abstract int getDefense();
  
  @Override
  public String toString() {
  	return "Monster [Attack=" + getAttack() + ", Defense=" + getDefense() + "]";
  }
}

class Goblin extends Monster
{
  private MonsterGame game;
  	
  public Goblin(MonsterGame game)
  {
	  game.incrementPoints(Statistic.DEFENSE, 1);
	  this.game = game;
  }

  @Override
  public int getAttack()
  {
	  if (game.creatures.size() == 1) {
		  return baseAttack;
	  }
    return baseAttack + game.bonus.get(Statistic.ATTACK);
  }

  @Override
  public int getDefense()
  {
	  if (game.creatures.size() == 1) {
		  return baseDefense;
	  }
	  return baseDefense + game.bonus.get(Statistic.DEFENSE) - 1;
  }

@Override
public String toString() {
	return "Goblin [Attack=" + getAttack() + ", Defense=" + getDefense() + "]";
}
  
  
}

class GoblinKing extends Goblin
{

	public GoblinKing(MonsterGame game) {
		 super(game);
		 game.incrementPoints(Statistic.ATTACK, 1);
	}

}

enum Statistic
{
  ATTACK, DEFENSE
}

class MonsterGame
{
   protected Map<Statistic, Integer> bonus = new HashMap<>();
   
   public List<Monster> creatures = new ArrayList<>();

   public void incrementPoints(Statistic statistic, int point) {
	 if (bonus.get(Statistic.ATTACK) == null) {
		 bonus.put(Statistic.ATTACK, 0);
	 }
	 if (bonus.get(Statistic.DEFENSE) == null) {
		 bonus.put(Statistic.DEFENSE, 0);
	 }
	 bonus.put(statistic, bonus.get(statistic) + point);
   }
   
   
}

public class ChainOfResponisibilityDemo {

	public static void main(String[] args) {
		
		runModifierExample();
		
		runCommanQuerySeparatorExample();
		
		 System.out.println("\n Exercise");
		 MonsterGame game = new MonsterGame();
		 Goblin goblin = new Goblin(game);
		 game.creatures.add(goblin);
		 game.creatures.add(new Goblin(game));
		 game.creatures.add(new Goblin(game));
		 
		 for (Monster g : game.creatures) {
			System.out.println(g);
		}
	}

	private static void runCommanQuerySeparatorExample() {
		Game game = new Game();
		Avatar avatar = new Avatar(game, "Avatar", 2, 2);
		System.out.println("Avatar " + avatar );
		IncreaseDefenseAvatarModifier idm = new IncreaseDefenseAvatarModifier(game, avatar);
		try(DoubleAttackAvatarModifier dam = new DoubleAttackAvatarModifier(game, avatar)) {
			System.out.println("Modified avatar " + avatar);
		}
		System.out.println("Avatar " + avatar);
	}

	private static void runModifierExample() {
		Creature goblin = new Creature("Goblin", 2, 2);
		System.out.println(goblin);
		
		CreatureModifier root = new CreatureModifier(goblin);
		root.add(new NoBonusModifier(goblin));
		root.add(new DoubleAttackModifier(goblin));
		root.add(new IncreaseDefenseModifier(goblin));
		
		root.handle();
		
		System.out.println("Modified goblin " + goblin);
	}

}
