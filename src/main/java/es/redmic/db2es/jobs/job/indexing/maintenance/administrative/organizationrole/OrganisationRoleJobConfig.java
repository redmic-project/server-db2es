package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.organizationrole;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.repository.OrganisationRoleRepository;
import es.redmic.db.maintenance.administrative.model.OrganisationRole;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.administrative.AdministrativeDomainJobName;
import es.redmic.es.maintenance.domain.administrative.service.OrganisationRoleESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.OrganisationRoleDTO;

@Configuration
public class OrganisationRoleJobConfig extends JobIndexingConfig<OrganisationRole, OrganisationRoleDTO, DomainES> {

	private static final String JOB_NAME = AdministrativeDomainJobName.ORGANISATION_ROLE.toString();

	@Autowired
	OrganisationRoleRepository repository;

	@Autowired
	OrganisationRoleESService service;

	public OrganisationRoleJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job organisationRoleJob() {

		return createJobIndexing().start(organisationRoleStep()).build();
	}

	@Bean
	@StepScope
	public Step organisationRoleStep() {

		return createStepIndexing(organisationRoleItemReader(), organisationRoleProccessor(),
				organisationRoleItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<OrganisationRole> organisationRoleItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public OrganisationRoleProcessor organisationRoleProccessor() {

		return new OrganisationRoleProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> organisationRoleItemWriter() {

		return createItemWriter(service);
	}
}
