package es.redmic.db2es.jobs.job.indexing.maintenance.quality;

import org.springframework.batch.core.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db2es.jobs.job.common.MultiJobBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;

@Configuration
public class QualityDomainJobConfig extends MultiJobBase {

	private static final String JOB_NAME = JobsNames.QUALITY_DOMAINS.toString();

	@Bean
	protected Job qualityDomainJob() {
		return createJob(JOB_NAME, QualityDomainJobName.class);
	}

}
