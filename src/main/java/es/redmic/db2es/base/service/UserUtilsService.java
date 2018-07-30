package es.redmic.db2es.base.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.redmic.es.common.service.UserUtilsServiceItfc;
import es.redmic.utils.httpclient.HttpClient;

@Service
public class UserUtilsService implements UserUtilsServiceItfc {

	protected static Logger logger = LogManager.getLogger();

	HttpClient client = new HttpClient();

	@Value("${oauth.userid.endpoint}")
	String GET_USERID_URL;

	String userId;

	@Override
	public String getUserId() {

		return "1";
	}

	@Override
	public List<String> getUserRole() {

		List<String> roles = new ArrayList<String>(
				Arrays.asList("ROLE_ADMINISTRATOR", "ROLE_OAG", "ROLE_CLIENT", "ROLE_COLLABORATOR", "ROLE_GUEST"));

		return roles;
	}

	@Override
	public List<Long> getAccessibilityControl() {

		List<Long> accessibilities = new ArrayList<Long>();

		if (getUserRole().contains("ROLE_ANONYMOUS")) {
			accessibilities.add(2L);
		}
		return accessibilities;
	}
}