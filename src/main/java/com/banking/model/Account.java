package com.banking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "account")
public class Account {

  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "name")
  private String name;

  //for JPA
  protected Account() {}

  public Account(UUID id, String name) {
    this.id = id;
    this.name = name;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Account account = (Account) o;
    return id.equals(account.id) && name.equals(account.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
