package es.redmic.db2es.jobs.job.indexing.administrative.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.model.Platform;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.administrative.service.PlatformESService;
import es.redmic.models.es.administrative.dto.PlatformDTO;

public class PlatformProcessor implements ItemProcessor<Platform, es.redmic.models.es.administrative.model.Platform> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlatformProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private PlatformESService service;

	public es.redmic.models.es.administrative.model.Platform process(Platform inBean) throws Exception {

		PlatformDTO dto = orikaMapper.map(inBean, PlatformDTO.class);
		es.redmic.models.es.administrative.model.Platform outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
