package SCIT_OS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);// function used to capture input
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		Random rand = new Random();// function used to generate random numbers

		final int SHARED_LIST_COUNT = 20;// default number of elements in shared  list
		final int MAX = 40;// maximum number of in simulation
		final int MAX_NUM_PROCESS = 20;// maximum number of process in OS
		final int MAX_CONCURRENT_OPERATIONS = 5;// maximum number of concurrent operations

		ListElement[] listElement = new ListElement[50];// initial size of shared list
		Integer[] RID = new Integer[MAX_NUM_PROCESS];// Random process ID variable
		Process[] process;// process

		int Nprocess = 0;// number of process
		int[] RecycleId = new int[MAX_CONCURRENT_OPERATIONS];// pass ID from ProcessQueue to new Process from ProcessWaitingQueue

		boolean postback = false;//used when user min/max criteria is not met
		
		try {
			WriteFile.printwriter.println("\t\t\t\t**********  SIMULATION REPORT  **********");//write file header
			
			for (int i = 0; i < SHARED_LIST_COUNT; i++) {//initialize SharedList
				listElement[i] = new ListElement();
				listElement[i].setValue((rand.nextInt(100)) + 1);
				SharedList.insert(listElement[i]);
			}
			SharedList.writeSharedList();//write shared list to file
			
			System.out.println("***************************");//program header
			System.out.println("********* SCIT OS *********");
			System.out.println("***************************");
			
			SharedList.display();//display SharedList

			do {
				if (postback == true) {
					System.out.println("Min[10] Max[30]");
				}
				System.out.println("# of Processes: ");
				Nprocess = in.nextInt();
				postback = true;
			} while (Nprocess > MAX | Nprocess < MAX_NUM_PROCESS);// min/max value condition

			process = new Process[Nprocess];// initialize process array data structure

			for (int i = 0; i < RID.length; i++) {// get process ID
				RID[i] = i;
			}
			Collections.shuffle(Arrays.asList(RID)); // randomize (shuffle) process ID

			for (int i = 0; i < MAX_NUM_PROCESS; i++) {// populate Process queue data structure
				process[i] = new Process();
				process[i].setPID(RID[i]);
				process[i].setTask((rand.nextInt(5) + 1));
				process[i].setPriority((rand.nextInt(5) + 1));
				process[i].setCreateTime(LocalDateTime.now());
				process[i].setSleepTime((rand.nextInt(5) + 1));

				ProcessQueue.enqueue(process[i]);// insert into process queue data structure
			}
			System.out.println("PROCESSES CREATED SUCCESSFULLY\n");
			
	
	        
			WriteFile.printwriter.println("\nPROCESS QUEUE");//file operation
			ProcessQueue.writeProcessQueue();//file operation

			if (Nprocess > MAX_NUM_PROCESS) {//populate Process waiting queue data structure
				for (int i = MAX_NUM_PROCESS; i < Nprocess; i++) {
					process[i] = new Process();
					process[i].setTask(rand.nextInt(5) + 1);
					process[i].setPriority((rand.nextInt(5) + 1));
					process[i].setCreateTime(LocalDateTime.now());
					process[i].setSleepTime((rand.nextInt(5) + 1));

					ProcessWaitingQueue.enqueue(process[i]);// insert into process waiting queue data structure
				}
				System.out.println("\nProcess Waiting Queue");
				ProcessWaitingQueue.display();//display process waiting queue

				WriteFile.printwriter.println("\nPROCESS WAITING QUEUE");//file operation
				ProcessWaitingQueue.writeProcessWaitingQueue();;//file operation
			}
			while (true) {
				// PROCESS QUEUE OPERATION
				if (ProcessQueue.Count == 0 && ProcessWaitingQueue.Count == 0) {// break if both queues are empty
					break;
				} else if (ProcessQueue.Count >= MAX_CONCURRENT_OPERATIONS) {// Process  a maximum 5 processes at a time
					for (int i = 0; i < MAX_CONCURRENT_OPERATIONS; i++) {
						RecycleId[i] = ProcessQueue.getFront().getData().getPID();// get process id from existing process 
						ProcessQueue.getFront().getData().start();// EXECUTE THREAD(Remove from process queue)
					}
				} else if (ProcessQueue.Count < MAX_CONCURRENT_OPERATIONS) {// if there is less then than 5 processes left in the process queue
					int i = 0;// index increment variable
					while (ProcessQueue.Count != 0) {
						RecycleId[i] = ProcessQueue.getFront().getData().getPID();// get process ID from exiting process
						ProcessQueue.getFront().getData().start();// EXECUTE THREAD(Remove from process queue)
				  		i++;
					}
				}
				// PROCESS WAITING QUEUE OPERATION
				if (ProcessWaitingQueue.getCount() != 0) {// if process exist in process queue
					if (ProcessWaitingQueue.getCount() >= MAX_CONCURRENT_OPERATIONS) {
						for (int i = 0; i < MAX_CONCURRENT_OPERATIONS; i++) {// add only 5 to process list REMOVE FROM WAITING QUEUE AND INSERT INTO PROCESS QUEUE
							ProcessQueue.enqueue(ProcessWaitingQueue.dequeue());// INSERT INTO PROCESS QUEUE
							ProcessQueue.getBack().getData().setPID(RecycleId[i]);// update Id
						}
					} else if (ProcessWaitingQueue.getCount() < MAX_CONCURRENT_OPERATIONS) {// insert remaining processes to process
						int i = 0;// index increment variable
						while (ProcessWaitingQueue.getCount() != 0) {
							ProcessQueue.enqueue(ProcessWaitingQueue.dequeue());// INSERT INTO PROCESS QUEUE
							ProcessQueue.getBack().getData().setPID(RecycleId[i]);// update Id
							i++;
						}
					}
				}
			}
			WriteFile.printwriter.println("\n\n**********  AFTER EXECUTION OF PROCESSES  **********\n");//file operation
			SharedList.writeSharedList();//file operation
			System.out.print("\n\nProcess ID \t   Task \t Priority \t  Create Time \t\t\t\t     Start Time \t\t\t\tEnd Time\t\t\t     Attempts\t\t Sleep Time \n");
			WriteFile.printwriter.print("\n\nProcess ID \t   Task \t Priority \t\t  Create Time \t\t\t\t\t       Start Time \t\t\t\t\tEnd Time\t\t\t          Attempts\t\tSleep Time \n");
			for (int i = 0; i < Nprocess; i++) {//display process report
				process[i].displayList();
			}
			WriteFile.printwriter.close();//file operation
			
			System.out.println("\nThank you for using SCIT OS");
			System.out.println("A report was generated for your viewing");

		} catch (InputMismatchException ime) {
			System.out.println("Program sequence interupted");
			System.out.println("Enter a valid number next time");
		}
	}
}