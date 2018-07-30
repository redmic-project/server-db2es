package es.redmic.db2es.jobs.job.indexing.administrative.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.model.Activity;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.administrative.service.ActivityESService;
import es.redmic.models.es.administrative.dto.ActivityDTO;

public class ActivityProcessor implements ItemProcessor<Activity, es.redmic.models.es.administrative.model.Activity> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private ActivityESService service;

	public es.redmic.models.es.administrative.model.Activity process(Activity inBean) throws Exception {

		ActivityDTO dto = orikaMapper.map(inBean, ActivityDTO.class);
		es.redmic.models.es.administrative.model.Activity outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
