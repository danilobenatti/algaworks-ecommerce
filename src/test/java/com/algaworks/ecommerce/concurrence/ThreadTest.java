package com.algaworks.ecommerce.concurrence;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.Thread.State;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;

class ThreadTest extends EntityManagerTest {
	
	private static void log(Object obj, Object... args) {
		Message message = new StringFormattedMessage("[%d] %s",
			System.currentTimeMillis(), obj.toString());
		logger.log(Level.INFO, message);
	}
	
	private static void waiting(long seconds) {
		await().atMost(seconds, TimeUnit.SECONDS);
	}
	
	@Test
	void usingLock() {
		Runnable runnable1 = () -> {
			log("Runnable A: waiting 5 seconds");
			waiting(5);
			log("Runnable A: concluded");
		};
		Runnable runnable2 = () -> {
			log("Runnable B: waiting 2 seconds");
			waiting(2);
			log("Runnable B: concluded");
		};
		
		Thread thread1 = new Thread(runnable1);
		Thread thread2 = new Thread(runnable2);
		
		thread1.start();
		thread2.start();
		
		try {
			thread1.join();
			thread2.join();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
		log("Execution Completed");
		
		assertEquals(State.TERMINATED, thread1.getState());
		assertEquals(State.TERMINATED, thread2.getState());
	}
	
}
