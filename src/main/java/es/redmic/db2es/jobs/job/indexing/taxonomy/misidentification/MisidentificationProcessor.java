package es.redmic.db2es.jobs.job.indexing.taxonomy.misidentification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.taxonomy.model.Misidentification;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.administrative.taxonomy.service.MisidentificationESService;
import es.redmic.models.es.administrative.taxonomy.dto.MisidentificationDTO;

public class MisidentificationProcessor implements
		ItemProcessor<Misidentification, es.redmic.models.es.administrative.taxonomy.model.Misidentification> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MisidentificationProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private MisidentificationESService service;

	public es.redmic.models.es.administrative.taxonomy.model.Misidentification process(Misidentification inBean)
			throws Exception {

		MisidentificationDTO dto = orikaMapper.map(inBean, MisidentificationDTO.class);
		es.redmic.models.es.administrative.taxonomy.model.Misidentification outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
