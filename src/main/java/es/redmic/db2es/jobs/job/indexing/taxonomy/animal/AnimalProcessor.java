package es.redmic.db2es.jobs.job.indexing.taxonomy.animal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.taxonomy.model.Animal;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.administrative.taxonomy.service.AnimalESService;
import es.redmic.models.es.administrative.taxonomy.dto.AnimalDTO;

public class AnimalProcessor implements ItemProcessor<Animal, es.redmic.models.es.administrative.taxonomy.model.Animal> {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(AnimalProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private AnimalESService service;
	
	public es.redmic.models.es.administrative.taxonomy.model.Animal process(Animal inBean) throws Exception {
		
		AnimalDTO dto = orikaMapper.map(inBean, AnimalDTO.class);
		es.redmic.models.es.administrative.taxonomy.model.Animal outBean = service.mapper(dto);
		
		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
