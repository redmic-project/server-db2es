package es.redmic.db2es.jobs.job.indexing.maintenance.toponymias.toponym;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.geodata.toponym.model.Toponym;
import es.redmic.db2es.jobs.job.common.JobBase;
import es.redmic.db2es.jobs.job.common.JobIncrementer;
import es.redmic.db2es.jobs.job.indexing.maintenance.toponymias.ToponymiasJobName;

@Configuration
public class ToponymJobConfig extends JobBase {

	private static final String JOB_NAME = ToponymiasJobName.TOPONYM.toString();

	private ToponymItemReader itemReader;
	private ToponymProcessor itemProcessor;
	private ToponymItemWriter itemWriter;

	@Autowired
	public ToponymJobConfig(ToponymItemReader itemReader, ToponymProcessor itemProcessor,
			ToponymItemWriter itemWriter) {

		this.itemReader = itemReader;
		this.itemProcessor = itemProcessor;
		this.itemWriter = itemWriter;
	}

	@Bean
	public Job toponymJob() {
		return createJobIndexing(JOB_NAME).start(getToponymStep()).build();
	}

	@Bean
	@StepScope
	public Step getToponymStep() {
		return createStepIndexing(JOB_NAME);
	}

	// TODO: para no crear una base solo para toponym, se hace aquí. Cambiar si
	// aparece otro caso.
	protected JobBuilder createJobIndexing(String name) {
		return jobBuilderFactory.get("indexing-" + name + "-job").listener(jobListener)
				.incrementer(new JobIncrementer());
	}

	protected Step createStepIndexing(String name) {
		return stepBuilderFactory.get("indexing-" + name + "-step")
				.listener(stepExecutionListener).<Toponym, es.redmic.models.es.geojson.toponym.model.Toponym>chunk(10)
				.reader(itemReader).processor(itemProcessor).writer(itemWriter).listener(logProcessListener)
				.faultTolerant().build();
	}
}
