import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {DialogErrorComponent} from "../dialog-error/dialog-error/dialog-error.component";

@Component({
    selector: 'app-dialog-success',
    templateUrl: './dialog-success.component.html',
    styleUrls: ['./dialog-success.component.css']
})
export class DialogSuccessComponent implements OnInit {

    constructor(private dialogRef: MatDialogRef<DialogSuccessComponent>,
                @Inject(MAT_DIALOG_DATA) public data: any) {
    }

    ngOnInit() {
    }

    onNoClick(): void {
        this.dialogRef.close();
    }
}
