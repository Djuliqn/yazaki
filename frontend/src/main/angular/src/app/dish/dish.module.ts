import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DishListComponent} from './dish-list/dish-list.component';
import {MaterialModule} from "../material/material.module";
import {DishService} from "./dish.service";

@NgModule({
    imports: [
        CommonModule,
        MaterialModule
    ],

    declarations: [
        DishListComponent
    ],

    providers: [DishService]
})
export class DishModule {
}
