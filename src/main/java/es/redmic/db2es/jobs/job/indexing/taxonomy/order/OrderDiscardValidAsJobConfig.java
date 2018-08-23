package es.redmic.db2es.jobs.job.indexing.taxonomy.order;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.taxonomy.model.Orderr;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.taxonomy.TaxonomyJobName;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

@Configuration
public class OrderDiscardValidAsJobConfig extends OrderCommonJobConfig {

	private static final String JOB_NAME = TaxonomyJobName.ORDER_DISCARD_VALID_AS.toString();

	public OrderDiscardValidAsJobConfig() {
		super(JOB_NAME);
	}

	@Bean
	public Job orderDiscardValidAsJob() {

		return createJobIndexing().start(orderDiscardValidAsStep()).build();
	}

	@Bean
	public Step orderDiscardValidAsStep() {

		return createStepIndexing(orderDiscardValidAsItemReader(), orderDiscardValidAsProccessor(), orderDiscardValidAsItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Orderr> orderDiscardValidAsItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ItemWriterBySave<Taxon> orderDiscardValidAsItemWriter() {

		return createItemWriter(service);
	}

	@Bean
	public OrderDiscardValidAsProcessor orderDiscardValidAsProccessor() {

		return new OrderDiscardValidAsProcessor();
	}
}
