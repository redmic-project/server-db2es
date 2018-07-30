package es.redmic.db2es.jobs.job.indexing.maintenance.toponymias;

import org.springframework.batch.core.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db2es.jobs.job.common.MultiJobBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;

@Configuration
public class ToponymiasJobConfig extends MultiJobBase {

	private static final String JOB_NAME = JobsNames.TOPONYMIAS.toString();

	@Bean
	protected Job toponymiasDomainJob() {
		return createJob(JOB_NAME, ToponymiasJobName.class);
	}
}
