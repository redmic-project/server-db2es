package es.redmic.db2es.jobs.job.indexing.maintenance.parameters.sampletype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.samples.model.SampleType;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.samples.service.SampleTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.samples.dto.SampleTypeDTO;

public class SampleTypeProcessor
		implements ItemProcessor<SampleType, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleTypeProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private SampleTypeESService service;

	public DomainES process(SampleType inBean) throws Exception {

		SampleTypeDTO dto = orikaMapper.map(inBean, SampleTypeDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
