package es.redmic.db2es.jobs.job.indexing.maintenance.classifications.objecttype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.objects.model.ObjectType;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.objects.service.ObjectTypeESService;
import es.redmic.models.es.maintenance.objects.dto.ObjectTypeDTO;

public class ObjectTypeProcessor
		implements ItemProcessor<ObjectType, es.redmic.models.es.maintenance.objects.model.ObjectType> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectTypeProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private ObjectTypeESService service;

	public es.redmic.models.es.maintenance.objects.model.ObjectType process(ObjectType inBean) throws Exception {

		ObjectTypeDTO dto = orikaMapper.map(inBean, ObjectTypeDTO.class);
		es.redmic.models.es.maintenance.objects.model.ObjectType outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
