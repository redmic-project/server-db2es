package es.redmic.db2es.jobs.job.indexing.atlas;

import org.springframework.batch.core.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db2es.jobs.job.common.MultiJobBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;

@Configuration
public class AtlasJobConfig extends MultiJobBase {

	private static final String JOB_NAME = JobsNames.ATLAS.toString();

	@Bean
	protected Job atlasJob() {
		return createJob(JOB_NAME, AtlasJobName.class);
	}

}
