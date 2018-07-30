package es.redmic.db2es.jobs.job.indexing.maintenance.classifications.areatype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.areas.model.AreaType;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.area.service.AreaTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.areas.dto.AreaTypeDTO;

public class AreaTypeProcessor implements ItemProcessor<AreaType, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AreaTypeProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private AreaTypeESService service;

	public DomainES process(AreaType inBean) throws Exception {

		AreaTypeDTO dto = orikaMapper.map(inBean, AreaTypeDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
