package com.alekseysamoylov.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Project {

  private final String name;
  private final ArrayList<StaffUnit> requiredTeams;

  public Project(String name, ArrayList<StaffUnit> requiredTeams) {
    this.name = name;
    this.requiredTeams = new ArrayList<>(requiredTeams);
  }

  public List<StaffUnit> getRequiredTeams() {
    return new ArrayList<>(requiredTeams);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Project project = (Project) o;
    return Objects.equals(name, project.name) && Objects.equals(requiredTeams, project.requiredTeams);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, requiredTeams);
  }

  @Override
  public String toString() {
    return "Project{" +
        "name='" + name + '\'' +
        ", requiredTeams=" + requiredTeams +
        '}';
  }
}
