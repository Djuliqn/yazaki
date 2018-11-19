package com.yazaki.yazaki.domain.scheduled.report;

import com.yazaki.yazaki.domain.model.DishCounter;
import com.yazaki.yazaki.domain.model.Order;
import com.yazaki.yazaki.domain.service.order.OrderService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

@Component
public class SimpleReportFiller {

    @Autowired
    private OrderService orderService;

    private String reportFileName;

    private JasperReport jasperReport;

    private JasperPrint jasperPrint;

    private DataSource dataSource;

    private Map<String, Object> parameters;

    @Autowired
    public SimpleReportFiller(DataSource dataSource) {
        parameters = newHashMap();
        reportFileName = "report.jrxml";
        this.dataSource = dataSource;
    }

    public void prepareReport() {
        compileReport();
        fillReport();
    }

    private void compileReport() {
        try {
            InputStream reportStream = getClass().getResourceAsStream("/".concat(reportFileName));
            jasperReport = JasperCompileManager.compileReport(reportStream);
            JRSaver.saveObject(jasperReport, reportFileName.replace(".jrxml", ".jasper"));
        } catch (JRException ex) {
            Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillReport() {
        try {
            final Order order = orderService.getLastAddedOrder();
            List<ReportStatisticMapper> filledData = order.getDishCounters().stream()
                                                                            .map(dishCounter -> new ReportStatisticMapper(dishCounter.getDish().getName(), dishCounter.getCounter()))
                                                                            .collect(Collectors.toList());


            JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(filledData);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, data);
        } catch (JRException  ex) {
            Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

}

