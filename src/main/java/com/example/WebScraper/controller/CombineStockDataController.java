package com.example.WebScraper.controller;

import com.example.WebScraper.model.Stock;
import com.example.WebScraper.service.CombineStockDataService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CombineStockDataController {

  private final CombineStockDataService service;

  public CombineStockDataController(CombineStockDataService service) {
    this.service = service;
  }

  @GetMapping("api/v1/stocks")
  public ResponseEntity<List<Stock>> get() {
    return service.getStocks().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
}
