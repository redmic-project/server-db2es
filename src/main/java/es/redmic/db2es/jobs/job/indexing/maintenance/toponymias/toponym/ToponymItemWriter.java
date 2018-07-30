package es.redmic.db2es.jobs.job.indexing.maintenance.toponymias.toponym;

import java.util.List;
import java.util.ListIterator;

import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.redmic.es.toponym.service.ToponymESService;
import es.redmic.models.es.geojson.toponym.model.Toponym;

@Component
public class ToponymItemWriter extends StepExecutionListenerSupport implements ItemWriter<Toponym> {

	ToponymESService service;

	@Autowired
	public ToponymItemWriter(ToponymESService service) {
		this.service = service;
	}

	@Override
	public void write(List<? extends Toponym> items) throws Exception {
		final ListIterator<? extends Toponym> itemIterator = items.listIterator();
		itemIterator.forEachRemaining(service::save);
	}
}
