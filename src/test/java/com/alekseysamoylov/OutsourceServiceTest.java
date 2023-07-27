package com.alekseysamoylov;

import static org.assertj.core.api.Assertions.assertThat;

import com.alekseysamoylov.model.OutsourceAttemptResult;
import com.alekseysamoylov.model.Project;
import com.alekseysamoylov.model.StaffType;
import com.alekseysamoylov.model.StaffUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;


class OutsourceServiceTest {

  @Test
  public void shouldOutsourceProject() {
    // Given
    Map<StaffType, Integer> availableResourcesMap = new HashMap<>();
    availableResourcesMap.put(StaffType.JAVA_DEVELOPER, 5);
    availableResourcesMap.put(StaffType.QA, 5);

    var outsourceService = new OutsourceService(availableResourcesMap);
    var requiredTeams = new ArrayList<StaffUnit>();
    requiredTeams.add(new StaffUnit(StaffType.JAVA_DEVELOPER, 5));
    requiredTeams.add(new StaffUnit(StaffType.QA, 5));
    var project = new Project("CentralBankService", requiredTeams);

    // When
    var result = outsourceService.tryOutsource(project);

    // Then
    assertThat(result).isEqualTo(OutsourceAttemptResult.SUCCESS);
  }
  @Test
  public void shouldOutsourceProjectInTwoRequests() {
    // Given
    Map<StaffType, Integer> availableResourcesMap = new HashMap<>();
    availableResourcesMap.put(StaffType.JAVA_DEVELOPER, 10);

    var outsourceService = new OutsourceService(availableResourcesMap);
    var requiredTeams = new ArrayList<StaffUnit>();
    requiredTeams.add(new StaffUnit(StaffType.JAVA_DEVELOPER, 5));
    var project = new Project("CentralBankService", requiredTeams);

    // When
    var firstResult = outsourceService.tryOutsource(project);
    var secondResult = outsourceService.tryOutsource(project);

    // Then
    assertThat(firstResult).isEqualTo(OutsourceAttemptResult.SUCCESS);
    assertThat(secondResult).isEqualTo(OutsourceAttemptResult.SUCCESS);
  }

  @Test
  public void shouldFailToOutsourceProjectBecauseOfWrongAmount() {
    // Given
    Map<StaffType, Integer> availableResourcesMap = new HashMap<>();
    availableResourcesMap.put(StaffType.JAVA_DEVELOPER, 5);

    var outsourceService = new OutsourceService(availableResourcesMap);
    var requiredTeams = new ArrayList<StaffUnit>();
    requiredTeams.add(new StaffUnit(StaffType.JAVA_DEVELOPER, 10));
    var project = new Project("CentralBankService", requiredTeams);

    // When
    var result = outsourceService.tryOutsource(project);

    // Then
    assertThat(result).isEqualTo(OutsourceAttemptResult.FAIL);
  }

  @Test
  public void shouldFailToOutsourceProjectInSecondBooking() {
    // Given
    Map<StaffType, Integer> availableResourcesMap = new HashMap<>();
    availableResourcesMap.put(StaffType.JAVA_DEVELOPER, 5);

    var outsourceService = new OutsourceService(availableResourcesMap);
    var requiredTeams = new ArrayList<StaffUnit>();
    requiredTeams.add(new StaffUnit(StaffType.JAVA_DEVELOPER, 5));
    var project = new Project("CentralBankService", requiredTeams);

    // When
    outsourceService.tryOutsource(project);
    var result = outsourceService.tryOutsource(project);

    // Then
    assertThat(result).isEqualTo(OutsourceAttemptResult.FAIL);
  }

  @Test
  public void shouldFailToOutsourceProjectBecauseOfWrongType() {
    // Given
    Map<StaffType, Integer> availableResourcesMap = new HashMap<>();
    availableResourcesMap.put(StaffType.JAVA_DEVELOPER, 5);

    var outsourceService = new OutsourceService(availableResourcesMap);
    var requiredTeams = new ArrayList<StaffUnit>();
    requiredTeams.add(new StaffUnit(StaffType.QA, 5));
    var project = new Project("CentralBankService", requiredTeams);

    // When
    outsourceService.tryOutsource(project);
    var result = outsourceService.tryOutsource(project);

    // Then
    assertThat(result).isEqualTo(OutsourceAttemptResult.FAIL);
  }

}
