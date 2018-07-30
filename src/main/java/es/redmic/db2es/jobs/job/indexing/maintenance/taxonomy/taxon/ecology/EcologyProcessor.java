package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.ecology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.taxonomy.model.Ecology;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.EcologyESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.EcologyDTO;

public class EcologyProcessor implements ItemProcessor<Ecology, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EcologyProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private EcologyESService service;

	public DomainES process(Ecology inBean) throws Exception {

		EcologyDTO dto = orikaMapper.map(inBean, EcologyDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
