package com.yazaki.yazaki.domain.scheduled;

import com.yazaki.yazaki.domain.scheduled.report.SimpleReportExporter;
import com.yazaki.yazaki.domain.scheduled.report.SimpleReportFiller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledReportGeneration {

    @Autowired
    private SimpleReportExporter simpleReportExporter;

    @Autowired
    private SimpleReportFiller simpleReportFiller;

    //fixedRate = 5000
    @Scheduled(cron = "0 0 22 * * *")
    public void generateStatisticReport() {
        simpleReportFiller.prepareReport();
        simpleReportExporter.setJasperPrint(simpleReportFiller.getJasperPrint());
        simpleReportExporter.exportToXlsx();
    }
}
