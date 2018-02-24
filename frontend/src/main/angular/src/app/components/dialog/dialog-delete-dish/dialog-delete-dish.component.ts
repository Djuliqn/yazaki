import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {DishService} from "../../../dish/dish.service";

@Component({
  selector: 'app-dialog-delete-dish',
  templateUrl: './dialog-delete-dish.component.html',
  styleUrls: ['./dialog-delete-dish.component.css']
})
export class DialogDeleteDishComponent implements OnInit {


    constructor(private dialogRef: MatDialogRef<DialogDeleteDishComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
                public dishService: DishService) { }

  ngOnInit() {
  }

    onNoClick(): void {
        this.dialogRef.close();
    }

    confirmDelete(): void {
        this.dishService.deleteDishById(this.data.id);
    }

}
