import {NgModule} from '@angular/core';

import {
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatRippleModule,
    MatTableModule,
    MatPaginatorModule,
    MatDialogModule,
    MatSortModule,
    MatSnackBarModule,
    MatCheckboxModule, MatDatepickerModule, MatNativeDateModule
} from '@angular/material';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatMomentDateModule} from "@angular/material-moment-adapter";

@NgModule({
    imports: [
        MatButtonModule,
        MatMenuModule,
        MatToolbarModule,
        MatIconModule,
        MatCardModule,
        MatTableModule,
        MatFormFieldModule,
        MatInputModule,
        MatRippleModule,
        BrowserAnimationsModule,
        MatPaginatorModule,
        MatDialogModule,
        MatSortModule,
        MatSnackBarModule,
        MatCheckboxModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatMomentDateModule
    ],
    exports: [
        MatButtonModule,
        MatMenuModule,
        MatToolbarModule,
        MatIconModule,
        MatCardModule,
        MatTableModule,
        MatFormFieldModule,
        MatInputModule,
        MatRippleModule,
        BrowserAnimationsModule,
        MatPaginatorModule,
        MatDialogModule,
        MatSortModule,
        MatSnackBarModule,
        MatCheckboxModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatMomentDateModule
    ]
})
export class MaterialModule {
}
