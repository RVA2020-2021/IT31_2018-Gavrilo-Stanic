import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subscription } from 'rxjs';
import { Departman } from 'src/app/models/departman';
import { Status } from 'src/app/models/status';
import { Student } from 'src/app/models/student';
import { DepartmanService } from 'src/app/services/departman.service';
import { StatusService } from 'src/app/services/status.service';
import { StudentService } from 'src/app/services/student.service';
import { FakultetDialogComponent } from '../fakultet-dialog/fakultet-dialog.component';

@Component({
  selector: 'app-student-dialog',
  templateUrl: './student-dialog.component.html',
  styleUrls: ['./student-dialog.component.css']
})
export class StudentDialogComponent implements OnInit,OnDestroy {

  public flag:number;
  departmani: Departman[];
  statusi: Status[];

  constructor(public snackBar: MatSnackBar,
    public dialog:MatDialogRef<FakultetDialogComponent>,
    @Inject (MAT_DIALOG_DATA) public data:Student,
    public studentService:StudentService,
    public departmanService:DepartmanService,
    public statusService:StatusService) { }

  ngOnDestroy(): void {
    this.subscriptionDepartman.unsubscribe()
    this.subscriptionStatus.unsubscribe();
  }
    subscriptionDepartman : Subscription;
    subscriptionStatus : Subscription;

  ngOnInit(): void {
    this.subscriptionDepartman = this.departmanService.getAllDepartments()
      .subscribe(data =>{
        this.departmani = data;
      }),(error:Error) => {
        console.log(error.name+' '+error.message )

      }
      this.subscriptionStatus = this.statusService.getAllStatuses()
        .subscribe(data =>{
          this.statusi = data;
        }),(error:Error) => {
          console.log(error.name+' '+error.message )

        }

  }

  compareTo(a,b){
    return a.id == b.id
  }

  public add():void{
    this.studentService.addStudent(this.data)
    .subscribe(data =>{
      this.snackBar.open('Uspesno dodat student '+ this.data.brojIndeksa, 'U redu',{
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
    this.studentService.updateStudent(this.data)
      .subscribe(data =>{
        this.snackBar.open('Uspesno modifikovan student '+ this.data.brojIndeksa, 'U redu',{
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
    this.studentService.deleteStudent(this.data.id)
      .subscribe(() =>{
        this.snackBar.open('Uspesno obrisan student ', 'U redu',{
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
