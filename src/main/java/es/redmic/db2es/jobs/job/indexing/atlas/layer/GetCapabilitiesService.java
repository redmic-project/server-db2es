package es.redmic.db2es.jobs.job.indexing.atlas.layer;

import java.io.IOException;
import java.util.HashMap;

import org.geotools.ows.ServiceException;
import org.springframework.stereotype.Component;

import es.redmic.exception.atlas.LayerNotFoundException;
import es.redmic.exception.custom.ResourceNotFoundException;
import es.redmic.models.es.atlas.dto.LayerDTO;
import es.redmic.utils.geo.wms.GetCapabilities;

@Component
public class GetCapabilitiesService {

	HashMap<String, LayerDTO> layers;

	public GetCapabilitiesService() {

		layers = new HashMap<String, LayerDTO>();
	}

	public LayerDTO getCapabilitiesFromGeoServer(String urlSource, String name) {

		if (layers.containsKey(name))
			return layers.get(name);

		GetCapabilities getCapabilities;
		try {
			getCapabilities = new GetCapabilities(urlSource);
			layers.putAll(getCapabilities.getCapabilities());
		} catch (ServiceException | IOException e) {
			throw new ResourceNotFoundException(e);
		}
		LayerDTO layerOut = layers.get(name);
		if (layerOut == null)
			throw new LayerNotFoundException(name, urlSource);
		return layerOut;
	}
}
