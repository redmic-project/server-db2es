package es.redmic.db2es.jobs.job.indexing.maintenance.toponymias.toponymtype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.toponym.model.ToponymType;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.toponym.service.ToponymTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.toponym.dto.ToponymTypeDTO;

public class ToponymTypeProcessor implements ItemProcessor<ToponymType, DomainES> {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(ToponymTypeProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private ToponymTypeESService service;
	
	public DomainES process(ToponymType inBean) throws Exception {
		
		ToponymTypeDTO dto = orikaMapper.map(inBean, ToponymTypeDTO.class);
		DomainES outBean = service.mapper(dto);
		
		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
