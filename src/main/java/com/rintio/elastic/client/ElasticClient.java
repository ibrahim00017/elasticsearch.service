package com.rintio.elastic.client;


import com.rintio.elastic.elastic.ElasticSearchOutput;
import com.rintio.elastic.elastic.HitObject;
import com.rintio.elastic.utils.GsonUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ElasticClient {
    RestClient restClient;
    RestClientBuilder builder;
    RestTemplate restTemplate;
    HttpComponentsClientHttpRequestFactory factory;
    private String HOST="http://elastic";
    private int port = 9200;
    private int port2 =9201;

//    public ElasticClient(String host,int port){
//        this.HOST = host;
//        this.port = port;
//    }


   // @PostConstruct
    public  ElasticClient(){
        restClient = RestClient.builder(
                new HttpHost(HOST, this.port, "http"),
                new HttpHost(HOST, this.port, "http")).build();
//
//        builder = RestClient.builder(new HttpHost(HOST, this.port))
//                .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
//                    @Override
//                    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
//                        return requestConfigBuilder.setConnectTimeout(120000)
//                                .setSocketTimeout(120000);
//                    }
//                })
//                .setMaxRetryTimeoutMillis(120000);
        factory = new HttpComponentsClientHttpRequestFactory();
        restTemplate = new RestTemplate(factory);

    }

    public Response creerIndexObject(String index, String type,Object object,int id) throws Exception{
        String operationJson = GsonUtils.toJSONWithoutClassName(object);
        Map<String, String> params = Collections.emptyMap();
        org.apache.http.HttpEntity entity = new NStringEntity(operationJson, ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("PUT", "/"+index+"/"+type+"/"+id, params, entity);
        return response;
    }
//
//    public List<Object> getObjects(String index){
//        RestTemplate restTemplate = new RestTemplate();
//        List<Object> objects =  restTemplate.getForObject("http://localhost:9200/index/_search",List.class);
//        return objects;
//    }


    public String construireURI(String index,String type) {
    return "http://"+HOST+":"+this.port+"/"+index+"/"+type;
    }

    public String construiredockerURI(String index,String type) {
        return "http://elastic:9201/"+index+"/"+type;
    }

    public SearchCreateOutput creerIndexObjectNative(String index, String type, Object object,String id) throws Exception{
        String uri = construireURI( index,type )+"/"+id;
        String requestBody = GsonUtils.toJSONWithoutClassName(object);
        MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<String> request = new HttpEntity(requestBody, headers);
        ResponseEntity<String> apiResponse = restTemplate.exchange(uri, HttpMethod.PUT,request,String.class);
        SearchCreateOutput result = GsonUtils.getObjectFromJson(apiResponse.getBody(), SearchCreateOutput.class);
        return result;
    }

    public SearchCreateOutput creerIndexObjectNativeTodocker(String index, String type, Object object,String id) throws Exception{
        String uri = construiredockerURI( index,type )+"/"+id;
        String requestBody = GsonUtils.toJSONWithoutClassName(object);
        MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<String> request = new HttpEntity(requestBody, headers);
        ResponseEntity<String> apiResponse = restTemplate.exchange(uri, HttpMethod.PUT,request,String.class);
        SearchCreateOutput result = GsonUtils.getObjectFromJson(apiResponse.getBody(), SearchCreateOutput.class);
        return result;
    }

    public SearchCreateOutput creerIndexObjectNative(String index, String type, Object object,int id) throws Exception{
        String uri = construireURI( index,type )+"/"+id;
        String requestBody = GsonUtils.toJSONWithoutClassName(object);
        MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<String> request = new HttpEntity(requestBody, headers);
        ResponseEntity<String> apiResponse = restTemplate.exchange(uri, HttpMethod.PUT,request,String.class);
        SearchCreateOutput result = GsonUtils.getObjectFromJson(apiResponse.getBody(), SearchCreateOutput.class);
        return result;
    }

//    public List<Object> getAllObject(String index){
//        String uri = "http://localhost:9200/"+index+"/_search";
//        MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
//        headers.add("Content-Type", "application/json;charset=UTF-8");
//        ElasticSearchOutput apiResponse = restTemplate.getForObject(uri,ElasticSearchOutput.class);
////        ElasticSearchOutput rest = apiResponse.getBody();
//        List<HitObject> objects = apiResponse.getHits().getHits();
//        List<Object> retour = new ArrayList<>();
//        for(HitObject obj:objects){
//            retour.add(obj.get_source());
//        }
//        return retour;
//    }

    public List<Object> getAllObject(String index) throws Exception{
        Map<String, String> params = Collections.singletonMap("pretty", "true");
        Response response = restClient.performRequest("GET", "/"+index+"/_search");
        String responseBody = EntityUtils.toString(response.getEntity());
        ModelMapper modelMapper = new ModelMapper();
//        ElasticSearchOutput elasticSearchOutput = modelMapper.map(responseBody,ElasticSearchOutput.class);
        ElasticSearchOutput elasticSearchOutput = GsonUtils.getObjectFromJson(responseBody,ElasticSearchOutput.class);
        List<HitObject> objects = elasticSearchOutput.getHits().getHits();
        List<Object> retour = new ArrayList<>();
        for(HitObject obj:objects){
            retour.add(obj.get_source());
        }
        return retour;

    }

    public String getOneObject(String index,String type,String id) throws Exception{
        Map<String, String> params = Collections.singletonMap("pretty", "true");
        Response response = restClient.performRequest("GET", "/"+index+"/"+type+"/"+id+"/_source",params);
        String responseBody = EntityUtils.toString(response.getEntity());
        return responseBody;
    }

}
