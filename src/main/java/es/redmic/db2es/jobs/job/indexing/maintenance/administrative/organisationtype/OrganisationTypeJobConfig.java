package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.organisationtype;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.administrative.model.OrganisationType;
import es.redmic.db.maintenance.administrative.repository.OrganisationTypeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.administrative.AdministrativeDomainJobName;
import es.redmic.es.maintenance.domain.administrative.service.OrganisationTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.OrganisationTypeDTO;

@Configuration
public class OrganisationTypeJobConfig extends JobIndexingConfig<OrganisationType, OrganisationTypeDTO, DomainES> {

	private static final String JOB_NAME = AdministrativeDomainJobName.ORGANISATION_TYPE.toString();

	@Autowired
	OrganisationTypeRepository repository;

	@Autowired
	OrganisationTypeESService service;

	public OrganisationTypeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job organisationTypeJob() {

		return createJobIndexing().start(organisationTypeStep()).build();
	}

	@Bean
	@StepScope
	public Step organisationTypeStep() {

		return createStepIndexing(organisationTypeItemReader(), organisationTypeProccessor(),
				organisationTypeItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<OrganisationType> organisationTypeItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public OrganisationTypeProcessor organisationTypeProccessor() {

		return new OrganisationTypeProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> organisationTypeItemWriter() {

		return createItemWriter(service);
	}
}
