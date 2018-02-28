import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {DailyMenu} from "./daily-menu";
import {Observable} from "rxjs/Observable";
import {StatisticForm} from "../statistic/statistic";

@Injectable()
export class OrderService {

    private saveDailyMenuUrl = 'http://localhost:8080/daily-menu/save';
    private statistics = 'http://localhost:8080/statistics';

    constructor(private http: HttpClient) {
    }

    saveDailyMenu(dailyMenu: DailyMenu): Observable<DailyMenu> {
        return this.http.post(this.saveDailyMenuUrl, dailyMenu).pipe();
    }

    getStatistic(statisticForm: StatisticForm): Observable<any> {
        let params = new HttpParams().set("year", statisticForm.year.toString())
                                     .set("month", statisticForm.month.toString())
                                     .set("day", statisticForm.day.toString()); //Create new HttpParams

        return this.http.get(this.statistics, {params: params}).pipe();
    }

}
