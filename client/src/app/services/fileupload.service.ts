import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs/internal/lastValueFrom';
import { UploadResult } from '../model/upload-result';

@Injectable({
  providedIn: 'root'
})
export class FileuploadService {

  constructor(private httpClient: HttpClient) { }

  getImage(postId: string){
    //by right  must use httpparams
    return lastValueFrom(this.httpClient.get<UploadResult>('/get-image/' + postId));
  }
}
