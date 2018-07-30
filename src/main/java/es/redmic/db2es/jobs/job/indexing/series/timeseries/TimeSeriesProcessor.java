package es.redmic.db2es.jobs.job.indexing.series.timeseries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.series.timeseries.model.TimeSeries;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.series.timeseries.service.TimeSeriesESService;
import es.redmic.models.es.series.timeseries.dto.TimeSeriesDTO;

public class TimeSeriesProcessor implements ItemProcessor<TimeSeries, es.redmic.models.es.series.timeseries.model.TimeSeries> {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(TimeSeriesProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private TimeSeriesESService service;
	
	public es.redmic.models.es.series.timeseries.model.TimeSeries process(TimeSeries inBean) throws Exception {
		
		TimeSeriesDTO tempDto = orikaMapper.map(inBean, TimeSeriesDTO.class);		
		es.redmic.models.es.series.timeseries.model.TimeSeries outBean = service.mapper(tempDto);
		
		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
