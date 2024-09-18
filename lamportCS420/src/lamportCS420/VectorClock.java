package lamportCS420;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class VectorClock implements VectorClockInterface, Serializable {

	private int[] clock;
	private static final long serialVersionUID = 1L;
	
	public VectorClock(int processes) throws RemoteException {
		//Constructor initializes a new array full of 0s
		clock = new int[processes];
		Arrays.fill(clock, 0);
	}
	
	@Override
	public int[] getClock() throws RemoteException {
		return clock.clone();
	}

	@Override
	public void increment(int processId) throws RemoteException {
		clock[processId]++;
	}

	@Override
	public void update(int[] remoteClock) throws RemoteException {
		//Updates vector clock when messages are sent from another process by comparing and taking max 
		for (int i=0; i<clock.length; i++) {
			clock[i] = Math.max(clock[i], remoteClock[i]);
		}
	}

}
