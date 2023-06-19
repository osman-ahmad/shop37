import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UploadResult } from '../model/upload-result';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadService {
  imageData = "";

  constructor(private httpClient: HttpClient) { }

  // the variable names "" should match with the server side variable names
  upload(form: any, image: Blob) {
    const formData = new FormData();
    formData.set("title", form['title']);
    formData.set("complain", form['complain']);
    formData.set("file", image);

    return firstValueFrom(this.httpClient.post<UploadResult>("/upload", formData));
    }
}
