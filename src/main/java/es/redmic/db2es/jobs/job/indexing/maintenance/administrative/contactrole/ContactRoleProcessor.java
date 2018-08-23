package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.contactrole;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.administrative.model.ContactRole;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.service.ContactRoleESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.ContactRoleDTO;

public class ContactRoleProcessor implements ItemProcessor<ContactRole, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactRoleProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private ContactRoleESService service;

	public DomainES process(ContactRole inBean) throws Exception {

		ContactRoleDTO dto = orikaMapper.map(inBean, ContactRoleDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
