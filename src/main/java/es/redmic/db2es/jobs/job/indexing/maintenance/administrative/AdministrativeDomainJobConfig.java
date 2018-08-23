package es.redmic.db2es.jobs.job.indexing.maintenance.administrative;

import org.springframework.batch.core.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db2es.jobs.job.common.MultiJobBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;

@Configuration
public class AdministrativeDomainJobConfig extends MultiJobBase {

	private static final String JOB_NAME = JobsNames.ADMINISTRATIVE_DOMAINS.toString();

	@Bean
	protected Job administrativeDomainJob() {
		return createJob(JOB_NAME, AdministrativeDomainJobName.class);
	}
}
