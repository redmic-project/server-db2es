package es.redmic.db2es.jobs.job.indexing.maintenance.classifications.thematictype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.areas.model.ThematicType;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.area.service.ThematicTypeESService;
import es.redmic.models.es.maintenance.areas.dto.ThematicTypeDTO;

public class ThematicTypeProcessor
		implements ItemProcessor<ThematicType, es.redmic.models.es.maintenance.areas.model.ThematicType> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThematicTypeProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private ThematicTypeESService service;

	public es.redmic.models.es.maintenance.areas.model.ThematicType process(ThematicType inBean) throws Exception {

		ThematicTypeDTO dto = orikaMapper.map(inBean, ThematicTypeDTO.class);
		es.redmic.models.es.maintenance.areas.model.ThematicType outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
