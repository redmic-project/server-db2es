package es.redmic.db2es.jobs.job.indexing.administrative;

import org.springframework.batch.core.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db2es.jobs.job.common.MultiJobBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;

@Configuration
public class AdministrativeJobConfig extends MultiJobBase {

	private static final String JOB_NAME = JobsNames.ADMINISTRATIVE.toString();

	@Bean
	public Job administrativeJob() {
		return createJob(JOB_NAME, AdministrativeJobName.class);
	}
}
