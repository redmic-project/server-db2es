package es.redmic.db2es.jobs.job.common.base;

import java.util.List;

import es.redmic.es.data.common.service.RWDataESService;
import es.redmic.models.es.common.model.BaseES;

public class ItemWriterBySave<TModelES extends BaseES<?>> extends ItemWriterBase<TModelES> {

	public ItemWriterBySave(RWDataESService<TModelES, ?> service) {
		super(service);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends TModelES> items) throws Exception {
		service.save((List<TModelES>) items);	
	}
}