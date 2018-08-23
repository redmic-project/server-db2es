package es.redmic.db2es.jobs.job.indexing.maintenance.classifications.censingstatus;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.strech.model.CensingStatus;
import es.redmic.db.maintenance.strech.repository.CensingStatusRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.classifications.ClassificationsDomainJobName;
import es.redmic.es.maintenance.strech.service.CensingStatusESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.strech.dto.CensingStatusDTO;

@Configuration
public class CensingStatusJobConfig extends JobIndexingConfig<CensingStatus, CensingStatusDTO, DomainES> {

	private static final String JOB_NAME = ClassificationsDomainJobName.CENSING_STATUS.toString();

	@Autowired
	CensingStatusRepository repository;

	@Autowired
	CensingStatusESService service;

	public CensingStatusJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job censingStatusJob() {

		return createJobIndexing().start(censingStatusStep()).build();
	}

	@Bean
	@StepScope
	public Step censingStatusStep() {

		return createStepIndexing(censingStatusItemReader(), censingStatusProccessor(), censingStatusItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<CensingStatus> censingStatusItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public CensingStatusProcessor censingStatusProccessor() {

		return new CensingStatusProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> censingStatusItemWriter() {

		return createItemWriter(service);
	}
}
