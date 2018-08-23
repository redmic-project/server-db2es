package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.animal.sex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.animal.model.Sex;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.animal.service.SexESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.animal.dto.SexDTO;

public class SexProcessor implements ItemProcessor<Sex, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SexProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private SexESService service;

	public DomainES process(Sex inBean) throws Exception {

		SexDTO dto = orikaMapper.map(inBean, SexDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
