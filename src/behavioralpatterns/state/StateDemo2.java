package behavioralpatterns.state;

import java.text.*;
import java.util.*;
import java.io.*;
import org.javatuples.*;

enum PhoneState {
	OFF_HOOK,  // starting
	ON_HOOK,    // final state
	CONNECTING,
	CONNECTED,
	ON_HOLD
}

enum Trigger {
	CALL_DIALED,
	HUNG_UP,
	CALL_CONNECTED,
	PLACED_ON_HOLD,
	TAKEN_OFF_HOLD,
	LEFT_MESSAGE,
	STOP_USING_PHONE
}

public class StateDemo2 {
	
	private static Map<PhoneState, List<Pair<Trigger, PhoneState>>> rules = new HashMap<>();
	
	static {
		rules.put(PhoneState.OFF_HOOK, Arrays.asList(
		  new Pair<>(Trigger.CALL_DIALED, PhoneState.CONNECTING),
		  new Pair<>(Trigger.STOP_USING_PHONE, PhoneState.ON_HOOK)
		));
		
		rules.put(PhoneState.CONNECTING, Arrays.asList(
		  new Pair<>(Trigger.HUNG_UP, PhoneState.OFF_HOOK),
		  new Pair<>(Trigger.CALL_CONNECTED, PhoneState.CONNECTED)
		));
		
		rules.put(PhoneState.CONNECTED, Arrays.asList(
		  new Pair<>(Trigger.LEFT_MESSAGE, PhoneState.OFF_HOOK),
		  new Pair<>(Trigger.HUNG_UP, PhoneState.OFF_HOOK),
		  new Pair<>(Trigger.PLACED_ON_HOLD, PhoneState.ON_HOLD)
		));
		
		rules.put(PhoneState.ON_HOLD, Arrays.asList(
		  new Pair<>(Trigger.TAKEN_OFF_HOLD, PhoneState.CONNECTED),
		  new Pair<>(Trigger.HUNG_UP, PhoneState.OFF_HOOK)
	    ));
	}
	
	private static PhoneState currentState = PhoneState.OFF_HOOK;
	private static PhoneState exitState = PhoneState.ON_HOOK;

	public static void main(String[] args) {
		
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			System.out.println("The phone is currently " + currentState);
			System.out.println("Select a trigger:"); 
			
			for (int i = 0; i< rules.get(currentState).size(); ++i) {
				Trigger trigger = rules.get(currentState).get(i).getValue0();
			    System.out.println("" + i + ". " + trigger.name());
			}
			
			boolean parseOK;
			int choice = 0;
			do {
				try {
					System.out.println("Please enter your choice");
					choice = Integer.parseInt(console.readLine());
					parseOK = choice >= 0 && choice < rules.get(currentState).size();
				} catch(Exception e) {
					parseOK =false;
				}
			} while (!parseOK);
			
			currentState = rules.get(currentState).get(choice).getValue1();
			
			if (currentState == exitState)
				break;
			
		}
		System.out.println("Terminated!");
	}

}
