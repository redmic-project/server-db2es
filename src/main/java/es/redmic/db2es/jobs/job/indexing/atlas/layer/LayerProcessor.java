package es.redmic.db2es.jobs.job.indexing.atlas.layer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.atlas.layer.model.Layer;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.atlas.service.LayerESService;
import es.redmic.models.es.atlas.dto.LayerDTO;
import es.redmic.models.es.atlas.model.LayerModel;

public class LayerProcessor implements ItemProcessor<Layer, LayerModel> {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(LayerProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	GetCapabilitiesService capabilitiesService;
	
	@Autowired
	private LayerESService service;
	
	public LayerModel process(Layer inBean) throws Exception {
		
		LayerDTO dto = new LayerDTO();
		if (inBean.getUrlSource() != null)
			 dto = capabilitiesService.getCapabilitiesFromGeoServer(inBean.getUrlSource(), inBean.getName());
				
		orikaMapper.map(inBean, dto);
		LayerModel outBean = service.mapper(dto);
		
		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
	
	
}
