import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Departman } from 'src/app/models/departman';
import { DepartmanService } from 'src/app/services/departman.service';
import {Fakultet} from 'src/app/models/fakultet'
import { FakultetService } from 'src/app/services/fakultet.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-departman-dialog',
  templateUrl: './departman-dialog.component.html',
  styleUrls: ['./departman-dialog.component.css']
})
export class DepartmanDialogComponent implements OnInit, OnDestroy {

  fakulteti: Fakultet[];
  public flag:number;
  subscription :Subscription;
  constructor(public snackBar: MatSnackBar,
              public dialog:MatDialogRef<DepartmanDialogComponent>,
              @Inject (MAT_DIALOG_DATA) public data:Departman,
              public departmanService:DepartmanService,
              public fakultetService: FakultetService) { }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.subscription = this.fakultetService.getAllFaculties()
      .subscribe(data =>{
        this.fakulteti = data;
      }),(error:Error) => {
        console.log(error.name+' '+error.message )

      }
  }
  compareTo(a,b){
    return a.id == b.id
  }


  public add():void{
    this.departmanService.addDepartment(this.data)
    .subscribe(data =>{
      this.snackBar.open('Uspesno dodat departman '+ this.data.naziv, 'U redu',{
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
    this.departmanService.updateDepartment(this.data)
      .subscribe(data =>{
        this.snackBar.open('Uspesno modifikovan departman '+ this.data.naziv, 'U redu',{
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
      this.departmanService.deleteDepartment(this.data.id)
        .subscribe(() =>{
          this.snackBar.open('Uspesno obrisan departman ', 'U redu',{
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
