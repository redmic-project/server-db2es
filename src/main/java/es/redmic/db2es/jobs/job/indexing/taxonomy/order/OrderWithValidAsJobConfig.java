package es.redmic.db2es.jobs.job.indexing.taxonomy.order;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.taxonomy.model.Orderr;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterByUpdate;
import es.redmic.db2es.jobs.job.indexing.taxonomy.TaxonomyJobName;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

@Configuration
public class OrderWithValidAsJobConfig extends OrderCommonJobConfig {

	private static final String JOB_NAME = TaxonomyJobName.ORDER_WITH_VALID_AS.toString();

	public OrderWithValidAsJobConfig() {
		super(JOB_NAME);
	}

	@Bean
	public Job orderWithValidAsJob() {

		return createJobIndexing().start(orderWithValidAsStep()).build();
	}

	@Bean
	public Step orderWithValidAsStep() {

		return createStepIndexing(orderWithValidAsItemReader(), orderWithValidAsProccessor(),
				orderItemWriterByUpdate());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Orderr> orderWithValidAsItemReader() {

		ItemReaderBase<Orderr> itemReader = createItemReader(repository);
		itemReader.setMethodName("findOrderWithValidAs");

		return itemReader;
	}

	@Bean
	public ItemWriterByUpdate<Taxon> orderItemWriterByUpdate() {

		return new ItemWriterByUpdate<Taxon>(service);
	}

	@Bean
	public OrderWithValidAsProcessor orderWithValidAsProccessor() {

		return new OrderWithValidAsProcessor();
	}
}
