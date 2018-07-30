package es.redmic.db2es.jobs.job.indexing.maintenance.parameters.parameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.parameter.model.Parameter;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.parameter.service.ParameterESService;
import es.redmic.models.es.maintenance.parameter.dto.ParameterDTO;

public class ParameterProcessor
		implements ItemProcessor<Parameter, es.redmic.models.es.maintenance.parameter.model.Parameter> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParameterProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private ParameterESService service;

	public es.redmic.models.es.maintenance.parameter.model.Parameter process(Parameter inBean) throws Exception {

		ParameterDTO dto = orikaMapper.map(inBean, ParameterDTO.class);
		es.redmic.models.es.maintenance.parameter.model.Parameter outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
