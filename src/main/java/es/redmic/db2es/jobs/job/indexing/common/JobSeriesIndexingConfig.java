package es.redmic.db2es.jobs.job.indexing.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemProcessor;

import es.redmic.databaselib.common.model.LongModel;
import es.redmic.databaselib.common.repository.BaseRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.series.ItemSeriesWriterBase;
import es.redmic.es.series.common.service.RWSeriesESService;
import es.redmic.models.es.series.common.dto.SeriesCommonDTO;
import es.redmic.models.es.series.common.model.SeriesCommon;

public abstract class JobSeriesIndexingConfig<TModel extends LongModel, TDTO extends SeriesCommonDTO, TModelES extends SeriesCommon>
		extends JobPartitionConfig<TModel, TDTO, TModelES> {

	public JobSeriesIndexingConfig(String name) {

		super(name);
	}

	// @formatter:off

	protected Step createStepIndexing(ItemReaderBase<TModel> itemReader, ItemProcessor<TModel, TModelES> itemProcessor,
			ItemSeriesWriterBase<TModelES> itemWriter) {

		return stepBuilderFactory.get("indexing-" + name + "-step").listener(stepExecutionListener)
				.<TModel, TModelES>chunk(COMMIT_INTERVAL).reader(itemReader).processor(itemProcessor).writer(itemWriter)
				.listener(logProcessListener).faultTolerant().build();
	}

	// @formatter:on

	protected ItemReaderBase<TModel> createItemReader(BaseRepository<TModel, ? extends Serializable> repository) {
		return createItemReader(repository, null, null);
	}

	protected ItemReaderBase<TModel> createItemReader(BaseRepository<TModel, ? extends Serializable> repository,
			Long start, Long end) {

		ItemReaderBase<TModel> itemReader = new ItemReaderBase<TModel>(repository, PAGE_SIZE);

		itemReader.setMethodName("findBetween");

		if (start != null && end != null) {

			List<Long> parameters = new ArrayList<Long>();
			parameters.add(start);
			parameters.add(end);

			itemReader.setArguments(parameters);
		}
		return itemReader;
	}

	protected ItemSeriesWriterBase<TModelES> createItemWriter(RWSeriesESService<TModelES, TDTO> service) {

		ItemSeriesWriterBase<TModelES> itemWriter = new ItemSeriesWriterBase<TModelES>(service);

		return itemWriter;
	}
}
