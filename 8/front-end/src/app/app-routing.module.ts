import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InputformComponent } from './inputform/inputform/inputform.component';

const routes: Routes = [
  {path:'',component:InputformComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)], 
  exports: [RouterModule]
})
export class AppRoutingModule { } 
