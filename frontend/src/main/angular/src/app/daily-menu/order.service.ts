import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {DailyMenu} from "./daily-menu";
import {Observable} from "rxjs/Observable";

@Injectable()
export class OrderService {

    private saveDailyMenuUrl = 'http://localhost:8080/daily-menu/save';

    constructor(private http: HttpClient) {
    }

    saveDailyMenu(dailyMenu: DailyMenu): Observable<DailyMenu> {
        return this.http.post(this.saveDailyMenuUrl, dailyMenu).pipe();
    }


}
