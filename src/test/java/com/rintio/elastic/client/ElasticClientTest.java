package com.rintio.elastic.client;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ElasticClientTest {

    @Test
    public void creerIndexObject() {
    }

    @Test
    public void getObjects() throws Exception {
        ElasticClient elasticClient = new ElasticClient();
        List<Object> objects = elasticClient.getAllObject("axes");

    }

    @Test
    public void construireURI() {
    }

    @Test
    public void getOneObject() throws Exception{
        ElasticClient elasticClient = new ElasticClient();
        String rest = elasticClient.getOneObject("stations","station","87128535397642451148");
        System.out.println(rest);
    }
}