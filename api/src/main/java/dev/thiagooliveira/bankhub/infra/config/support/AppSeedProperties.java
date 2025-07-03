package dev.thiagooliveira.bankhub.infra.config.support;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app.seed")
@Component
public class AppSeedProperties {
  private boolean enabled;
  private Set<User> users;
  private List<Bank> banks;
  private List<Category> categories;

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public List<Bank> getBanks() {
    return banks;
  }

  public void setBanks(List<Bank> banks) {
    this.banks = banks;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public static class User {
    private String name;
    private String email;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
      return Objects.hash(name, email);
    }
  }

  public static class Account {
    private String name;
    private BigDecimal balance;
    private String currency;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public BigDecimal getBalance() {
      return balance;
    }

    public void setBalance(BigDecimal balance) {
      this.balance = balance;
    }

    public String getCurrency() {
      return currency;
    }

    public void setCurrency(String currency) {
      this.currency = currency;
    }
  }

  public static class Bank {
    private String name;
    private List<Account> accounts;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public List<Account> getAccounts() {
      return accounts;
    }

    public void setAccounts(List<Account> accounts) {
      this.accounts = accounts;
    }
  }

  public static class Category {
    private String name;
    private String type;
    private boolean root;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public boolean isRoot() {
      return root;
    }

    public void setRoot(boolean root) {
      this.root = root;
    }
  }
}
