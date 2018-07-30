package es.redmic.db2es.jobs.job.indexing.maintenance.parameters.unittype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.parameter.model.UnitType;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.parameter.service.UnitTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.parameter.dto.UnitTypeDTO;

public class UnitTypeProcessor implements ItemProcessor<UnitType, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(UnitTypeProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private UnitTypeESService service;

	public DomainES process(UnitType inBean) throws Exception {

		UnitTypeDTO dto = orikaMapper.map(inBean, UnitTypeDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
