import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatSnackBar, MatSort} from "@angular/material";
import {DishService} from "../dish.service";
import {Dish} from "../dish";
import {DialogAddDishComponent} from "../../components/dialog/dialog-add-dish/dialog-add-dish.component";
import {DialogDeleteDishComponent} from "../../components/dialog/dialog-delete-dish/dialog-delete-dish.component";
import {DialogEditDishComponent} from "../../components/dialog/dialog-edit-dish/dialog-edit-dish.component";
import {DataSource} from "@angular/cdk/collections";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {DialogErrorComponent} from "../../components/dialog/dialog-error/dialog-error/dialog-error.component";

import 'rxjs/add/observable/merge';
import 'rxjs/add/observable/fromEvent';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';

@Component({
    selector: 'app-dish-list',
    templateUrl: './dish-list.component.html',
    styleUrls: ['./dish-list.component.css']
})

export class DishListComponent implements OnInit {

    displayedColumns = ["name", "description", "update", "delete"];
    id: number;
    index: number;
    dataSource: ExampleDataSource | null;

    constructor(private dishService: DishService,
                private dialog: MatDialog,
                private httpClient: HttpClient,
                private snackBar: MatSnackBar) {

    }

    @ViewChild('filter') filter: ElementRef;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    ngOnInit() {
        this.paginator._intl.itemsPerPageLabel = "Ястия на страница:";
        this.paginator._intl.firstPageLabel = "Начало";
        this.paginator._intl.lastPageLabel = "Край";
        this.paginator._intl.nextPageLabel = "Напред";
        this.paginator._intl.previousPageLabel = "Назад";
        this.loadData();
    }

    refresh() {
        this.loadData();
    }

    addNew() {
        const dialogRef = this.dialog.open(DialogAddDishComponent)

        dialogRef.afterClosed().subscribe(result => {
            if (result === 1) {
                const dish = new Dish();
                dish.name = dialogRef.componentInstance.form.value.nameFormControl;
                dish.description = dialogRef.componentInstance.form.value.descriptionFormControl;

                this.dishService.save(dish).subscribe(next => {
                        // After dialog is closed we're doing frontend updates
                        // For add we're just pushing a new row inside DataService
                        this.dishService.setDialogData(next);
                        this.dishService.dataChange.value.push(this.dishService.getDialogData());
                        this.refreshTable();
                        this.openSnackBar("Успешно добавен запис", "");
                    },
                    error => {
                        this.dialog.open(DialogErrorComponent, {
                            data: {message: error.error}
                        })
                    });
            }
        });
    }

    deleteItem(i: number, id: number, name: string, description: string) {
        this.index = i;
        this.id = id;
        const dialogRef = this.dialog.open(DialogDeleteDishComponent, {
            data: {
                name: name,
                description: description
            }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result === 1) {
                this.dishService.deleteDishById(id).subscribe(next => {
                        const foundIndex = this.dishService.dataChange.value.findIndex(x => x.id === this.id);
                        // for delete we use splice in order to remove single object from DishService

                        this.dishService.dataChange.value.splice(foundIndex, 1);
                        this.refreshTable();
                        this.openSnackBar("Успешно изтрит запис.", "");
                    },
                    error => {
                        this.dialog.open(DialogErrorComponent, {
                            data: {message: error.error}
                        })

                    });
            }
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

    startEdit(i: number, id: number, name: string, description: string) {
        this.id = id;
        // index row is used just for debugging proposes and can be removed
        this.index = i;
        console.log(this.index);
        const dialogRef = this.dialog.open(DialogEditDishComponent, {
            data: {name: name, description: description}
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result === 1) {
                const dish = new Dish();
                dish.id = id;
                dish.name = dialogRef.componentInstance.form.value.nameFormControl;
                dish.description = dialogRef.componentInstance.form.value.descriptionFormControl;

                this.dishService.update(dish).subscribe(next => {
                        // When using an edit things are little different, firstly we find record inside DishService by id
                        const foundIndex = this.dishService.dataChange.value.findIndex(x => x.id === this.id);
                        // Then you update that record using data from dialogData (values you enetered)
                        this.dishService.setDialogData(next);
                        this.dishService.dataChange.value[foundIndex] = this.dishService.getDialogData();
                        // And lastly refresh table
                        this.refreshTable();
                        this.openSnackBar("Успешно редактиран запис.", "");
                    },
                    error => {
                        this.dialog.open(DialogErrorComponent, {
                            data: {message: error.error}
                        })
                    });
            }
        });
    }

    // If you don't need a filter or a pagination this can be simplified, you just use code from else block
    private refreshTable() {
        // if there's a paginator active we're using it for refresh
        if (this.dataSource._paginator.hasNextPage()) {
            this.dataSource._paginator.nextPage();
            this.dataSource._paginator.previousPage();
            // in case we're on last page this if will tick
        } else if (this.dataSource._paginator.hasPreviousPage()) {
            this.dataSource._paginator.previousPage();
            this.dataSource._paginator.nextPage();
            // in all other cases including active filter we do it like this
        } else {
            this.dataSource.filter = '';
            this.dataSource.filter = this.filter.nativeElement.value;
        }
    }

    private openSnackBar(message: string, action: string) {
        this.snackBar.open(message, action, {
            duration: 2000,
        });
    }
}

export class ExampleDataSource extends DataSource<Dish> {
    _filterChange = new BehaviorSubject('');

    get filter(): string {
        return this._filterChange.value;
    }

    set filter(filter: string) {
        this._filterChange.next(filter);
    }

    filteredData: Dish[] = [];
    renderedData: Dish[] = [];

    constructor(public _dishService: DishService,
                public _paginator: MatPaginator,
                public _sort: MatSort) {
        super();
        // Reset to the first page when the user changes the filter.
        this._filterChange.subscribe(() => this._paginator.pageIndex = 0);
    }

    /** Connect function called by the table to retrieve one stream containing the data to render. */
    connect(): Observable<Dish[]> {
        // Listen for any changes in the base data, sorting, filtering, or pagination
        const displayDataChanges = [
            this._dishService.dataChange,
            this._sort.sortChange,
            this._filterChange,
            this._paginator.page
        ];

        this._dishService.getAllDishes();

        return Observable.merge(...displayDataChanges).map(() => {
            // Filter data
            this.filteredData = this._dishService.data.slice().filter((dish: Dish) => {
                const searchStr = (dish.name + dish.description).toLowerCase();
                return searchStr.indexOf(this.filter.toLowerCase()) !== -1;
            });

            // Sort filtered data
            const sortedData = this.sortData(this.filteredData.slice());

            // Grab the page's slice of the filtered sorted data.
            const startIndex = this._paginator.pageIndex * this._paginator.pageSize;
            this.renderedData = sortedData.splice(startIndex, this._paginator.pageSize);
            return this.renderedData;
        });
    }

    disconnect() {
    }


    /** Returns a sorted copy of the database data. */
    sortData(data: Dish[]): Dish[] {
        if (!this._sort.active || this._sort.direction === '') {
            return data;
        }

        return data.sort((a, b) => {
            let propertyA: number | string = '';
            let propertyB: number | string = '';

            switch (this._sort.active) {
                case 'name':
                    [propertyA, propertyB] = [a.name, b.name];
                    break;
                case 'description':
                    [propertyA, propertyB] = [a.description, b.description];
                    break;
            }

            const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
            const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

            return (valueA < valueB ? -1 : 1) * (this._sort.direction === 'asc' ? 1 : -1);
        });
    }
}
