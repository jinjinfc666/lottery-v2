package com.jll.common.threadpool;

public class ThreadParam {

	private static ThreadLocal<Object> threadLocal = new ThreadLocal<>();
	
	public static Object get() {
		return threadLocal.get();
	}
	
	public static void set(Object obj) {
		threadLocal.set(obj);
	}
}
