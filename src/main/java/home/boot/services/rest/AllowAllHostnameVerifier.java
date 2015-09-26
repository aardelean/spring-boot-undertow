package home.boot.services.rest;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class AllowAllHostnameVerifier implements HostnameVerifier {

	@Override
	public final String toString() {
		return "ALLOW_ALL";
	}

	@Override
	public boolean verify(String hostname, SSLSession session) {
		//allow everything
		return true;
	}

}
