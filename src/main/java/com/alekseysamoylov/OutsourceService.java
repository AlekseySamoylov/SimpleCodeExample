package com.alekseysamoylov;


import com.alekseysamoylov.model.OutsourceAttemptResult;
import com.alekseysamoylov.model.Project;
import com.alekseysamoylov.model.StaffType;
import com.alekseysamoylov.model.StaffUnit;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class OutsourceService {

  private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
  private final Map<StaffType, Integer> availableResourcesMap = new HashMap<>();

  public OutsourceService(Map<StaffType, Integer> availableResourcesMap) {
    this.availableResourcesMap.putAll(availableResourcesMap);
  }

  /**
   * Thread safe to allocate staff
   *
   * @param project with required staff
   * @return SUCCESS or FAIL
   */
  public OutsourceAttemptResult tryOutsource(Project project) {
    log.info("Try to outsource {}", project);
    if (project != null) {
      synchronized (this) {
        for (StaffUnit requestedTeam : project.getRequiredTeams()) {
          var availableNumber = availableResourcesMap.get(requestedTeam.getType());
          if (availableNumber == null) {
            log.warn("Requested number {} is not presented for the type {}", requestedTeam.getNumber(),
                requestedTeam.getType());
            return OutsourceAttemptResult.FAIL;
          }
          if (availableNumber < requestedTeam.getNumber()) {
            log.warn("Requested number {} is more than available number {} for the type {}", requestedTeam.getNumber(),
                availableNumber, requestedTeam.getType());
            return OutsourceAttemptResult.FAIL;
          }
        }
        project.getRequiredTeams().forEach(requestedTeam -> availableResourcesMap.put(requestedTeam.getType(),
            availableResourcesMap.get(requestedTeam.getType()) - requestedTeam.getNumber()));
        log.debug("Available resources {}", availableResourcesMap);
        return OutsourceAttemptResult.SUCCESS;
      }
    } else {
      log.error("Attempt to outsource null");
      return OutsourceAttemptResult.FAIL;
    }
  }
}
