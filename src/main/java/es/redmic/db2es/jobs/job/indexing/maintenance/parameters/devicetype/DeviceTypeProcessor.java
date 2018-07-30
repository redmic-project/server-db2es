package es.redmic.db2es.jobs.job.indexing.maintenance.parameters.devicetype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.device.model.DeviceType;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.device.service.DeviceTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.device.dto.DeviceTypeDTO;

public class DeviceTypeProcessor implements ItemProcessor<DeviceType, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceTypeProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private DeviceTypeESService service;

	public DomainES process(DeviceType inBean) throws Exception {

		DeviceTypeDTO dto = orikaMapper.map(inBean, DeviceTypeDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
