package es.redmic.db2es.jobs.job.indexing.administrative.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.device.model.Device;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.device.service.DeviceESService;
import es.redmic.models.es.maintenance.device.dto.DeviceDTO;

public class DeviceProcessor implements ItemProcessor<Device, es.redmic.models.es.maintenance.device.model.Device> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private DeviceESService service;

	public es.redmic.models.es.maintenance.device.model.Device process(Device inBean) throws Exception {

		DeviceDTO dto = orikaMapper.map(inBean, DeviceDTO.class);
		es.redmic.models.es.maintenance.device.model.Device outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
