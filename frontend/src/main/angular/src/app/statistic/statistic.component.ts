import {Component, OnInit, ViewChild} from '@angular/core';
import {DateAdapter, MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {OrderService} from "../daily-menu/order.service";
import {StatisticForm} from "./statistic";

@Component({
    selector: 'app-statistic',
    templateUrl: './statistic.component.html',
    styleUrls: ['./statistic.component.css']
})
export class StatisticComponent implements OnInit {

    displayedColumns = ["name", "counter"];
    dataSource = new MatTableDataSource<Element>();

    constructor(private adapter: DateAdapter<any>,
                private orderService: OrderService,) {
    }

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    ngOnInit() {
        this.paginator._intl.itemsPerPageLabel = "Ястия на страница:";
        this.paginator._intl.firstPageLabel = "Начало";
        this.paginator._intl.lastPageLabel = "Край";
        this.paginator._intl.nextPageLabel = "Напред";
        this.paginator._intl.previousPageLabel = "Назад";
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.adapter.setLocale('bg');
    }

    onPickedDate($event) {
        console.log($event._i);

        let statistic = new StatisticForm($event._i.year, $event._i.month + 1, $event._i.date);

        this.orderService.getStatistic(statistic).subscribe(next => {
            let elements: Array<Element> = [];
            next.dishCounters.forEach(data => {
                let element = new Element();

                element.name = data.dish.name;
                element.counter = data.counter;

                elements.push(element);
            });

            this.dataSource.data = elements;
        }, error => {
            this.dataSource.data = new Array();
        });
    }
}

export class Element {

    constructor(public name?: string, public counter?: number) {

    }
}
