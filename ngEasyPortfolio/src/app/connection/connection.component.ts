import { Component } from '@angular/core';
import { User } from '../utils/models/user.interface';
import { HttpService } from '../services/http.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-connection',
  templateUrl: './connection.component.html',
  styleUrls: ['./connection.component.scss']
})
export class ConnectionComponent {
  table: string = "users";
  user: User = {
              id: 0,
              name: "",
              firstname: "",
              email: "",
              password: ""
            };

  constructor(private http: HttpService){}

  onSubmit= (form : NgForm)=>{
    this.http.add(this.table, form.value ).subscribe({
      next: (response)=>console.log(response),
      error: (err: Error)=> console.log("add user error ", err),
      complete: ()=>console.log("insert user ok")
    })
  }

}
