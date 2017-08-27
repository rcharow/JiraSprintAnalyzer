package com.analyzer.cron;

import com.analyzer.dao.JiraBoardDao;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Created by rcharow on 8/27/17.
 */
@Component
public class JiraUpdate {
    private JiraBoardDao boardDao;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JiraUpdate.class);

    @Autowired
    public JiraUpdate(JiraBoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    public void update() {
        boardDao.updateJiraBoards();
        logger.info("--------------- Updated jira boards!");
    }

}
