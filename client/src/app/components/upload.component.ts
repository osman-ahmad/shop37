import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { UploadService } from '../services/upload.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit{
  imageData = "";
  form!: FormGroup;
  // blob!: Blob;


  constructor(private router: Router, private formbuilder: FormBuilder,
    private uploadsvc: UploadService){

  }

  ngOnInit(): void {
    if(!this.uploadsvc.imageData){
      this.router.navigate(['/']);
      return;
    }

    this.imageData = this.uploadsvc.imageData;
    this.createForm();
    // this.blob = this.dataURItoBlob(this.imageData);
  }

  // upload(){
  //   const formVal = this.form.value;
  //   this.uploadsvc.upload(formVal, this.blob)
  //     .then((result)=>{
  //       this.router.navigate(['/']);
  //     }).catch(error=> console.log(error))
  // }

  upload(): void {
    const formValue = this.form.value;
    const file = this.dataURItoFile(this.imageData, 'image'); // Convert data URI to File object

    this.uploadsvc.upload(formValue, file)
      .then((result) => {
        this.router.navigate(['/']);
      })
      .catch((error) => console.log(error));
  }

  private createForm(){
    this.form = this.formbuilder.group({
      title: this.formbuilder.control<string>(''),
      complain: this.formbuilder.control<string>(''),
    });
  }

  // dataURItoBlob(dataURI: String){
  //   var byteString = atob(dataURI.split(',')[1]);
  //   let mimeString = dataURI.split(',')[0].split(';')[0];
  //   var ar = new ArrayBuffer(byteString.length);
  //   var ai = new Uint8Array(ar);
  //   for (var i=0; i <byteString.length; i++){
  //     ai[i] = byteString.charCodeAt(i);
  //   }
  //   return new Blob([ar], {type: mimeString});
  // }
  private dataURItoFile(dataURI: string, fileName: string): File {
    const byteString = atob(dataURI.split(',')[1]);
    const mimeString = dataURI.split(',')[0].split(';')[0].split(':')[1].split('/')[1];
    const ab = new ArrayBuffer(byteString.length);
    const ia = new Uint8Array(ab);
    for (let i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i);
    }
    const file = new File([ab], fileName, { type: `image/${mimeString}` });
    return file;
  }
}
