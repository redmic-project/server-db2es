package es.redmic.db2es.jobs.job.common.base;

import java.util.List;

import es.redmic.es.data.common.service.RWDataESService;
import es.redmic.models.es.common.model.BaseES;

public class ItemWriterByUpdate<TModelES extends BaseES<?>> extends ItemWriterBase<TModelES> {

	public ItemWriterByUpdate(RWDataESService<TModelES, ?> service) {
		super(service);
	}

	@Override
	public void write(List<? extends TModelES> items) throws Exception {
		
		for (TModelES item : items)
			service.update(item);	
	}
}