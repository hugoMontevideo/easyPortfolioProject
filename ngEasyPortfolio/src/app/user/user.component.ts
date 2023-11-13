import { Component, OnInit } from '@angular/core';
import { HttpService } from '../services/http.service';
import { ActivatedRoute } from '@angular/router';
import { User } from '../utils/models/user.interface';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  table: string = "users";
  users: User[] = [];
  currentUser!: User;
  currentIndex: number = 0;

  constructor ( private httpService: HttpService,
                private route: ActivatedRoute ) {}

  ngOnInit(): void {
    let currentId = this.route.snapshot.paramMap.get('id');
    console.log(currentId);

    (currentId != null) ? this.currentUser.id = parseInt(currentId) : this.currentUser.id = 0;

    if(this.currentUser.id == 0){
      this.getUsers(this.table, this.currentUser.id);
    }else{
      this.getUserById(this.table, this.currentUser.id)
    }
  

  }

  getUsers(table: string, id:number ){
    this.httpService.getData(table, id).subscribe({
      next: (response: User[]) => this.users=response,
      error: (err: Error)=> console.log("Error"),
      complete: ()=> console.log("completed")
    })
  }

  getUserById(table:string, id:number){
    this.httpService.getUserById(table, id).subscribe({
      next:(response:User)=> this.currentUser = response,
      error: (err:Error)=>console.log("Error userbyId"),
      complete: ()=> console.log(this.currentUser.name)
      
    })
  }

  onEditClick(event: any, index:number) {
    // console.log (this.projects[index].dateStart.slice(0,10));  **  formater date pour affichage html
    // console.log(index);
    
    // this.currentUser.id= this.users[index].id;
    // this.editProject.name= this.projects[index].name;
    // this.editProject.dateStart= this.projects[index].dateStart.slice(0,10);
    // this.editProject.teamSize= this.projects[index].teamSize;

    // this.getUsers(this.table, this.currentUser.id);

 
    // this.currentIndex=index;
  }

  onUpdateClick(){
    // this.projectService.updateProject(this.editProject, this.table).subscribe({
    //   next: (response: Project) => {
    //                     if(response.name != 'Erreur'){
    //                       this.projects[this.editIndex]=response;
    //                       this.getProjects;
    //                     }else{console.log('erreur la modif n\'a pas été realisée');}
    //                 },
    //   error: (err: Error )=> {console.log('Error '+ err)},
    //   complete: ()=> { 
    //               this.editProject.id=0;
    //               this.editProject.dateStart='';
    //               this.editProject.name='';
    //               this.editProject.teamSize=0;
    //           }
    // });
  }

  onDeleteClick(event:any , index: number){
    // this.deleteIndex = index;
    // this.deleteProject.id= this.projects[index].id;
    // this.deleteProject.name= this.projects[index].name;
    // this.deleteProject.dateStart= this.projects[index].dateStart.slice(0,10);
    // this.deleteProject.teamSize= this.projects[index].teamSize;
  }

  

}
