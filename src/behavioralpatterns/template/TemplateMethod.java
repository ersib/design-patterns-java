package behavioralpatterns.template;

abstract class Game {
	
	protected int currentPlayer;
	protected final int numberOfPlayers;
	
	public Game(int nrPlayers) {
		this.numberOfPlayers = nrPlayers;
	}
	
	public void run() {
		start();
		while(!haveWinner())
			takeTurn();
		System.out.println("Player " + getWinningPlayer() + " wins");
	}

	protected abstract int getWinningPlayer();

	protected abstract void takeTurn();

	protected abstract boolean haveWinner();

	protected abstract void start();

}

class Chess extends Game {

	private int maxTurns = 10;
	private int turn = 1;
	
	public Chess() {
		super(2);
	}

	@Override
	protected int getWinningPlayer() {
		return 0;
	}

	@Override
	protected void takeTurn() {
		System.out.println("Turn " + (turn++) + " taken by player " + currentPlayer);
		currentPlayer = (currentPlayer+1)%numberOfPlayers;
		
	}

	@Override
	protected boolean haveWinner() {
		// TODO Auto-generated method stub
		return turn == maxTurns;
	}

	@Override
	protected void start() {
		System.out.println("Starting a game a chess");
	}
	
}

public class TemplateMethod {

	public static void main(String[] args) {
		new Chess().run();
	}

}
