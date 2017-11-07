package com.analyzer.cron;

import com.analyzer.dao.JiraBoardDao;
import com.analyzer.dao.JiraIssueDao;
import com.analyzer.dao.JiraSprintDao;
import com.analyzer.dao.JiraWorklogDao;
import com.analyzer.domain.JiraSprint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by rcharow on 8/27/17.
 */
@Component
public class JiraUpdate {
    private JiraBoardDao boardDao;
    private JiraSprintDao sprintDao;
    private JiraIssueDao issueDao;
    private JiraWorklogDao worklogDao;
    private static final Logger logger = LoggerFactory.getLogger(JiraUpdate.class);

    @Autowired
    public JiraUpdate(JiraBoardDao boardDao, JiraSprintDao sprintDao, JiraIssueDao issueDao, JiraWorklogDao worklogDao) {
        this.boardDao = boardDao;
        this.sprintDao = sprintDao;
        this.issueDao = issueDao;
        this.worklogDao = worklogDao;
    }

    @PostConstruct
    public void onStartup() {
        update();
    }

//    @Scheduled(cron = "0 0 0 * * SUN")
//    @Scheduled(fixedRate = 30000)
    public void update() {
        boardDao.updateJiraBoards();
        logger.info("--------------- Updated jira boards!");

        sprintDao.updateSprints();
        logger.info("--------------- Updated jira sprints!");


        List<JiraSprint> issueSprints = sprintDao.getClosedSprintsWithUnsyncedIssues();
        for (JiraSprint sprint : issueSprints) {
            issueDao.updateJiraSprintIssues(sprint);
        }
        logger.info("--------------- Updated jira issues!");

        List<JiraSprint> worklogSprints = sprintDao.getClosedSprintsWithUnsyncedWorklogs();
        for (JiraSprint sprint : worklogSprints) {
            worklogDao.updateJiraSprintWorklogs(sprint);
        }
        logger.info("--------------- Updated jira worklogs!");

    }

}
