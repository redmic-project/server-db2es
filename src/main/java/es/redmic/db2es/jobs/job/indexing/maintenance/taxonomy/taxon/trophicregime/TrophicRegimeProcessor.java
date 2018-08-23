package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.trophicregime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.taxonomy.model.TrophicRegime;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.TrophicRegimeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.TrophicRegimeDTO;

public class TrophicRegimeProcessor implements ItemProcessor<TrophicRegime, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrophicRegimeProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private TrophicRegimeESService service;

	public DomainES process(TrophicRegime inBean) throws Exception {

		TrophicRegimeDTO dto = orikaMapper.map(inBean, TrophicRegimeDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
