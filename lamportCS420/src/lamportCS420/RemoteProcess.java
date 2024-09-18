package lamportCS420;

import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class RemoteProcess extends UnicastRemoteObject implements RemoteProcessInterface {

	private int processId;
	private VectorClock vectorClock;
	
	public RemoteProcess(int processId, int processes) throws RemoteException {
		this.processId = processId;
		this.vectorClock = new VectorClock(processes);
	}
	
	public VectorClock getVectorClock() throws RemoteException {
		return vectorClock;
	}
	
	public void sendEvent(int eventId, String data, String targetProcessName) throws RemoteException {
	        //Increment the clock of sending process
	        vectorClock.increment(processId);

	        try {
	            //Lookup the target (receiving) process
	        	RemoteProcessInterface targetProcess = (RemoteProcessInterface) Naming.lookup("rmi://localhost/" + targetProcessName);
	        	
	            //Get local clock value
	            int[] localClock = vectorClock.getClock();

	            //Logging event and clock on server side
	            System.out.println("Sending event " + eventId + " with data: " + data + " and clock: " + Arrays.toString(localClock));
	            
	            //Update clock of target process
	            targetProcess.getVectorClock().update(localClock);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
    }

	@Override
	public void update(VectorClockInterface remoteClock) throws RemoteException {
		vectorClock.update(remoteClock.getClock());
        System.out.println("Vector clock updated after receiving message: " + java.util.Arrays.toString(vectorClock.getClock()));
		
	}
	
}
