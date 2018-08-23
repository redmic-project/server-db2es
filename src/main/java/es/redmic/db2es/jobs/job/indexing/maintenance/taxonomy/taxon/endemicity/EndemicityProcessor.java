package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.endemicity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.taxonomy.model.Endemicity;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.EndemicityESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.EndemicityDTO;

public class EndemicityProcessor implements ItemProcessor<Endemicity, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EndemicityProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private EndemicityESService service;

	public DomainES process(Endemicity inBean) throws Exception {

		EndemicityDTO dto = orikaMapper.map(inBean, EndemicityDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
