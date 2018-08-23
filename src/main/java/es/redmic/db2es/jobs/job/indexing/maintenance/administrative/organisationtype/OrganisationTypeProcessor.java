package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.organisationtype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.administrative.model.OrganisationType;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.service.OrganisationTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.OrganisationTypeDTO;

public class OrganisationTypeProcessor implements ItemProcessor<OrganisationType, DomainES> {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(OrganisationTypeProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private OrganisationTypeESService service;
	
	public DomainES process(OrganisationType inBean) throws Exception {
		
		OrganisationTypeDTO dto = orikaMapper.map(inBean, OrganisationTypeDTO.class);
		DomainES outBean = service.mapper(dto);
		
		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}


}
