package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.projectgroup;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.administrative.model.ProjectGroup;
import es.redmic.db.maintenance.administrative.repository.ProjectGroupRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.administrative.AdministrativeDomainJobName;
import es.redmic.es.maintenance.domain.administrative.service.ProjectGroupESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.ProjectGroupDTO;

@Configuration
public class ProjectGroupJobConfig extends JobIndexingConfig<ProjectGroup, ProjectGroupDTO, DomainES> {

	private static final String JOB_NAME = AdministrativeDomainJobName.PROJECT_GROUP.toString();

	@Autowired
	ProjectGroupRepository repository;
	
	@Autowired
	ProjectGroupESService service;
	
	public ProjectGroupJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job projectGroupJob() {
		
		return createJobIndexing().start(projectGroupStep()).build();
	}

	@Bean
	@StepScope
	public Step projectGroupStep() {
		
		return createStepIndexing(projectGroupItemReader(), projectGroupProccessor(), projectGroupItemWriter());
	}
	
	@Bean
	@StepScope
	public ItemReaderBase<ProjectGroup> projectGroupItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public ProjectGroupProcessor projectGroupProccessor() {
		
		return new ProjectGroupProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> projectGroupItemWriter() {
		
		return createItemWriter(service);
	}
}
