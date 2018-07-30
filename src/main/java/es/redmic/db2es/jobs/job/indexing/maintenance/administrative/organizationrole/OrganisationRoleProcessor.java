package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.organizationrole;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.administrative.model.OrganisationRole;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.service.OrganisationRoleESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.OrganisationRoleDTO;

public class OrganisationRoleProcessor implements ItemProcessor<OrganisationRole, DomainES> {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(OrganisationRoleProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private OrganisationRoleESService service;
	
	public DomainES process(OrganisationRole inBean) throws Exception {
		
		OrganisationRoleDTO dto = orikaMapper.map(inBean, OrganisationRoleDTO.class);
		DomainES outBean = service.mapper(dto);
		
		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}


}
