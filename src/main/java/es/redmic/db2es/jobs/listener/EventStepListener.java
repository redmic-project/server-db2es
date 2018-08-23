package es.redmic.db2es.jobs.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.ApplicationEventPublisher;

import es.redmic.db2es.dto.EventTask;

public class EventStepListener implements StepExecutionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventStepListener.class);
	
	final ApplicationEventPublisher publisher;
	
	public EventStepListener(ApplicationEventPublisher publisher) {
		super();
		this.publisher = publisher;
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		LOGGER.info("After step: " + stepExecution.getSummary());
		
		publisher.publishEvent(new EventTask(publisher, stepExecution));
		
		return null;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		LOGGER.info("Before step: " + stepExecution.getSummary());
		
		publisher.publishEvent(new EventTask(publisher, stepExecution));
		
	}
	
}
