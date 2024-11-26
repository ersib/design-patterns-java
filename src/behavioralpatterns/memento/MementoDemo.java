package behavioralpatterns.memento;

import java.util.ArrayList;
import java.util.List;

class BankAccount {
	private int balance;
	
	public BankAccount(int balance) {
		this.balance = balance;
	}
	
	public Memento deposit(int amount) {
		balance += amount;
		return new Memento(balance);
	}

	@Override
	public String toString() {
		return "BankAccount [balance=" + balance + "]";
	}
	
	public void restore(Memento m) {
		balance = m.getBalance();
	}
}

class Memento {
	private int balance;
	public Memento(int balance) {
		this.balance = balance;
	}
	
	public int getBalance() {
		return balance;
	}
}

/// exercise


class Token
{
  public int value = 0;

  public Token(int value)
  {
    this.value = value;
  }
}

class MementoToken
{
  public List<Token> tokens = new ArrayList<>();
}

class TokenMachine
{
  public List<Token> tokens = new ArrayList<>();

  public MementoToken addToken(int value)
  {
	  MementoToken m = new MementoToken();
    for (Token token: tokens)
        m.tokens.add(token);
 
    this.tokens.add(new Token (value));
    m.tokens.add(new Token (value));
    return m;
  }

  public MementoToken addToken(Token token)
  {  
	  MementoToken m = new MementoToken();
    for (Token t: tokens)
        m.tokens.add(t);
    
    this.tokens.add(new Token(token.value));
    m.tokens.add(new Token(token.value));
    
    return m;
  }

  public void revert(MementoToken m)
  {
    this.tokens = new ArrayList();
    for (Token token: m.tokens)
        this.tokens.add(token);
  }
}

public class MementoDemo {

	public static void main(String[] args) {
		BankAccount ba = new BankAccount(100);
		Memento m1 = ba.deposit(50);
		Memento m2 = ba.deposit(25);
		System.out.println(ba);
		
		ba.restore(m1);
		System.out.println(ba);
		
		ba.restore(m2);
		System.out.println(ba);

	}

}
