package es.redmic.db2es.jobs.job.indexing.series.infrastructureattributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.series.infrastructureattributes.model.InfrastructureAttributes;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.series.attributeseries.service.AttributeSeriesESService;
import es.redmic.models.es.series.attributeseries.dto.AttributeSeriesDTO;
import es.redmic.models.es.series.attributeseries.model.AttributeSeries;

public class InfrastructureAttributeSeriesProcessor implements ItemProcessor<InfrastructureAttributes, AttributeSeries> {

	private static final Logger LOGGER = LoggerFactory.getLogger(InfrastructureAttributeSeriesProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private AttributeSeriesESService service;

	public AttributeSeries process(InfrastructureAttributes inBean) throws Exception {

		AttributeSeriesDTO tempDto = orikaMapper.map(inBean, AttributeSeriesDTO.class);

		AttributeSeries outBean = service.mapper(tempDto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}