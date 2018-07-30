package es.redmic.db2es.jobs.job.indexing.maintenance.parameters.devicetype;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.device.model.DeviceType;
import es.redmic.db.maintenance.device.repository.DeviceTypeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.parameters.ParametersDomainJobName;
import es.redmic.es.maintenance.device.service.DeviceTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.device.dto.DeviceTypeDTO;

@Configuration
public class DeviceTypeJobConfig extends JobIndexingConfig<DeviceType, DeviceTypeDTO, DomainES> {

	private static final String JOB_NAME = ParametersDomainJobName.DEVICE_TYPE.toString();

	@Autowired
	DeviceTypeRepository repository;

	@Autowired
	DeviceTypeESService service;

	public DeviceTypeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job deviceTypeJob() {

		return createJobIndexing().start(deviceTypeStep()).build();
	}

	@Bean
	@StepScope
	public Step deviceTypeStep() {

		return createStepIndexing(deviceTypeItemReader(), deviceTypeProccessor(), deviceTypeItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<DeviceType> deviceTypeItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public DeviceTypeProcessor deviceTypeProccessor() {

		return new DeviceTypeProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> deviceTypeItemWriter() {

		return createItemWriter(service);
	}
}
