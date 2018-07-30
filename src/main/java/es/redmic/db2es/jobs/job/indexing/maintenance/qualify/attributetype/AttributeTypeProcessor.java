package es.redmic.db2es.jobs.job.indexing.maintenance.qualify.attributetype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.qualifiers.model.AttributeType;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.qualifiers.service.AttributeTypeESService;
import es.redmic.models.es.maintenance.qualifiers.dto.AttributeTypeDTO;

public class AttributeTypeProcessor
		implements ItemProcessor<AttributeType, es.redmic.models.es.maintenance.qualifiers.model.AttributeType> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AttributeTypeProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private AttributeTypeESService service;

	public es.redmic.models.es.maintenance.qualifiers.model.AttributeType process(AttributeType inBean)
			throws Exception {

		AttributeTypeDTO dto = orikaMapper.map(inBean, AttributeTypeDTO.class);
		es.redmic.models.es.maintenance.qualifiers.model.AttributeType outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
