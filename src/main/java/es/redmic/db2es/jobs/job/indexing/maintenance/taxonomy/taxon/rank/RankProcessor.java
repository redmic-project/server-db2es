package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.rank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.taxonomy.model.Rank;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.TaxonRankESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.RankDTO;

public class RankProcessor implements ItemProcessor<Rank, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RankProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private TaxonRankESService service;

	public DomainES process(Rank inBean) throws Exception {

		RankDTO dto = orikaMapper.map(inBean, RankDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
