import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UploadService } from '../services/upload.service';

@Component({
  selector: 'app-insert',
  templateUrl: './insert.component.html',
  styleUrls: ['./insert.component.css']
})
export class InsertComponent implements OnInit, OnDestroy{

  // width = 400;
  // height = 400;
  
  pics: string[] = []; //for multiple images
  pic: string = ""; //for single image
  constructor(private router: Router, private uploadSvc: UploadService) { }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
  }

  handleFileInput(event: any) {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = (e) => {
      const imageData = e.target?.result as string;
      this.uploadSvc.imageData = imageData;
      this.pics.push(this.uploadSvc.imageData);
      this.pic = this.uploadSvc.imageData;
    };

    reader.readAsDataURL(file);
  }

}
