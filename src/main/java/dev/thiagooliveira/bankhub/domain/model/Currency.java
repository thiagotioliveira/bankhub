package dev.thiagooliveira.bankhub.domain.model;

public enum Currency {
  EUR("€"),
  BRL("R$");

  private final String symbol;

  Currency(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }
}
