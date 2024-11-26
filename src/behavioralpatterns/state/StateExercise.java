package behavioralpatterns.state;

class CombinationLock
{
  private int [] combination;
  public String status;
  private int count = 0;

  public CombinationLock(int[] combination)
  {
    this.combination = combination;
    status = "LOCKED";
  }

  public void enterDigit(int digit)
  {
     if (digit == combination[count]) {
         if (status.equals("LOCKED") || status.equals("ERROR"))
            status = "";
         status += "" + digit;
         count++;
         if (unlocked()) {
             status = "OPEN";
         }
     } else {
         status = "ERROR";
         count = 0;
     }
     System.out.println("Entered digit " + digit + ", moved into state " + status);
  }
  
  private boolean unlocked() {
      String combinationOrder = "";
      for (int i = 0; i < combination.length; i++)
        combinationOrder += "" + combination[i];
      return combinationOrder.equals(status);
  }
  
}

public class StateExercise {

	public static void main(String[] args) {
		CombinationLock cl = new CombinationLock(new int[]{1, 2, 3, 4});
		//assertEquals("LOCKED", cl.status);
		cl.enterDigit(1);
		//assertEquals("1", cl.status);
		cl.enterDigit(2);
		//assertEquals("12", cl.status);
		cl.enterDigit(5);
		//assertEquals("123", cl.status);
		cl.enterDigit(4);
		//assertEquals("OPEN", cl.status);
		cl.enterDigit(2);
		cl.enterDigit(3);
		cl.enterDigit(4);
		cl.enterDigit(1);
		cl.enterDigit(2);
		cl.enterDigit(3);
		cl.enterDigit(4);
	}

}
