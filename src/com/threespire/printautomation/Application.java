package com.threespire.printautomation;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Application {

	/*
	 * public static void main(String[] args) { //new
	 * PrintController().printOrder(true, "101");
	 * 
	 * Timer timer = new Timer(); TimerTask task = new TimerTask() {
	 * 
	 * @Override public void run() { myMethod(); } };
	 * 
	 * timer.schedule(task, 0);
	 * 
	 * }
	 * 
	 * public static void myMethod(){ System.out.println("Time: "+ new Date());
	 * }
	 */

	long delay = 10 * 1000; // delay in milliseconds
	LoopTask task = new LoopTask();
	Timer timer = new Timer("TaskName");

	public void start() {
		timer.cancel();
		timer = new Timer("TaskName");
		Date executionDate = new Date(); // no params = now
		timer.scheduleAtFixedRate(task, executionDate, delay);
	}

	private class LoopTask extends TimerTask {
		public void run() {
			System.out.println("This message will print every 10 seconds.");
		}
	}

	public static void main(String[] args) {
		Application executingTask = new Application();
		executingTask.start();
	}

}
