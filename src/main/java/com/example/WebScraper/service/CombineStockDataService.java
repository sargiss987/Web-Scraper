package com.example.WebScraper.service;

import com.example.WebScraper.model.Stock;
import com.example.WebScraper.model.StockSearchNames;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CombineStockDataService {

  private final IndeedClient indeedClient;
  private final YahooClient yahooClient;

  public CombineStockDataService(IndeedClient indeedClient, YahooClient yahooClient) {
    this.indeedClient = indeedClient;
    this.yahooClient = yahooClient;
  }

  public Optional<List<Stock>> getStocks() {
    return yahooClient
        .extractStockSearchData()
        .map(
            names ->
                names.stream()
                    .map(
                        searchNames ->
                            new Stock(
                                concatStockNames(searchNames), concatIndustryNames(searchNames)))
                    .collect(Collectors.toList()));
  }

  private String concatStockNames(StockSearchNames searchNames) {
    return String.format(
        "%s(%s)",
        searchNames.getYahooSearchName(), searchNames.getIndeedSearchName().replace("+", " "));
  }

  private String concatIndustryNames(StockSearchNames searchNames) {
    return String.format(
        "%s %s",
        yahooClient.extractStockIndustryName(searchNames.getYahooSearchName()),
        indeedClient.extractStockIndustryName(searchNames.getIndeedSearchName()));
  }
}
