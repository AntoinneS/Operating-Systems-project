package SCIT_OS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class Process implements Runnable {
	Scanner in = new Scanner(System.in);
	Random rand = new Random();

	Thread thread;

	private int PID;
	private int Task;
	private int Priority;
	private LocalDateTime CreateTime;
	private LocalDateTime StartTime;
	private LocalDateTime EndTime;
	private int Attempts;
	private int SleepTime;
	
	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");

	// Constructors
	public Process() {
		thread = null;

		PID = 0;
		Task = 0;
		Priority = 0;
		CreateTime = null;
		StartTime = null;
		EndTime = null;
		Attempts = 0;
		SleepTime = 0;
	}

	// Getters/Setters
	public int getPID() {
		return PID;
	}

	public void setPID(int pID) {
		PID = pID;
	}

	public int getTask() {
		return Task;
	}

	public void setTask(int task) {
		Task = task;
	}

	public int getPriority() {
		return Priority;
	}

	public void setPriority(int priority) {
		Priority = priority;
	}

	public LocalDateTime getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		CreateTime = createTime;
	}

	public LocalDateTime getStartTime() {
		return StartTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		StartTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return EndTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		EndTime = endTime;
	}

	public int getAttempts() {
		return Attempts;
	}

	public void setAttempts(int attempts) {
		Attempts = attempts;
	}

	public int getSleepTime() {
		return SleepTime;
	}

	public void setSleepTime(int sleepTime) {
		SleepTime = sleepTime;
	}

	public void displayShort() {
		String task = null;
		if (Task==1){
			task="Add  ";
		}else if (Task == 2){
			task="Copy ";
		}else if (Task == 3){
			task="Display";
		}
		System.out.println(PID + "\t\t\t" + task + "\t\t   " + Priority);
	}
	
	public void writeProcessShort() {
		String task = null;
		if (Task==1){
			task="Add  ";
		}else if (Task == 2){
			task="Copy  ";
		}else if (Task == 3){
			task="Dispay";
		}
		WriteFile.printwriter.println(PID + "\t\t\t" + task + "\t\t   " + Priority);
	}

	public void displayFull() {
		String task = null;
		if (Task==1){
			task="Add";
		}else if (Task == 2){
			task="Copy";
		}else if (Task == 3){
			task="Display";
		}
		System.out.print("\n   "+PID+"\t\t   "+task+"\t     "+Priority+"\t     "+dtf.format(CreateTime)+"\t\t	"+StartTime+"\t\t	"+EndTime+"\t\t		"+Attempts+"\t\t     "+SleepTime+"\n");
	}
	
	public void displayList(){
		String task = null;
		if (Task==1){
			task="Add";
		}else if (Task == 2){
			task="Copy";
		}else if (Task == 3){
			task="Display";
		}
		System.out.print("\n   "+PID+"\t\t   "+task+"\t     "+Priority+"\t     "+dtf.format(CreateTime)+"\t\t	"+dtf.format(StartTime)+"\t\t	"+dtf.format(EndTime)+"\t\t		"+Attempts+"\t\t     "+SleepTime+"\n");
		WriteFile.printwriter.print("\n   "+PID+"\t\t   "+task+"\t     "+Priority+"\t     "+dtf.format(CreateTime)+"\t\t	"+dtf.format(StartTime)+"\t\t	"+dtf.format(EndTime)+"\t\t		"+Attempts+"\t\t     "+SleepTime+"\n");
	}

	@Override
	public void run() {
		try {
			Attempts+=1;
			System.out.println("Process "+PID+" exection START");
			if (Task == 1) {
				int value=(rand.nextInt(100) + 1);
				SharedList.insert(value);
				System.out.println(value+" inserted in shared list");

			} else if (Task == 2) {
				SharedList.remove();

			} else if (Task == 3) {
				System.out.println("Enter search key: ");
				int key = in.nextInt();
				SharedList.retrieve(key);

			} else if (Task == 4) {
				SharedList.sort();
				System.out.println("Shared list sorted by value");

			} else if (Task == 5) {
				SharedList.calculateTotal();

			}
		} catch (Exception e) {
			System.out.println("Thread " + PID + " interrupted.");
		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			StartTime=LocalDateTime.now();//set start time for process
			thread.start();
			try {
				Thread.sleep((SleepTime*1000));//sleep 
				thread.join();//wait for thread to execute to completion

			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			EndTime=LocalDateTime.now();//after execution, set end time for process
			
			ProcessQueue.dequeue();//after execution, remove process from process list
			
			System.out.println("Process "+PID+" execution COMPLETE\n\n");
		}
	}
}