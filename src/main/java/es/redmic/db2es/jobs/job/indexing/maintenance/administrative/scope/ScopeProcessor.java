package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.scope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.administrative.model.Scope;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.service.ScopeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.ScopeDTO;

public class ScopeProcessor implements ItemProcessor<Scope, DomainES> {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(ScopeProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private ScopeESService service;
	
	public DomainES process(Scope inBean) throws Exception {
		
		ScopeDTO dto = orikaMapper.map(inBean, ScopeDTO.class);
		DomainES outBean = service.mapper(dto);
		
		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}


}
