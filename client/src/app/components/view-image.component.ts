import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FileuploadService } from '../services/fileupload.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-view-image',
  templateUrl: './view-image.component.html',
  styleUrls: ['./view-image.component.css']
})
export class ViewImageComponent implements OnInit, OnDestroy{

    postId= "";
    param$! : Subscription;
    imageData: any;


  constructor(private actRoute: ActivatedRoute, 
    private fileUpSvc: FileuploadService){

} 

ngOnInit(): void {
  this.actRoute.params.subscribe(
    async (params)=> {
      this.postId = params['postId'];
      let r = await this.fileUpSvc.getImage(this.postId);
      console.log(r);
      this.imageData = r.image;
      
    }
  );
}

ngOnDestroy(): void {
    this.param$.unsubscribe();
}

}
