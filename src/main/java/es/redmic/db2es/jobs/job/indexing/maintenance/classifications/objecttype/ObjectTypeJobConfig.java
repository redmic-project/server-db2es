package es.redmic.db2es.jobs.job.indexing.maintenance.classifications.objecttype;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.objects.model.ObjectType;
import es.redmic.db.maintenance.objects.repository.ObjectTypeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.classifications.ClassificationsDomainJobName;
import es.redmic.es.maintenance.objects.service.ObjectTypeESService;
import es.redmic.models.es.maintenance.objects.dto.ObjectTypeDTO;

@Configuration
public class ObjectTypeJobConfig
		extends JobIndexingConfig<ObjectType, ObjectTypeDTO, es.redmic.models.es.maintenance.objects.model.ObjectType> {

	private static final String JOB_NAME = ClassificationsDomainJobName.OBJECT_TYPE.toString();

	@Autowired
	ObjectTypeRepository repository;
	
	@Autowired
	ObjectTypeESService service;
	
	public ObjectTypeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job objectTypeJob() {
		
		return createJobIndexing().start(getObjectTypeStep()).build();
	}

	@Bean
	public Step getObjectTypeStep() {
		
		return createStepIndexing(objectTypeItemReader(), objectTypeProccessor(), objectTypeItemWriter());
	}
	
	@Bean
	@StepScope
	public ItemReaderBase<ObjectType> objectTypeItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public ObjectTypeProcessor objectTypeProccessor() {
		
		return new ObjectTypeProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.maintenance.objects.model.ObjectType> objectTypeItemWriter() {
		
		return createItemWriter(service);
	}
}
