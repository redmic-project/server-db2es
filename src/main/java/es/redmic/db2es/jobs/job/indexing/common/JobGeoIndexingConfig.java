package es.redmic.db2es.jobs.job.indexing.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemProcessor;

import es.redmic.db.geodata.common.model.GeoDataModel;
import es.redmic.db.geodata.common.repository.RepositoryGeo;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.geo.ItemGeoWriterBase;
import es.redmic.es.geodata.common.service.GeoDataESService;
import es.redmic.models.es.geojson.common.dto.MetaFeatureDTO;
import es.redmic.models.es.geojson.common.model.Feature;
import es.redmic.models.es.geojson.properties.model.GeoDataProperties;

public abstract class JobGeoIndexingConfig<TModel extends GeoDataModel, TDTO extends MetaFeatureDTO<?, ?>, TModelES extends Feature<GeoDataProperties, ?>>
		extends JobPartitionConfig<TModel, TDTO, TModelES> {

	public JobGeoIndexingConfig(String name) {
		
		super(name);
	}

	// @formatter:off


	protected Step createStepIndexing(ItemReaderBase<TModel> itemReader,
			ItemProcessor<TModel, TModelES> itemProcessor, ItemGeoWriterBase<TModelES> itemWriter) {
		
		return stepBuilderFactory.get("indexing-" + name + "-step")
				.listener(stepExecutionListener)
				.<TModel, TModelES>chunk(COMMIT_INTERVAL)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.listener(logProcessListener)
				.faultTolerant()
				.build();
	}
	
	// @formatter:on
	
	protected ItemReaderBase<TModel> createItemReader(RepositoryGeo<TModel, Long> repository) {

		return createItemReader(repository, null, null);
	}
	
	protected ItemReaderBase<TModel> createItemReader(RepositoryGeo<TModel, Long> repository, Long start, Long end) {

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

	protected ItemGeoWriterBase<TModelES> createItemWriter(GeoDataESService<?, TModelES> service) {

		ItemGeoWriterBase<TModelES> itemWriter = new ItemGeoWriterBase<TModelES>(service);

		return itemWriter;
	}
}
