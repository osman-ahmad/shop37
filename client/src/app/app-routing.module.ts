import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UploadComponent } from './components/upload.component';
import { InsertComponent } from './components/insert.component';

const routes: Routes = [
  { path: "", component: InsertComponent},
  { path: "upload", component: UploadComponent},
  { path: "**", redirectTo: "/", pathMatch: "full"}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
