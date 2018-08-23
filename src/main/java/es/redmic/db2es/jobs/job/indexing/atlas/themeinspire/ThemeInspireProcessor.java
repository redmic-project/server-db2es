package es.redmic.db2es.jobs.job.indexing.atlas.themeinspire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.atlas.layer.model.ThemeInspire;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.atlas.service.ThemeInspireESService;
import es.redmic.models.es.atlas.dto.ThemeInspireDTO;

public class ThemeInspireProcessor implements ItemProcessor<ThemeInspire, es.redmic.models.es.atlas.model.ThemeInspire> {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(ThemeInspireProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private ThemeInspireESService service;
	
	public es.redmic.models.es.atlas.model.ThemeInspire process(ThemeInspire inBean) throws Exception {
		
		ThemeInspireDTO dto = orikaMapper.map(inBean, ThemeInspireDTO.class);
		es.redmic.models.es.atlas.model.ThemeInspire outBean = service.mapper(dto);
		
		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
