package com.temula.image;

import java.util.logging.Logger;

public class Clock{
	static final Logger logger = Logger.getLogger(ImageResource.class.getName());
	long start;
	long last;
	public Clock(){
		start=System.currentTimeMillis();
		last=start;
	}
	public void lap(){
		long current = System.currentTimeMillis();
		last=current;
	}
	public void lap(String description){
		long current = System.currentTimeMillis();
		logger.info(description +" ("+(current-last)+")");
		last=current;
	}
	
	public void stop(){
		long current = System.currentTimeMillis();
		logger.info("total time: ("+(current-start)+")");
		start=System.currentTimeMillis();
		last=start;
	}
	
}
