import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Departman } from 'src/app/models/departman';
import { Status } from 'src/app/models/status';
import { Student } from 'src/app/models/student';
import { StudentService } from 'src/app/services/student.service';
import { StudentDialogComponent } from '../dialogs/student-dialog/student-dialog.component';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit, OnDestroy, OnChanges {


  displayedColumns = ['id','brojIndeksa','ime','prezime','departman','status','actions'];
  dataSource: MatTableDataSource<Student>
  subscription : Subscription;

  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;

  @Input() selectedDepartment : Departman;

  constructor(private studentService:StudentService,
              private dialog:MatDialog) { }


  ngOnChanges(changes: SimpleChanges): void {
    if(this.selectedDepartment.id){
      this.loadData();
    }
  }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.loadData()

  }

  public loadData(){
      this.subscription = this.studentService.getStudentsFromDepartments(this.selectedDepartment.id)
        .subscribe(data =>{
          this.dataSource = new MatTableDataSource(data);

          this.dataSource.filterPredicate = (data, filter: string) => {
            const accumulator = (currentTerm, key) => {
              return key === 'status' ? currentTerm + data.status.naziv : currentTerm + data[key];
            };
            const dataStr = Object.keys(data).reduce(accumulator, '').toLowerCase();
            const transformedFilter = filter.trim().toLowerCase();
            return dataStr.indexOf(transformedFilter) !== -1;
          };

          // sortiranje po nazivu ugnježdenog objekta
          this.dataSource.sortingDataAccessor = (data, property) => {
            switch (property) {
              case 'status': return data.status.naziv.toLocaleLowerCase();
              default: return data[property];
            }
          };

          this.dataSource.sort = this.sort;
          this.dataSource.paginator = this.paginator
        }),
        (error: Error) =>{
        console.log(error.name+ ' ' +error.message)
      }
    }

    public openDialog(flag: number, id?: number, brojIndeksa?: string, ime?: string, prezime? :string, departman?: Departman, status?: Status) {
      const dialogRef = this.dialog.open(StudentDialogComponent, {
        data: {id, brojIndeksa, ime, prezime, departman, status}
      });
      dialogRef.componentInstance.flag = flag;
      if(flag===1) {
        dialogRef.componentInstance.data.departman = this.selectedDepartment;
      }
      dialogRef.afterClosed()
        .subscribe(result => {
          if(result === 1) {
            this.loadData();
          }
        })
    }

    applyFilter(filterValue: string) {
      filterValue = filterValue.trim();
      filterValue = filterValue.toLocaleLowerCase();
      this.dataSource.filter = filterValue; //    JaBuKa    --> JaBuKa --> jabuka
    }
}
