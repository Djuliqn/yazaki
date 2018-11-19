package com.yazaki.yazaki.domain.scheduled;

import com.smattme.MysqlExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;

@Component
public class ScheduledDbBackupGeneration {

    @Autowired
    private MysqlExportService mysqlExportService;

    @Scheduled(cron = "0 0 23 * * *")
    public void generateDbBackup() {
        try {
            mysqlExportService.export();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
