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
  blob!: Blob;


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
    this.blob = this.dataURItoBlob(this.imageData);
  }

  upload(){
    const formVal = this.form.value;
    this.uploadsvc.upload(formVal, this.blob)
      .then((result)=>{
        this.router.navigate(['/']);
      }).catch(error=> console.log(error))
  }

  private createForm(){
    this.form = this.formbuilder.group({
      title: this.formbuilder.control<string>(''),
      complain: this.formbuilder.control<string>(''),
    });
  }

  dataURItoBlob(dataURI: String){
    var byteString = atob(dataURI.split(',')[1]);
    let mimeString = dataURI.split(',')[0].split(';')[0];
    var ar = new ArrayBuffer(byteString.length);
    var ai = new Uint8Array(ar);
    for (var i=0; i <byteString.length; i++){
      ai[i] = byteString.charCodeAt(i);
    }
    return new Blob([ar], {type: mimeString});
  }
}
