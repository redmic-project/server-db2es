package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.activityfield;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.administrative.model.ActivityField;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.service.ActivityFieldESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.ActivityFieldDTO;

public class ActivityFieldProcessor implements ItemProcessor<ActivityField, DomainES> {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityFieldProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private ActivityFieldESService service;
	
	public DomainES process(ActivityField inBean) throws Exception {
		
		ActivityFieldDTO dto = orikaMapper.map(inBean, ActivityFieldDTO.class);
		DomainES outBean = service.mapper(dto);
		
		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}


}
