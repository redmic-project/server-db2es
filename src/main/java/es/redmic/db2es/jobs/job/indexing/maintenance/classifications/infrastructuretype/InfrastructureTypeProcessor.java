package es.redmic.db2es.jobs.job.indexing.maintenance.classifications.infrastructuretype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.point.model.InfrastructureType;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.point.service.InfrastructureTypeESService;
import es.redmic.models.es.maintenance.point.dto.InfrastructureTypeDTO;

public class InfrastructureTypeProcessor
		implements ItemProcessor<InfrastructureType, es.redmic.models.es.maintenance.point.model.InfrastructureType> {

	private static final Logger LOGGER = LoggerFactory.getLogger(InfrastructureTypeProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private InfrastructureTypeESService service;

	public es.redmic.models.es.maintenance.point.model.InfrastructureType process(InfrastructureType inBean)
			throws Exception {

		InfrastructureTypeDTO dto = orikaMapper.map(inBean, InfrastructureTypeDTO.class);
		es.redmic.models.es.maintenance.point.model.InfrastructureType outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
