package es.redmic.db2es.jobs.job.indexing.maintenance.parameters;

import org.springframework.batch.core.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db2es.jobs.job.common.MultiJobBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;

@Configuration
public class ParametersDomainJobConfig extends MultiJobBase {

	private static final String JOB_NAME = JobsNames.PARAMETERS_DOMAINS.toString();

	@Bean
	protected Job parametersDomainJob() {
		return createJob(JOB_NAME, ParametersDomainJobName.class);
	}
}
