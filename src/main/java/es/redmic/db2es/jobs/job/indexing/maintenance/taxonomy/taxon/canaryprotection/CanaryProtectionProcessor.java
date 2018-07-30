package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.canaryprotection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.taxonomy.model.CanaryProtection;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.CanaryProtectionESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.CanaryProtectionDTO;

public class CanaryProtectionProcessor implements ItemProcessor<CanaryProtection, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CanaryProtectionProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private CanaryProtectionESService service;

	public DomainES process(CanaryProtection inBean) throws Exception {

		CanaryProtectionDTO dto = orikaMapper.map(inBean, CanaryProtectionDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
