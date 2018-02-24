import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {DateAdapter, MatDatepickerInputEvent, MatPaginator, MatSort} from "@angular/material";
import {DishService} from "../dish/dish.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {ExampleDataSource} from "../dish/dish-list/dish-list.component";
import {OrderService} from "./order.service";
import {SelectionModel} from "@angular/cdk/collections";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {DailyMenu} from "./daily-menu";

@Component({
    selector: 'app-daily-menu',
    templateUrl: './daily-menu.component.html',
    styleUrls: ['./daily-menu.component.css']
})
export class DailyMenuComponent implements OnInit {

    displayedColumns = ["check", "name", "description"];
    dataSource: ExampleDataSource | null;
    selection = new SelectionModel<string>(true, []);

    dailyMenuForm;

    constructor(private dishService: DishService,
                private httpClient: HttpClient,
                private orderService: OrderService,
                private adapter: DateAdapter<any>) {
    }

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild('filter') filter: ElementRef;
    @ViewChild(MatSort) sort: MatSort;

    ngOnInit() {
        this.paginator._intl.itemsPerPageLabel = "Ястия на страница:";
        this.paginator._intl.firstPageLabel = "Начало";
        this.paginator._intl.lastPageLabel = "Край";
        this.paginator._intl.nextPageLabel = "Напред";
        this.paginator._intl.previousPageLabel = "Назад";
        this.loadData();
        this.adapter.setLocale('bg');

        this.dailyMenuForm = new FormGroup({
            pickedDate: new FormControl('', [Validators.required])
        });
    }

    public loadData() {
        this.dishService = new DishService(this.httpClient);
        this.dataSource = new ExampleDataSource(this.dishService, this.paginator, this.sort);

        Observable.fromEvent(this.filter.nativeElement, 'keyup')
            .debounceTime(150)
            .distinctUntilChanged()
            .subscribe(() => {
                if (!this.dataSource) {
                    return;
                }
                this.dataSource.filter = this.filter.nativeElement.value;
            });
    }

    setDate(): void {
        // Set today date using the patchValue function
        let date = new Date(this.dailyMenuForm.get('pickedDate').value._d)

        let year = date.getFullYear();
        let month = date.getMonth() + 1;
        let day = date.getDate();
    }

    addDailyMenu() {
        let date = new Date(this.dailyMenuForm.get('pickedDate').value._d);

        console.log(this.selection.selected);

        const dailyMenu = new DailyMenu(new Array(this.selection.selected.length), date);

        this.selection.selected.forEach( (id, index) => {
            dailyMenu.dishIds[index] = Number(id);
        });

        this.orderService.saveDailyMenu(dailyMenu).subscribe();
    }

}
