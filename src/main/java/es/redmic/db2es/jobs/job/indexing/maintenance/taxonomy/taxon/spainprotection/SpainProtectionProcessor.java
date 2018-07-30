package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.spainprotection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.taxonomy.model.SpainProtection;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.SpainProtectionESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.SpainProtectionDTO;

public class SpainProtectionProcessor implements ItemProcessor<SpainProtection, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpainProtectionProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private SpainProtectionESService service;

	public DomainES process(SpainProtection inBean) throws Exception {

		SpainProtectionDTO dto = orikaMapper.map(inBean, SpainProtectionDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
