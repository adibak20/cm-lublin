package example.rest;

import example.rest.config.Endpoint;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.logging.Logger;

public class CoreRestClient {

    public static Logger logger = Logger.getLogger(CoreRestClient.class.getName());

    private static String BASE_URL = "http://localhost:8080";

    private static String result = null;

    static public String getPatients() {
        HttpGet request = new HttpGet(BASE_URL + Endpoint.PATIENT_LIST.getPatch());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        return execute(request, httpClient);
    }

    private static String execute(HttpRequestBase request, CloseableHttpClient httpClient) {
        request.addHeader("Content-Type", "application/json; charset=UTF-8");
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity,  "UTF-8");
            }
        } catch( Exception e) {
            logger.severe("Błąd polaczenia" +e.getMessage());
            return null;
        }
        return result;
    }

    static public String findPatients(String keyword) {
        HttpGet request = new HttpGet(BASE_URL + Endpoint.PATIENT_FIND.getPatch(keyword));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        return execute(request, httpClient);
    }

    static public String addPatient(String json) throws IOException {
        HttpPost request = new HttpPost(BASE_URL + Endpoint.PATIENT_ADD.getPatch());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        request.setEntity(new StringEntity(json, "UTF-8"));
        return execute(request, httpClient);
    }

    static public String getVisitsByPatient(Long patientId) throws IOException {
        HttpGet request = new HttpGet(BASE_URL + Endpoint.VISIT_LIST.getPatch(patientId));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        return execute(request, httpClient);
    }

    static public String deletePatient(Long patientId) throws IOException {
        HttpDelete request = new HttpDelete(BASE_URL + Endpoint.PATIENT_DELETE.getPatch(patientId));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        return execute(request, httpClient);
    }
}
