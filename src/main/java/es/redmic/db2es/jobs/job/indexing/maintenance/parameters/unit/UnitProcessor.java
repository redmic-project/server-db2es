package es.redmic.db2es.jobs.job.indexing.maintenance.parameters.unit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.parameter.model.Unit;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.parameter.service.UnitESService;
import es.redmic.models.es.maintenance.parameter.dto.UnitDTO;

public class UnitProcessor implements ItemProcessor<Unit, es.redmic.models.es.maintenance.parameter.model.Unit> {

	private static final Logger LOGGER = LoggerFactory.getLogger(UnitProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private UnitESService service;

	public es.redmic.models.es.maintenance.parameter.model.Unit process(Unit inBean) throws Exception {

		UnitDTO dto = orikaMapper.map(inBean, UnitDTO.class);
		es.redmic.models.es.maintenance.parameter.model.Unit outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
