package es.redmic.db2es.jobs.job.indexing.maintenance.classifications;

import org.springframework.batch.core.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db2es.jobs.job.common.MultiJobBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;

@Configuration
public class ClassificationsDomainJobConfig extends MultiJobBase {

	private static final String JOB_NAME = JobsNames.CLASSIFICATION_DOMAINS.toString();

	@Bean
	protected Job classificationsDomainJob() {
		return createJob(JOB_NAME, ClassificationsDomainJobName.class);
	}

}
