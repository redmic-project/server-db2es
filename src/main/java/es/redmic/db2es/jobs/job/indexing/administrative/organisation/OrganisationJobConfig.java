package es.redmic.db2es.jobs.job.indexing.administrative.organisation;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.model.Organisation;
import es.redmic.db.administrative.repository.OrganisationRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.administrative.AdministrativeJobName;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.es.administrative.service.OrganisationESService;
import es.redmic.models.es.administrative.dto.OrganisationDTO;

@Configuration
public class OrganisationJobConfig extends
		JobIndexingConfig<Organisation, OrganisationDTO, es.redmic.models.es.administrative.model.Organisation> {

	private static final String JOB_NAME = AdministrativeJobName.ORGANISATION.toString();

	@Autowired
	OrganisationRepository repository;

	@Autowired
	OrganisationESService service;

	public OrganisationJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job organisationJob() {
		
		return createJobIndexing().start(organisationStep()).build();
	}

	@Bean
	public Step organisationStep() {
		
		return createStepIndexing(organisationItemReader(), organisationProccessor(), organisationItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Organisation> organisationItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public OrganisationProcessor organisationProccessor() {

		return new OrganisationProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.administrative.model.Organisation> organisationItemWriter() {

		return createItemWriter(service);
	}
}
