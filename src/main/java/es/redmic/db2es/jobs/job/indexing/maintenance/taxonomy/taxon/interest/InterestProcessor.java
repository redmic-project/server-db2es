package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.interest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.taxonomy.model.Interest;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.InterestESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.InterestDTO;

public class InterestProcessor implements ItemProcessor<Interest, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(InterestProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private InterestESService service;

	public DomainES process(Interest inBean) throws Exception {

		InterestDTO dto = orikaMapper.map(inBean, InterestDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
