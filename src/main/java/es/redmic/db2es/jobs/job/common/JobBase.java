package es.redmic.db2es.jobs.job.common;

import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db2es.jobs.listener.JobListener;
import es.redmic.db2es.jobs.listener.LogProcessListener;

public abstract class JobBase {
	
	@Autowired
	protected JobBuilderFactory jobBuilderFactory;

	@Autowired
	protected StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	protected JobListener jobListener;
	
	@Autowired
	protected LogProcessListener logProcessListener;
	
	@Autowired
	protected StepExecutionListener stepExecutionListener;

}
