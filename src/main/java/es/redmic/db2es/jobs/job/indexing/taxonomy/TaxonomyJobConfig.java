package es.redmic.db2es.jobs.job.indexing.taxonomy;

import org.springframework.batch.core.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db2es.jobs.job.common.MultiJobBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;

@Configuration
public class TaxonomyJobConfig extends MultiJobBase {

	private static final String JOB_NAME = JobsNames.TAXONOMY.toString();

	@Bean
	protected Job taxonomyJob() {
		return createJob(JOB_NAME, TaxonomyJobName.class);
	}

}
