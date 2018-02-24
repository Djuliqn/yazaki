import {Component, Inject, OnInit} from '@angular/core';
import {Dish} from "../../../dish/dish";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-dialog-edit-dish',
  templateUrl: './dialog-edit-dish.component.html',
  styleUrls: ['./dialog-edit-dish.component.css']
})
export class DialogEditDishComponent implements OnInit {

    form;
  constructor(private dialogRef: MatDialogRef<DialogEditDishComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Dish) { }

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
