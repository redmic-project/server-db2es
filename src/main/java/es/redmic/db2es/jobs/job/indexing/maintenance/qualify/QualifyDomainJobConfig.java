package es.redmic.db2es.jobs.job.indexing.maintenance.qualify;

import org.springframework.batch.core.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db2es.jobs.job.common.MultiJobBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;

@Configuration
public class QualifyDomainJobConfig extends MultiJobBase {

	private static final String JOB_NAME = JobsNames.QUALIFY_DOMAINS.toString();

	@Bean
	protected Job qualifyDomainJob() {
		return createJob(JOB_NAME, QualifyDomainJobName.class);
	}
}
