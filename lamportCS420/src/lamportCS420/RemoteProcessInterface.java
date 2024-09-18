package lamportCS420;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteProcessInterface extends Remote {
	VectorClock getVectorClock() throws RemoteException;
	void sendEvent (int eventId, String data, String targetProcessName) throws RemoteException;
	void update(VectorClockInterface remoteClock) throws RemoteException;
}
