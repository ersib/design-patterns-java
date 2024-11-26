package behavioralpatterns.null_object;

import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.net.URL;

interface Log {
	void info(String msg);
	void warn(String msg);
}

class BankAccount {
	private Log log;
	private int balance = 0;
	public BankAccount(Log log) {
		this.log = log;
	}
	
	public void deposit(int amount) {
		this.balance += amount;
		log.info("Deposited " + amount);
	}
}

class ConsoleLog implements Log {

	@Override
	public void info(String msg) {
		System.out.println("INFO - " + msg);
	}

	@Override
	public void warn(String msg) {
		System.out.println("ERROR - " + msg);
	}
	
}

final class NullLog implements Log {

	@Override
	public void info(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(String msg) {
		// TODO Auto-generated method stub
		
	}
	
}


//// exercise

interface RecordLog
{
  // max # of elements in the log
  int getRecordLimit();

  // number of elements already in the log
  int getRecordCount();

  // expected to increment record count
  void logInfo(String message);
}

class Account
{
  private RecordLog log;

  public Account(RecordLog log)
  {
    this.log = log;
  }

  public void someOperation() throws Exception
  {
    int c = log.getRecordCount();
    log.logInfo("Performing an operation");
    if (c+1 != log.getRecordCount())
      throw new Exception();
    if (log.getRecordCount() >= log.getRecordLimit())
      throw new Exception();
  }
}

class NullRecordLog implements RecordLog
{
    private int count = 0;
    
    public int getRecordLimit() {
        return count+1;
    }

    public int getRecordCount() {
      return count;
    }

    public void logInfo(String message) {
      this.count++;
    }
}


public class NullObjectDemo {
	
	@SuppressWarnings("unchecked")
	public static <T> T noOp(Class<T> itf) {
		return (T) Proxy.newProxyInstance(
					itf.getClassLoader(), 
					new Class<?>[] {itf}, 
					( (proxy, method, args) -> {
					     if (method.getReturnType().equals(Void.TYPE))
					    	 return null;
					     else
					    	 return method.getReturnType().getConstructor().newInstance();
					  }
					)
				);
	}
	


	public static void main(String[] args) {
		Log cl = new ConsoleLog();
		BankAccount ba = new BankAccount(cl); //what if i dont want to pass a log
		ba.deposit(100);	
		
		Log nl = new NullLog();
		BankAccount ba2 = new BankAccount(nl); //passing a null object
		ba2.deposit(200);	
		
		
		Log log = noOp(Log.class);
		BankAccount ba3 = new BankAccount(log); //passing a null object
		ba3.deposit(2540);
		
	}

}
