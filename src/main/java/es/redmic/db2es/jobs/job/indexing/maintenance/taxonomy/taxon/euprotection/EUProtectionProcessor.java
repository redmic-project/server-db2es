package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.euprotection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.taxonomy.model.EUProtection;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.EUProtectionESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.EUProtectionDTO;

public class EUProtectionProcessor implements ItemProcessor<EUProtection, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EUProtectionProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private EUProtectionESService service;

	public DomainES process(EUProtection inBean) throws Exception {

		EUProtectionDTO dto = orikaMapper.map(inBean, EUProtectionDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
