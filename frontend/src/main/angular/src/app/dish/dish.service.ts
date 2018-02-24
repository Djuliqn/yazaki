import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {Dish} from "./dish";

import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import {BehaviorSubject} from "rxjs/BehaviorSubject";

@Injectable()
export class DishService {

    private getDishesUrl = 'http://localhost:8080/dish/all';
    private deleteDishUrl = 'http://localhost:8080/dish/delete/';
    private saveDishUrl = 'http://localhost:8080/dish/save';
    private updateDishUrl = 'http://localhost:8080/dish/update';

    dataChange: BehaviorSubject<Dish[]> = new BehaviorSubject<Dish[]>([]);

    dialogData: any;

    constructor(private http: HttpClient) {
    }

    get data(): Dish[] {
        return this.dataChange.value;
    }

    getDialogData() {
        return this.dialogData;
    }

    setDialogData(dialogData: any) {
        this.dialogData = dialogData;
    }

    getAllDishes(): void {
        this.http.get<Dish[]>(this.getDishesUrl).subscribe(data => {
                this.dataChange.next(data);
            },
            (error: HttpErrorResponse) => {
                console.log(error.name + ' ' + error.message);
            });
    }

    save(dish: Dish): Observable<Dish> {
        return this.http.post(this.saveDishUrl, dish).pipe();
    }

    deleteDishById(id: number) {
        return this.http.delete(this.deleteDishUrl + id).pipe();
    }

    update(dish: Dish): Observable<Dish> {
        return this.http.put<Dish>(this.updateDishUrl, dish).pipe();
    }
}

