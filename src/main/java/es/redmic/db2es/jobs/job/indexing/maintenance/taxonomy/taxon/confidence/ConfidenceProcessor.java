package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.confidence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.geodata.common.domain.model.Confidence;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.ConfidenceESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.geojson.common.domain.dto.ConfidenceDTO;

public class ConfidenceProcessor implements ItemProcessor<Confidence, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfidenceProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private ConfidenceESService service;

	public DomainES process(Confidence inBean) throws Exception {

		ConfidenceDTO dto = orikaMapper.map(inBean, ConfidenceDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
