package com.alekseysamoylov.model;


import java.util.Objects;

public final class StaffUnit  {

  private final StaffType type;
  private final int number;
  public StaffUnit(StaffType type, int number) {
    this.type = type;
    this.number = number;
  }

  public StaffType getType() {
    return type;
  }

  public int getNumber() {
    return number;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StaffUnit javaTeam = (StaffUnit) o;
    return number == javaTeam.number && type == javaTeam.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, number);
  }

  @Override
  public String toString() {
    return "StaffUnit{" +
        "type=" + type +
        ", number=" + number +
        '}';
  }
}
