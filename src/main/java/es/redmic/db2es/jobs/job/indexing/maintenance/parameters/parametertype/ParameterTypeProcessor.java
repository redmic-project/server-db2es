package es.redmic.db2es.jobs.job.indexing.maintenance.parameters.parametertype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.parameter.model.ParameterType;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.parameter.service.ParameterTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.parameter.dto.ParameterTypeDTO;

public class ParameterTypeProcessor implements ItemProcessor<ParameterType, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParameterTypeProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private ParameterTypeESService service;

	public DomainES process(ParameterType inBean) throws Exception {

		ParameterTypeDTO dto = orikaMapper.map(inBean, ParameterTypeDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
