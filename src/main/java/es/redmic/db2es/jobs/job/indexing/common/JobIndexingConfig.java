package es.redmic.db2es.jobs.job.indexing.common;

import java.io.Serializable;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.redmic.databaselib.common.model.SuperModel;
import es.redmic.databaselib.common.repository.BaseRepository;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.db2es.jobs.job.common.JobBase;
import es.redmic.db2es.jobs.job.common.JobIncrementer;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.es.data.common.service.RWDataESService;
import es.redmic.models.es.common.dto.BaseDTO;
import es.redmic.models.es.common.model.BaseES;

public abstract class JobIndexingConfig<TModel extends SuperModel, TDTO extends BaseDTO<?>, TModelES extends BaseES<?>>
		extends JobBase {

	@Value("${db2es.reader.PAGE_SIZE}")
	private int PAGE_SIZE;
	
	@Value("${db2es.writer.COMMIT_INTERVAL}")
	Integer COMMIT_INTERVAL;

	@Autowired
	protected OrikaScanBean orikaScanBean;

	private String name;

	public JobIndexingConfig(String name) {

		this.name = name;
	}

	protected JobBuilder createJobIndexing() {

		// @formatter:off
		return jobBuilderFactory.get("indexing-" + name + "-job")
				.listener(jobListener)
				.incrementer(new JobIncrementer());
		// @formatter:on
	}

	protected Step createStepIndexing(ItemReaderBase<TModel> itemReader, ItemProcessor<TModel, TModelES> itemProcessor,
			ItemWriterBase<TModelES> itemWriter) {

		//// @formatter:off
		return stepBuilderFactory.get("indexing-" + name + "-step")
					.listener(stepExecutionListener)
					.<TModel, TModelES>chunk(COMMIT_INTERVAL)
					.reader(itemReader)
					.processor(itemProcessor)
					.writer(itemWriter)
					.listener(logProcessListener)
					.faultTolerant()
					.build();
		// @formatter:on
	}

	protected ItemReaderBase<TModel> createItemReader(BaseRepository<TModel, ? extends Serializable> repository) {

		ItemReaderBase<TModel> itemReader = new ItemReaderBase<TModel>(repository, PAGE_SIZE);

		return itemReader;
	}

	protected ItemWriterBySave<TModelES> createItemWriter(RWDataESService<TModelES, TDTO> service) {

		ItemWriterBySave<TModelES> itemWriter = new ItemWriterBySave<TModelES>(service);

		return itemWriter;
	}
}
