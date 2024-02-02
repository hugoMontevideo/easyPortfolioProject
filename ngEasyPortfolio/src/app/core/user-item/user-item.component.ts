import { Component, Input, SimpleChanges } from '@angular/core';
import { User } from '../user/user.interface';
import { UserService } from 'src/app/services/user.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-user-item',
  templateUrl: './user-item.component.html',
  styleUrls: ['./user-item.component.scss']
})
export class UserItemComponent {
  @Input() user: User = {
                        id: -1,
                        name: "",
                        firstname: "",
                        email: "",
                        password: "",
                        dateInscription: new Date("1970-01-01"),
                        dateConnect: new Date("1970-01-01"), 
                        profileImgPath:"",
                        portfolios: [],
                        roles: []
                        // role!: string;
                        // token!: string;
                        // confirmed!: number;
                      }
  legend: string = "";
  isUserFormShowing=false;
  inputError?: string;
  isPictureFormShowing=false;
  selectedFile?: File ;

  constructor ( protected userService: UserService ) {}

  public onModifyPicture = () => {
    this.legend = "Modifier la photo de profil"
    this.isPictureFormShowing = true;
    this.isUserFormShowing = false;
  }

  public onEditUser = () => {
    this.legend = "Modifier mon profil"
    this.isUserFormShowing = true;
    this.isPictureFormShowing = false;
  }

  public onCloseModalForm = () => {
    this.isUserFormShowing = false;
    this.isPictureFormShowing = false;
  }

  public onSubmitUser = ()=>{
    this.userService.saveUser(this.user)
    .subscribe({
      next:(data:User)=>{        
              this.isUserFormShowing = false;  // hide the form
              this.user = data;
          },
      error:(_error : any)=>{
        console.error("**error updating User**");
        if(_error instanceof HttpErrorResponse ) {
          this.inputError = _error.error.title;
        } 
      }
    });
  }

  public onSubmitPicture = ()=>{
    this.userService.savePicture(this.user.id, this.selectedFile!)
    .subscribe({
      next:(data:User)=>{
              this.isPictureFormShowing = false;  // hide the form
              this.user = data;         
          },
      error:(_error : any)=>{
        console.error("**error updating User profile picture**");
        if(_error instanceof HttpErrorResponse ) {
          this.inputError = _error.error.title;
        } 
      }
    });
  }
  
  onSelectedFile = (event:any):void => {
    this.selectedFile = event.target.files[0];    
  }




}
