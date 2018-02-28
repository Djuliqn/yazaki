import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {DialogErrorComponent} from "../dialog-error/dialog-error/dialog-error.component";
import {Router} from "@angular/router";

@Component({
    selector: 'app-dialog-success',
    templateUrl: './dialog-success.component.html',
    styleUrls: ['./dialog-success.component.css']
})
export class DialogSuccessComponent implements OnInit {

    constructor(private dialogRef: MatDialogRef<DialogSuccessComponent>,
                @Inject(MAT_DIALOG_DATA) public data: any, private router: Router) {
    }

    ngOnInit() {
    }

    onNoClick(): void {
        this.dialogRef.close();
        this.router.navigateByUrl('');
    }
}
