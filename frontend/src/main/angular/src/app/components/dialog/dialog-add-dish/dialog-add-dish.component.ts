import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Dish} from "../../../dish/dish";


@Component({
    selector: 'app-dialog-add-dish',
    templateUrl: './dialog-add-dish.component.html',
    styleUrls: ['./dialog-add-dish.component.css']
})
export class DialogAddDishComponent implements OnInit {

    form;

    constructor(private dialogRef: MatDialogRef<DialogAddDishComponent>,
                @Inject(MAT_DIALOG_DATA) public data: Dish) {
    }

    ngOnInit() {
        this.form = new FormGroup({
            nameFormControl: new FormControl('',
                [
                    Validators.required,
                    Validators.minLength(3),
                    Validators.maxLength(30)
                ]),
            descriptionFormControl: new FormControl('',
                [
                    Validators.required,
                    Validators.minLength(3),
                    Validators.maxLength(300)
                ]
            )
        });
    }

}
