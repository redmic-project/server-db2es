package es.redmic.db2es.jobs.job.indexing.administrative.program;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.model.Program;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.administrative.service.ProgramESService;
import es.redmic.models.es.administrative.dto.ProgramDTO;

public class ProgramProcessor implements ItemProcessor<Program, es.redmic.models.es.administrative.model.Program> {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(ProgramProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private ProgramESService service;
	
	public es.redmic.models.es.administrative.model.Program process(Program inBean) throws Exception {
		
		ProgramDTO dto = orikaMapper.map(inBean, ProgramDTO.class);
		es.redmic.models.es.administrative.model.Program outBean = service.mapper(dto);
		
		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
