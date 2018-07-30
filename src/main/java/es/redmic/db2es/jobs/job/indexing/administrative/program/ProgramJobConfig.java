package es.redmic.db2es.jobs.job.indexing.administrative.program;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.model.Program;
import es.redmic.db.administrative.repository.ProgramRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.administrative.AdministrativeJobName;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.es.administrative.service.ProgramESService;
import es.redmic.models.es.administrative.dto.ProgramDTO;

@Configuration
public class ProgramJobConfig extends JobIndexingConfig<Program, ProgramDTO, es.redmic.models.es.administrative.model.Program> {

	private static final String JOB_NAME = AdministrativeJobName.PROGRAM.toString();

	@Autowired
	ProgramRepository repository;
	
	@Autowired
	ProgramESService service;
	
	public ProgramJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job programJob() {
		
		return createJobIndexing().start(programStep()).build();
	}

	@Bean
	public Step programStep() {
		
		return createStepIndexing(programItemReader(), programProccessor(), programItemWriter());
	}
	
	@Bean
	@StepScope
	public ItemReaderBase<Program> programItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public ProgramProcessor programProccessor() {
		
		return new ProgramProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.administrative.model.Program> programItemWriter() {
		
		return createItemWriter(service);
	}
}
