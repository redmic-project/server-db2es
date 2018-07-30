package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy;

import org.springframework.batch.core.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db2es.jobs.job.common.MultiJobBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;

@Configuration
public class TaxonomyDomainJobConfig extends MultiJobBase {

	private static final String JOB_NAME = JobsNames.TAXONOMY_DOMAINS.toString();

	@Bean
	protected Job taxonomyDomainJob() {
		return createJob(JOB_NAME, TaxonomyDomainJobName.class);
	}
}
