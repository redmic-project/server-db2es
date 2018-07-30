package es.redmic.db2es.jobs.job.common.geo;

import java.util.List;

import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemWriter;

import es.redmic.es.geodata.common.service.GeoDataESService;
import es.redmic.models.es.geojson.common.model.Feature;
import es.redmic.models.es.geojson.properties.model.GeoDataProperties;

public class ItemGeoWriterBase<TGeom extends Feature<GeoDataProperties, ?>> 
	extends StepExecutionListenerSupport 
		implements ItemWriter<TGeom>{
	
	GeoDataESService<?, TGeom> service;
	
	public ItemGeoWriterBase(GeoDataESService<?, TGeom> service) {
		this.service = service;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends TGeom> items) throws Exception {
		
		service.save((List<TGeom>) items);
	}
}
