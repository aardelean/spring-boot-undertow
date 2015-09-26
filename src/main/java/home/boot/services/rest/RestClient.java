package home.boot.services.rest;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for issuing rest requests parametrized.
 *
 * Uses {@link AllowAllHostnameVerifier} and {@link TrustAllTrustManager} => no SSL validation.
 *
 * @author aardelean
 *
 */
@Component
public class RestClient {

	private Client client;

	/**
	 * Setting up the proper configuration for serializing the requests sent.
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	@PostConstruct
	public void setUp() throws KeyManagementException, NoSuchAlgorithmException {
		SSLContext ctx = SSLContext.getInstance("SSL");
		X509TrustManager trustManager = new TrustAllTrustManager();
		TrustManager[] tm = { trustManager };
		ctx.init(null, tm, null);

		ClientConfig config = new ClientConfig();
		config.property(ClientProperties.READ_TIMEOUT, 300000);

		client = ClientBuilder.newBuilder().sslContext(ctx).hostnameVerifier(new AllowAllHostnameVerifier()).build();
	}


	public WebTarget targetWithParams(String url) {
		return client.target(url);
	}

}
