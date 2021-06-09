import { Component, Inject, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Fakultet } from 'src/app/models/fakultet';
import { FakultetService } from 'src/app/services/fakultet.service';
import {MatDialogRef,MAT_DIALOG_DATA} from '@angular/material/dialog'

@Component({
  selector: 'app-fakultet-dialog',
  templateUrl: './fakultet-dialog.component.html',
  styleUrls: ['./fakultet-dialog.component.css']
})
export class FakultetDialogComponent implements OnInit {

  public flag: number;

  constructor(public snackBar: MatSnackBar,
              public dialog:MatDialogRef<FakultetDialogComponent>,
              @Inject (MAT_DIALOG_DATA) public data:Fakultet,
              public fakultetService:FakultetService) { }

  ngOnInit(): void {
  }

  public add():void{
      this.fakultetService.addFaculty(this.data)
        .subscribe(data =>{
          this.snackBar.open('Uspesno dodat fakultet '+ this.data.naziv, 'U redu',{
            duration : 2500
          });
        }),
        (error:Error) => {
          console.log(error.name+' '+error.message )
          this.snackBar.open('Dogodila se greska. Pokusajte ponovo! ' , 'Zatvori',{
            duration : 2500
          });
        }

  }

  public update():void{
    this.fakultetService.updateFaculty(this.data)
      .subscribe(data =>{
        this.snackBar.open('Uspesno modifikovan fakultet '+ this.data.naziv, 'U redu',{
          duration : 2500
        });
      }),
      (error:Error) => {
        console.log(error.name+' '+error.message )
        this.snackBar.open('Dogodila se greska. Pokusajte ponovo! ' , 'Zatvori',{
          duration : 2500
        });
      }
  }

  public delete():void{
    this.fakultetService.deleteFaculty(this.data.id)
      .subscribe(() =>{
        this.snackBar.open('Uspesno obrisan fakultet ', 'U redu',{
          duration : 2500
        });
      }),
      (error:Error) => {
        console.log(error.name+' '+error.message )
        this.snackBar.open('Dogodila se greska. Pokusajte ponovo! ' , 'Zatvori',{
          duration : 2500
        });
      }
  }

  public cancel():void{
    this.dialog.close();
    this.snackBar.open('Odustali ste od izmena', 'U redu',{
      duration : 1000
    });

  }
}
