package es.redmic.db2es.jobs.job.indexing.maintenance.classifications.linetype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.line.model.LineType;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.line.service.LineTypeESService;
import es.redmic.models.es.maintenance.line.dto.LineTypeDTO;

public class LineTypeProcessor implements ItemProcessor<LineType, es.redmic.models.es.maintenance.line.model.LineType> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LineTypeProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private LineTypeESService service;

	public es.redmic.models.es.maintenance.line.model.LineType process(LineType inBean) throws Exception {

		LineTypeDTO dto = orikaMapper.map(inBean, LineTypeDTO.class);
		es.redmic.models.es.maintenance.line.model.LineType outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
