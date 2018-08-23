package es.redmic.db2es.jobs.job.indexing.maintenance.toponymias.toponym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.redmic.db.geodata.toponym.model.Toponym;
import es.redmic.db.geodata.toponym.repository.ToponymRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;

@Component
public class ToponymItemReader extends ItemReaderBase<Toponym> {

	// TODO: pasar pageSize como en los otros servicios
	
	@Autowired
	public ToponymItemReader(ToponymRepository repository) {
		super(repository, 10);
	}
}
