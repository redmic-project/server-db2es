package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.accessibility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.common.model.Accessibility;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.service.AccessibilityESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.common.dto.AccessibilityDTO;

public class AccessibilityProcessor implements ItemProcessor<Accessibility, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccessibilityProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private AccessibilityESService service;

	public DomainES process(Accessibility inBean) throws Exception {

		AccessibilityDTO dto = orikaMapper.map(inBean, AccessibilityDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
