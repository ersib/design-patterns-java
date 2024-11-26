package behavioralpatterns.template;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

class Creature
{
  public int attack, health;

  public Creature(int attack, int health)
  {
    this.attack = attack;
    this.health = health;
  }
}

abstract class CardGame
{
  public Creature [] creatures;

  public CardGame(Creature[] creatures)
  {
    this.creatures = creatures;
  }

  // returns -1 if no clear winner (both alive or both dead)
  public int combat(int creature1, int creature2)
  {
    
    Creature first = creatures[creature1];
    Creature second = creatures[creature2];
    int initalHealthFirst = first.health;
    int initalHealthSecond = second.health;
    hit(first, second);
    hit(second, first);
    
   
        
    
    // todo: determine who won and return either creature1 or creature2

    if (first.health <= 0 && second.health <= 0)
        return -1;
    
       
    
    if (first.health > 0 && second.health > 0) {
        evaluateDamage(first, initalHealthFirst);
        evaluateDamage(second, initalHealthSecond); 
        return -1;
    }
        
    
    
        
    
    if (first.health <= 0)
      return creature2;
    else 
      return creature1;
  }

  // attacker hits other creature
  protected abstract void hit(Creature attacker, Creature other);
  
  protected abstract void evaluateDamage(Creature creature, int originalHealth);
  
}

class TemporaryCardDamageGame extends CardGame
{

  public TemporaryCardDamageGame(Creature[] creatures)
  {
    super(creatures);
  }
    
  protected void hit(Creature attacker, Creature other) {
      //if (attacker.health > 0) {
        int initalHealthFirst = other.health;
        other.health -= attacker.attack;
      //}
  }
  
  protected void evaluateDamage(Creature creature, int originalHealth) {
      //if (creature.health > 0)
        creature.health = originalHealth;
  }
  
}

class PermanentCardDamageGame extends CardGame
{
     public PermanentCardDamageGame(Creature[] creatures)
  {
    super(creatures);
  }
    
  protected void hit(Creature attacker, Creature other) {
      //if (attacker.health > 0) {
          int initalHealthFirst = other.health;
          other.health -= attacker.attack;
          System.out.println("other health " + other.health);
      //}
  }
  
    protected void evaluateDamage(Creature creature, int originalHealth) {
    
    }
}

public class TemplateExercise {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	  @Test
	  public void impasseTest()
	  {
	    Creature c1 = new Creature(1, 2);
	    Creature c2 = new Creature(1, 2);
	    TemporaryCardDamageGame game = new TemporaryCardDamageGame(new Creature[]{c1, c2});
	    assertEquals(-1, game.combat(0,1));
	    assertEquals(-1, game.combat(0,1));
	  }

	  @Test
	  public void temporaryMurderTest()
	  {
	    Creature c1 = new Creature(1, 1);
	    Creature c2 = new Creature(2, 2);
	    TemporaryCardDamageGame game = new TemporaryCardDamageGame(new Creature[]{c1, c2});
	    assertEquals(1, game.combat(0,1));
	  }

	  @Test
	  public void doubleMurderTest()
	  {
	    Creature c1 = new Creature(2, 2);
	    Creature c2 = new Creature(2, 2);
	    TemporaryCardDamageGame game = new TemporaryCardDamageGame(new Creature[]{c1, c2});
	    assertEquals(-1, game.combat(0,1));
	  }

	  @Test
	  public void permanentDamageDeathTest()
	  {
	    Creature c1 = new Creature(1, 2);
	    Creature c2 = new Creature(1, 3);
	    CardGame game = new PermanentCardDamageGame(new Creature[]{c1, c2});
	    assertEquals(-1, game.combat(0,1));
	    assertEquals(1, game.combat(0,1));
	  }

}
