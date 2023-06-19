package com.algaworks.ecommerce.concurrence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.Thread.State;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;

class ThreadTest extends EntityManagerTest {
	
	private static final Long timeout = 15L;
	
	@Test
	void usingLock() {
		Runnable runnable_1 = () -> {
			log("Runnable A: waiting 7 seconds");
			waiting(7L, timeout);
			log("Runnable A: concluded");
		};
		Runnable runnable_2 = () -> {
			log("Runnable B: waiting 3 seconds");
			waiting(3L, timeout);
			log("Runnable B: concluded");
		};
		Runnable runnable_3 = () -> {
			log("Runnable C: waiting 12 seconds");
			waiting(12L, timeout);
			log("Runnable C: concluded");
		};
		
		Thread thread_1 = new Thread(runnable_1);
		Thread thread_2 = new Thread(runnable_2);
		Thread thread_3 = new Thread(runnable_3);
		
		thread_1.start();
		thread_2.start();
		thread_3.start();
		
		try {
			thread_1.join();
			thread_2.join();
			thread_3.join();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
		log("Execution Completed");
		
		assertEquals(State.TERMINATED, thread_1.getState());
		assertEquals(State.TERMINATED, thread_2.getState());
		assertEquals(State.TERMINATED, thread_3.getState());
	}
	
}
