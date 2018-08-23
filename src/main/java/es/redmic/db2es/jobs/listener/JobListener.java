package es.redmic.db2es.jobs.listener;

import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import es.redmic.db2es.dto.EventTask;

@Component
public class JobListener implements JobExecutionListener {


	private static final Logger LOGGER = LoggerFactory.getLogger(JobListener.class);
	
	final ApplicationEventPublisher publisher;
	

	public JobListener(ApplicationEventPublisher publisher){
		this.publisher = publisher;
	}

	public void afterJob(JobExecution jobExecution) {
		
		publisher.publishEvent(new EventTask(publisher, jobExecution));
		
		LOGGER.info(generateLogEndJob(jobExecution));
	}

	public void beforeJob(JobExecution jobExecution) {
	
		publisher.publishEvent(new EventTask(publisher, jobExecution));
		
		LOGGER.info(generateLogStartJob(jobExecution));
	}

	
	private String generateLogStartJob(JobExecution jobExecution) {
		StringBuilder protocol = new StringBuilder();
		protocol.append("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
		protocol.append("Protocol for " + jobExecution.getJobInstance().getJobName() + " \n");
		
		return protocol.toString();
	}
	
	private String generateLogEndJob(JobExecution jobExecution) {
		StringBuilder protocol = new StringBuilder();
		protocol.append("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
		protocol.append("Protocol for " + jobExecution.getJobInstance().getJobName() + " \n");
		protocol.append("  Started     : " + jobExecution.getStartTime() + "\n");
		protocol.append("  Finished    : " + jobExecution.getEndTime() + "\n");
		protocol.append("  Exit-Code   : " + jobExecution.getExitStatus().getExitCode() + "\n");
		protocol.append("  Exit-Descr. : " + jobExecution.getExitStatus().getExitDescription() + "\n");
		protocol.append("  Status      : " + jobExecution.getStatus() + "\n");
		protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");

		protocol.append("Job-Parameter: \n");		
		JobParameters jp = jobExecution.getJobParameters();
		for (Iterator<Entry<String,JobParameter>> iter = jp.getParameters().entrySet().iterator(); iter.hasNext();) {
			Entry<String,JobParameter> entry = iter.next();
			protocol.append("  " + entry.getKey() + "=" + entry.getValue() + "\n");
		}
		protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");		
		
		for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
			protocol.append("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
			protocol.append("Step " + stepExecution.getStepName() + " \n");
			protocol.append("WriteCount: " + stepExecution.getWriteCount() + "\n");
			protocol.append("Commits: " + stepExecution.getCommitCount() + "\n");
			protocol.append("SkipCount: " + stepExecution.getSkipCount() + "\n");
			protocol.append("Rollbacks: " + stepExecution.getRollbackCount() + "\n");
			protocol.append("Filter: " + stepExecution.getFilterCount() + "\n");					
			protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
		}
		
		return protocol.toString();
	}
}
