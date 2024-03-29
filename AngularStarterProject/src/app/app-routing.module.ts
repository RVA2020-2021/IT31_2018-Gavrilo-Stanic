import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AboutComponent } from './components/core/about/about.component';
import { AuthorComponent } from './components/core/author/author.component';
import { HomeComponent } from './components/core/home/home.component';
import { DepartmentComponent } from './components/department/department.component';
import { FacultyComponent } from './components/faculty/faculty.component';
import { StatusComponent } from './components/status/status.component';
import { StudentComponent } from './components/student/student.component';


const routes: Routes = [
  {path: 'fakultet', component:FacultyComponent},
  {path: 'departman', component:DepartmentComponent},
  {path: 'status', component:StatusComponent},
  {path: 'student', component:StudentComponent},
  {path: 'home', component:HomeComponent},
  {path: 'about', component:AboutComponent},
  {path: 'author', component:AuthorComponent},
  {path: '', redirectTo: '/home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
