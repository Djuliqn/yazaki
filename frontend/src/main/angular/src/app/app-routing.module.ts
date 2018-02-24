import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {DishListComponent} from "./dish/dish-list/dish-list.component";
import {DailyMenuComponent} from "./daily-menu/daily-menu.component";


const routes: Routes = [
    { path: 'dish/all', component: DishListComponent },
    { path: 'dish/save', component: DishListComponent },
    { path: 'dish/update', component: DishListComponent },
    { path: 'daily-menu', component: DailyMenuComponent}
];

@NgModule({

    imports: [ RouterModule.forRoot(routes) ],

    exports: [ RouterModule ]
})

export class AppRoutingModule {
}
