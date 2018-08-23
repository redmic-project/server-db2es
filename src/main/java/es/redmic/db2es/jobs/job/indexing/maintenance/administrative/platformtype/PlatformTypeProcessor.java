package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.platformtype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.administrative.model.PlatformType;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.service.PlatformTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.PlatformTypeDTO;

public class PlatformTypeProcessor implements ItemProcessor<PlatformType, DomainES> {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(PlatformTypeProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private PlatformTypeESService service;
	
	public DomainES process(PlatformType inBean) throws Exception {
		
		PlatformTypeDTO dto = orikaMapper.map(inBean, PlatformTypeDTO.class);
		DomainES outBean = service.mapper(dto);
		
		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}


}
