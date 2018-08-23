package es.redmic.db2es.dto;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.context.ApplicationEvent;

public class EventTask extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7470572218665003215L;
	
	StatusTask task;
	
	public EventTask(Object source, JobExecution job) {
		super(source);
		task = new StatusTask(job);
	}
	
	public EventTask(Object source, StepExecution step) {
		super(source);
		task = new StatusTask(step);
	}

	public StatusTask getTask() {
		return task;
	}

	public void setTask(StatusTask task) {
		this.task = task;
	}
	
	

}
