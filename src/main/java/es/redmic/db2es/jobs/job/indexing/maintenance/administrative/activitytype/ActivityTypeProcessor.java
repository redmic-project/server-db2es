package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.activitytype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.administrative.model.ActivityType;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.service.ActivityTypeESService;
import es.redmic.models.es.maintenance.administrative.dto.ActivityTypeDTO;

public class ActivityTypeProcessor
		implements ItemProcessor<ActivityType, es.redmic.models.es.maintenance.administrative.model.ActivityType> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityTypeProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private ActivityTypeESService service;

	public es.redmic.models.es.maintenance.administrative.model.ActivityType process(ActivityType inBean)
			throws Exception {

		ActivityTypeDTO dto = orikaMapper.map(inBean, ActivityTypeDTO.class);
		es.redmic.models.es.maintenance.administrative.model.ActivityType outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
