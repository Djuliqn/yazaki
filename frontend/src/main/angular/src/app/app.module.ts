import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {MaterialModule} from "./material/material.module";
import {AppRoutingModule} from './app-routing.module';
import {DishModule} from "./dish/dish.module";
import {HttpClientModule} from "@angular/common/http";

import 'hammerjs';

import {AppComponent} from './app.component';
import {NavBarComponent} from './components/nav-bar/nav-bar.component';
import {DishService} from "./dish/dish.service";
import {DialogAddDishComponent} from './components/dialog/dialog-add-dish/dialog-add-dish.component';
import {ReactiveFormsModule} from "@angular/forms";
import {DialogDeleteDishComponent} from './components/dialog/dialog-delete-dish/dialog-delete-dish.component';
import {DialogEditDishComponent} from './components/dialog/dialog-edit-dish/dialog-edit-dish.component';
import {DialogErrorComponent} from './components/dialog/dialog-error/dialog-error/dialog-error.component';
import {DailyMenuComponent} from './daily-menu/daily-menu.component';
import {OrderService} from "./daily-menu/order.service";
import {MAT_DATE_LOCALE} from "@angular/material";
import {DialogSuccessComponent} from './components/dialog/dialog-success/dialog-success.component';
import {StatisticComponent} from './statistic/statistic.component';

@NgModule({
    declarations: [
        AppComponent,
        NavBarComponent,
        DialogAddDishComponent,
        DialogDeleteDishComponent,
        DialogEditDishComponent,
        DialogErrorComponent,
        DailyMenuComponent,
        DialogSuccessComponent,
        StatisticComponent
    ],
    imports: [
        BrowserModule,
        MaterialModule,
        DishModule,
        AppRoutingModule,
        HttpClientModule,
        ReactiveFormsModule
    ],

    providers: [
        DishService,
        OrderService,
        {provide: MAT_DATE_LOCALE, useValue: 'ja-JP'},
    ],

    entryComponents: [
        DialogAddDishComponent,
        DialogDeleteDishComponent,
        DialogEditDishComponent,
        DialogErrorComponent,
        DialogSuccessComponent
    ],

    bootstrap: [AppComponent]
})
export class AppModule {
}
