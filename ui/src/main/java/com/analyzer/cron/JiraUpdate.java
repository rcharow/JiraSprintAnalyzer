package com.analyzer.cron;

import com.analyzer.dao.JiraBoardDao;
import com.analyzer.dao.JiraSprintDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by rcharow on 8/27/17.
 */
@Component
public class JiraUpdate {
    private JiraBoardDao boardDao;
    private JiraSprintDao sprintDao;
    private static final Logger logger = LoggerFactory.getLogger(JiraUpdate.class);

    @Autowired
    public JiraUpdate(JiraBoardDao boardDao, JiraSprintDao sprintDao) {
        this.boardDao = boardDao;
        this.sprintDao = sprintDao;
    }

    @Scheduled(cron = "0 0 0 * * SUN")
//    @Scheduled(fixedRate = 30000)
    public void update() {
        boardDao.updateJiraBoards();
        logger.info("--------------- Updated jira boards!");

        sprintDao.updateSprints();
        logger.info("--------------- Updated jira sprints!");

    }

}
