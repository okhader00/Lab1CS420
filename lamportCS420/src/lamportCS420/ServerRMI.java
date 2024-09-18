package lamportCS420;

import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;

public class ServerRMI {

	public static void main(String[] args) {
		try {
            LocateRegistry.createRegistry(1099);
            //Creating registry and our 3 processes
            RemoteProcess process1 = new RemoteProcess(0, 3);
            RemoteProcess process2 = new RemoteProcess(1, 3);
            RemoteProcess process3 = new RemoteProcess(2, 3);
            
            //Naming processes
            Naming.rebind("rmi://localhost/Process1", process1);
            Naming.rebind("rmi://localhost/Process2", process2);
            Naming.rebind("rmi://localhost/Process3", process3);
            
            //Server side message to ensure processes were properly initialized and
            System.out.println("Process 1 is running.");
            System.out.println("Process 2 is running.");	
            System.out.println("Process 3 is running.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
