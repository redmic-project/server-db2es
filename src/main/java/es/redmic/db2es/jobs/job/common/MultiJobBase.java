package es.redmic.db2es.jobs.job.common;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


public abstract class MultiJobBase extends JobBase {

	@Autowired
	ApplicationContext ctx;

	@SuppressWarnings("rawtypes")
	protected Job createJob(String jobName, Class<? extends JobsNamesBase> enumerate) {
		JobBuilder jobBuilder = createJobBuilder(jobName);
		SimpleJobBuilder job = addSteps(jobBuilder, enumerate);

		return job.build();
	}

	private JobBuilder createJobBuilder(String jobName) {
		JobBuilder jobBuilder = jobBuilderFactory.get("indexing-" + jobName + "-job").listener(jobListener)
				.incrementer(new JobIncrementer());

		return jobBuilder;
	}

	@SuppressWarnings("rawtypes")
	private SimpleJobBuilder addSteps(JobBuilder jobBuilder, Class<? extends JobsNamesBase> enumerate) {
		SimpleJobBuilder job = null;

		for (JobsNamesBase jobName : enumerate.getEnumConstants()) {
			Step step = createStep(jobName.toString());

			if (job == null)
				job = jobBuilder.start(step);
			else
				job.next(step);
		}

		return job;
	}

	private Step createStep(String jobName) {
		Job job = (Job) ctx.getBean(jobName + "Job");
		Step step = stepBuilderFactory.get("indexing-" + jobName + "-job-step").job(job).build();

		return step;
	}

}
