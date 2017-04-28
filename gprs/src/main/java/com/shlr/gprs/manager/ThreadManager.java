package com.shlr.gprs.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 */

public class ThreadManager {

	private ExecutorService executor = Executors.newCachedThreadPool();

	public static ThreadManager getInstance() {
		return ThreadManagerHolder.manager;
	}

	private static class ThreadManagerHolder {
		static ThreadManager manager = new ThreadManager();
	}

	public void execute(Runnable runnable) {
		executor.execute(runnable);
	}

	public void shutDown() {
		executor.shutdown();
	}
}
