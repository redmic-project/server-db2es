package es.redmic.db2es.jobs.job.indexing.maintenance.qualify.attributetype;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.qualifiers.model.AttributeType;
import es.redmic.db.maintenance.qualifiers.repository.AttributeTypeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.qualify.QualifyDomainJobName;
import es.redmic.es.maintenance.qualifiers.service.AttributeTypeESService;
import es.redmic.models.es.maintenance.qualifiers.dto.AttributeTypeDTO;

@Configuration
public class AttributeTypeJobConfig extends
		JobIndexingConfig<AttributeType, AttributeTypeDTO, es.redmic.models.es.maintenance.qualifiers.model.AttributeType> {

	private static final String JOB_NAME = QualifyDomainJobName.ATTRIBUTE_TYPE.toString();

	@Autowired
	AttributeTypeRepository repository;

	@Autowired
	AttributeTypeESService service;

	public AttributeTypeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job attributeTypeJob() {

		return createJobIndexing().start(attributeTypeStep()).build();
	}

	@Bean
	@StepScope
	public Step attributeTypeStep() {

		return createStepIndexing(attributeTypeItemReader(), attributeTypeProccessor(), attributeTypeItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<AttributeType> attributeTypeItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public AttributeTypeProcessor attributeTypeProccessor() {

		return new AttributeTypeProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.maintenance.qualifiers.model.AttributeType> attributeTypeItemWriter() {

		return createItemWriter(service);
	}
}
