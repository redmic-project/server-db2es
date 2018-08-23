package es.redmic.db2es.jobs.job.indexing.atlas.layer;

import java.util.Collections;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;

import es.redmic.db.atlas.layer.model.Layer;
import es.redmic.db.atlas.layer.repository.LayerRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.atlas.AtlasJobName;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.es.atlas.service.LayerESService;
import es.redmic.models.es.atlas.dto.LayerDTO;
import es.redmic.models.es.atlas.model.LayerModel;

@Configuration
public class LayerJobConfig extends JobIndexingConfig<Layer, LayerDTO, LayerModel> {

	private static final String JOB_NAME = AtlasJobName.LAYER.toString();

	@Autowired
	LayerRepository repository;
	
	@Autowired
	LayerESService service;

	public LayerJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job layerJob() {
		
		return createJobIndexing().start(layerStep()).build();
	}

	@Bean
	public Step layerStep() {

		return createStepIndexing(layerItemReader(), layerProccessor(), layerItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Layer> layerItemReader() {

		ItemReaderBase<Layer> itemReader = createItemReader(repository);
		
		// TODO: la ordenación se hace por pagina, si es menor al numero de datos, ordena mal
		itemReader.setSort(Collections.singletonMap("parent.id", Direction.DESC));
		itemReader.setPageSize(100); 
		
		return itemReader;
	}

	@Bean
	public LayerProcessor layerProccessor() {

		return new LayerProcessor();
	}

	@Bean
	public ItemWriterBySave<LayerModel> layerItemWriter() {

		return createItemWriter(service);
	}
}
