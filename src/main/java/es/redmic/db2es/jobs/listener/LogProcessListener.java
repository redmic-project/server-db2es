package es.redmic.db2es.jobs.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;

import es.redmic.databaselib.common.model.CharacterModel;
import es.redmic.databaselib.common.model.LongModel;

public class LogProcessListener implements ItemProcessListener<Object, Object> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogProcessListener.class);
	

	public void afterProcess(Object item, Object result) {
		String id = null;
		if (LongModel.class.isInstance(item)) {
			id = ((LongModel)item).getId().toString();
		} else if (CharacterModel.class.isInstance(item)) {
			id = ((CharacterModel)item).getId().toString();
		}
		
		LOGGER.info("Input to Processor: " + id);
		LOGGER.info("Output of Processor: " + result.toString());
	}

	public void beforeProcess(Object item) {
	}

	public void onProcessError(Object item, Exception e) {
	}
}
