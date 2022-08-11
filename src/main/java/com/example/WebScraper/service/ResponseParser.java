package com.example.WebScraper.service;

import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ResponseParser {

  private final RestTemplate restTemplate;

  public ResponseParser(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public Document parse(String url) {
    return Jsoup.parse(getResponse(url));
  }

  private String getResponse(String url) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(List.of(MediaType.APPLICATION_JSON));
      headers.add(
          "user-agent",
          "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
      HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
      return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).toString();
    } catch (HttpClientErrorException exception) {
      // TO:DO log failed urls
      System.out.println(url);
      return "";
    }
  }
}
