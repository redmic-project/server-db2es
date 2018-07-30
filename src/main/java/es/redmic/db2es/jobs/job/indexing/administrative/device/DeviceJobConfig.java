package es.redmic.db2es.jobs.job.indexing.administrative.device;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.device.model.Device;
import es.redmic.db.maintenance.device.repository.DeviceRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.administrative.AdministrativeJobName;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.es.maintenance.device.service.DeviceESService;
import es.redmic.models.es.maintenance.device.dto.DeviceDTO;

@Configuration
public class DeviceJobConfig extends JobIndexingConfig<Device, DeviceDTO, es.redmic.models.es.maintenance.device.model.Device> {

	private static final String JOB_NAME = AdministrativeJobName.DEVICE.toString();

	@Autowired
	DeviceRepository repository;
	
	@Autowired
	DeviceESService service;
	
	public DeviceJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job deviceJob() {
		
		return createJobIndexing().start(deviceStep()).build();
	}

	@Bean
	public Step deviceStep() {
		
		return createStepIndexing(deviceItemReader(), deviceProccessor(), deviceItemWriter());
	}
	
	@Bean
	@StepScope
	public ItemReaderBase<Device> deviceItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public DeviceProcessor deviceProccessor() {
		
		return new DeviceProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.maintenance.device.model.Device> deviceItemWriter() {
		
		return createItemWriter(service);
	}
}
