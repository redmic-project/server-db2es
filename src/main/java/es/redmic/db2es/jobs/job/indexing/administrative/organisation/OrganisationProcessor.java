package es.redmic.db2es.jobs.job.indexing.administrative.organisation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.model.Organisation;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.administrative.service.OrganisationESService;
import es.redmic.models.es.administrative.dto.OrganisationDTO;

public class OrganisationProcessor
		implements ItemProcessor<Organisation, es.redmic.models.es.administrative.model.Organisation> {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrganisationProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private OrganisationESService service;

	public es.redmic.models.es.administrative.model.Organisation process(Organisation inBean) throws Exception {

		OrganisationDTO dto = orikaMapper.map(inBean, OrganisationDTO.class);
		es.redmic.models.es.administrative.model.Organisation outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
