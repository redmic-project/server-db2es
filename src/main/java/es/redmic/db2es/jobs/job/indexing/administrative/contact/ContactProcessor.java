package es.redmic.db2es.jobs.job.indexing.administrative.contact;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.redmic.db.administrative.model.Contact;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.administrative.service.ContactESService;
import es.redmic.models.es.administrative.dto.ContactDTO;

@Component
public class ContactProcessor implements ItemProcessor<Contact, es.redmic.models.es.administrative.model.Contact> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private ContactESService service;

	public es.redmic.models.es.administrative.model.Contact process(Contact inBean) throws Exception {

		ContactDTO dto = orikaMapper.map(inBean, ContactDTO.class);
		es.redmic.models.es.administrative.model.Contact outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
