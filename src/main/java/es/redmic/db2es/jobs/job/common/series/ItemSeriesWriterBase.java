package es.redmic.db2es.jobs.job.common.series;

import java.util.List;

import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemWriter;

import es.redmic.es.series.common.service.RWSeriesESService;
import es.redmic.models.es.series.common.model.SeriesCommon;

public class ItemSeriesWriterBase<TModel extends SeriesCommon> extends StepExecutionListenerSupport
		implements ItemWriter<TModel> {

	RWSeriesESService<TModel, ?> service;

	public ItemSeriesWriterBase(RWSeriesESService<TModel, ?> service) {
		this.service = service;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends TModel> items) throws Exception {

		service.save((List<TModel>) items);
	}
}
