import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { Skill } from './skill.interface';
import { SkillService } from '../../services/skill.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CategorySkill } from './category-skill.inteface';

@Component({
  selector: 'app-skill',
  templateUrl: './skill.component.html',
  styleUrls: ['./skill.component.scss']
})
export class SkillComponent implements OnInit {
  @Input() skills:Skill[] = [];
  @Input() portfolioId!: number;
  @Output() skillsChanged = new EventEmitter<Skill[]>();
  legend: string = "";
  inputError?: string;
  categorySkills: CategorySkill[] | any;

  newSkill: Skill = {
    id:-1,
    title: "",
    description: "",
    categorySkillId: -1,
    portfolioId: this.portfolioId
  };
  isSkillFormShowing: boolean = false; // display or hide form

  constructor( private skillService: SkillService ){}

  ngOnInit(): void {
    this.getCategorySkills();
  }

  ngOnChanges( change: SimpleChanges){
    this.newSkill.portfolioId = this.portfolioId;    
  }

  public onCloseModalForm = () => {
    this.isSkillFormShowing = false;
    this.newSkill = this.skillService.resetNewSkill(this.newSkill.portfolioId);
  }

  public onEditSkill = (index: number) => {
    this.legend = "Modifier une compétence"
    this.isSkillFormShowing = true;
    this.newSkill = this.skills[index];
    this.newSkill.portfolioId = this.portfolioId; 
  }

  public onAddSkill = () => {
    this.legend = "Ajouter une compétence"
    this.isSkillFormShowing = true;
    this.skillService.add(this.newSkill)
    .subscribe({
      next:(data)=>{
        this.newSkill.id = data.id; 
        this.newSkill.title = data.title; 
        this.skillService.getSkills(this.newSkill.portfolioId) // refresh projects[]
          .subscribe({
              next: (data:Skill[]) => { 
                        this.skills = data ;
                        this.skillsChanged.emit(this.skills);
                      },
              error: (err:Error)=>{console.error("**error Getting educations**");} //TODO *******
            });
      },
      error:(_error)=>{
        console.log("**error Adding Education**");
        if(_error instanceof HttpErrorResponse ) {
          this.inputError = _error.error.training;
        } 
      }
    });
  }

  public onSubmitSkill = ()=>{
    // hide the form
    this.newSkill.portfolioId = this.portfolioId; 
    this.skillService.saveSkill(this.newSkill)
    .subscribe({
      next:(data)=>{
          this.isSkillFormShowing = false;  // hide the form
          this.newSkill = this.skillService.resetNewSkill(this.portfolioId);
          this.skillService.getSkills(this.newSkill.portfolioId) // refresh projects[]
            .subscribe({
                next: (data:Skill[]) => { 
                        this.skills = data;
                        this.skillsChanged.emit(this.skills);
                      },
                error: (_error:Error)=>{console.error("**error Getting skills[]**");} //TODO *******
              });
        },
      error:(_error)=>{
        console.error("**error updating Skill**");
        if(_error instanceof HttpErrorResponse ) {
          this.inputError = _error.error.title;
        } 
      }
    });
  }

  onDeleteSkill = (skillId : number, index: number):void => {
    this.isSkillFormShowing = false;
    this.skillService.deleteSkill(skillId)
    .subscribe({
      next:( )=> {
       this.skillService.getSkills(this.newSkill.portfolioId) // refresh projects[]
         .subscribe({
             next: (data:Skill[]) => { 
                     this.skills = data;
                     this.skillsChanged.emit(this.skills); 
                   },
             error: (_error)=>{
                 console.log("**error Getting Projects**"); 
               }
           });
       },
      error:(_error:Error)=>{ console.log("Error while deleting education.");
         }
     }) 
  }

  getCategorySkills = () => {
    this.skillService.getCategoriesSkills()
    .subscribe({
      next:( data : CategorySkill[] )=> {
        // getting skill categories, i can use it in the form
          this.categorySkills = data ;          
        },
      error:(_error:Error)=>{ console.log("Error while getting skill categories .");
         }
    }) 
  }


}
