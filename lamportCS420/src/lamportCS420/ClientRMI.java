package lamportCS420;

import java.rmi.Naming;

public class ClientRMI {

	public static void main(String[] args) {
		try {
			//Lookup the remote processes from the RMI registry
            RemoteProcessInterface process1 = (RemoteProcessInterface) Naming.lookup("rmi://localhost/Process1");
            RemoteProcessInterface process2 = (RemoteProcessInterface) Naming.lookup("rmi://localhost/Process2");
            RemoteProcessInterface process3 = (RemoteProcessInterface) Naming.lookup("rmi://localhost/Process3");

            //Example of internal events and message sending
            //Process 1 Internal event
            process1.sendEvent(1, "P1 Internal Event", "Process1");
            System.out.println("Process 1 after internal event: " + java.util.Arrays.toString(process1.getVectorClock().getClock()));

            //Process 2 Internal event
            process2.sendEvent(2, "P2 Internal Event", "Process2");
            System.out.println("Process 2 after internal event: " + java.util.Arrays.toString(process2.getVectorClock().getClock()));

            //Process 1 sends event to Process 2
            process1.sendEvent(3, "Message from P1 to P2", "Process2");
            //When sending messages, receiving process updates its clock
            process2.update(process1.getVectorClock());
            System.out.println("Process 1 after sending message to Process 2: " + java.util.Arrays.toString(process1.getVectorClock().getClock()));
            System.out.println("Process 2 after receiving message from Process 1: " + java.util.Arrays.toString(process2.getVectorClock().getClock()));

            //Process 3 Internal event
            process3.sendEvent(4, "P3 Internal Event", "Process3");
            System.out.println("Process 3 after internal event: " + java.util.Arrays.toString(process3.getVectorClock().getClock()));

            //Process 3 sends event to Process 1
            process3.sendEvent(5, "Message from P3 to P1", "Process1");
            process1.update(process3.getVectorClock());
            System.out.println("Process 3 after sending message to Process 1: " + java.util.Arrays.toString(process3.getVectorClock().getClock()));
            System.out.println("Process 1 after receiving message from Process 3: " + java.util.Arrays.toString(process1.getVectorClock().getClock()));

            //Process 2 sends event to Process 3
            process2.sendEvent(6, "Message from P2 to P3", "Process3");
            process3.update(process2.getVectorClock());
            System.out.println("Process 2 after sending message to Process 3: " + java.util.Arrays.toString(process2.getVectorClock().getClock()));
            System.out.println("Process 3 after receiving message from Process 2: " + java.util.Arrays.toString(process3.getVectorClock().getClock()));
            
        } catch (Exception e) {
            e.printStackTrace();
        }

	}

}
