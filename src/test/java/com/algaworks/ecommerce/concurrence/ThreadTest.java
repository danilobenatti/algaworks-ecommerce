package com.algaworks.ecommerce.concurrence;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.Thread.State;
import java.time.Duration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;

class ThreadTest extends EntityManagerTest {
	
	private static void log(Object obj, Object... args) {
		logger.log(Level.INFO, new StringFormattedMessage("[%d] %s",
			System.currentTimeMillis(), obj.toString()));
	}
	
	private static void wait(Duration seconds) {
		await().atMost(Duration.ofSeconds(15)).during(seconds)
			.until(() -> true);
	}
	
	@Test
	void usingLock() {
		Runnable runnable_1 = () -> {
			log("Runnable A: waiting 7 seconds");
			wait(Duration.ofSeconds(7));
			log("Runnable A: concluded");
		};
		Runnable runnable_2 = () -> {
			log("Runnable B: waiting 3 seconds");
			wait(Duration.ofSeconds(3));
			log("Runnable B: concluded");
		};
		Runnable runnable_3 = () -> {
			log("Runnable C: waiting 12 seconds");
			wait(Duration.ofSeconds(12));
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
