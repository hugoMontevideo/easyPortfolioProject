import { AfterViewChecked, Component, EventEmitter, Input,Renderer2, ElementRef, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { Skill } from './skill.interface';
import { SkillService } from '../../services/skill.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CategorySkill } from './category-skill.inteface';
import * as Editor from 'ckeditor5-custom-build/build/ckeditor';

@Component({
  selector: 'app-skill',
  templateUrl: './skill.component.html',
  styleUrls: ['./skill.component.scss']
})
export class SkillComponent implements OnInit,AfterViewChecked {
  @Input() skills:Skill[] = [];
  @Input() portfolioId!: number;
  @Output() skillsChanged = new EventEmitter<Skill[]>();
  legend: string = "";
  inputError?: string;
  categorySkills!: CategorySkill[];
  catSkillsWithout6!: CategorySkill[] ;

  skillsWithout6: Skill[] = [] ;
  skill6!: Skill|undefined ; // the text that describes the user's skills
  textMsg!:string;

  public Editor: any = Editor;  // ckeditor
  editorData: string = "";

  newSkill: Skill = {
    id:-1,
    title: "",
    description: "",
    categorySkillId: -1,
    portfolioId: this.portfolioId
  };
  
  isSkillFormShowing: boolean = false; // display or hide form

  constructor( private skillService: SkillService,
                private renderer : Renderer2,
                private elRef : ElementRef
   ){}

  ngOnInit(): void {
    this.getCategorySkills();
  }

  ngOnChanges( change: SimpleChanges){
    this.newSkill.portfolioId = this.portfolioId;
    this.getSkill6();
    this.getSkillsWithout6();    
    this.editorData = this.textMsg;
  }

  ngAfterViewChecked(): void {
    const cssEditorSkill = this.elRef.nativeElement.querySelector('#editorSkill .ck-content'); // ckeditor  main
      
    this.renderer.setStyle(cssEditorSkill,'background-color', 'rgb(21%,21%,34%)');
    this.renderer.setStyle(cssEditorSkill,'border-radius', '0 0 5px 5px');
    this.renderer.setStyle(cssEditorSkill,'color', 'rgb(100%,100%,100%)');
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
    this.newSkill.title="Titre";
    this.newSkill.categorySkillId=5;
    this.skillService.add(this.newSkill)
    .subscribe({
      next:(data)=>{
        this.newSkill.id = data.id; 
        this.skillService.getSkills(this.newSkill.portfolioId) // refresh projects[]
          .subscribe({
              next: (data:Skill[]) => { 
                        this.skills = data ;
                        this.skillsChanged.emit(this.skills);
                      },
              error: (err:Error)=>{console.error("**error Getting skills**");} //TODO *******
            });
      },
      error:(_error)=>{
        // todo manage error  -- onSubmitSkillTxt too
        console.log("**error Adding Skill**");
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
          this.getCategorySkillsWithout6();                    
        },
      error:(_error:Error)=>{ console.log("Error while getting skill categories .");
         }
    }) 
  }

  getCategorySkillsWithout6 = () => {
    this.catSkillsWithout6 = this.categorySkills.filter(category=>category.id!==6); 
  } 

  getSkillsWithout6 = () => {
    this.skillsWithout6 = this.skills.filter(skill=>skill.categorySkillId!==6);
  } 

  getSkill6 = () => {
    this.skill6 = this.skills.find(skill=>skill.categorySkillId===6);
    this.textMsg = (this.skill6) ?this.skill6.description :`Je me considère comme une personne très réactive, à l'écoute et
                            capable de traduire les besoins du client ... `;
  }

  public onSubmitSkillTxt = ()=>{
    if( this.skill6 == undefined){  // add an empty skill
      this.skill6={
        id: 1,
        title: "Texte de la section Compétences",
        description: this.editorData,
        categorySkillId:6,
        portfolioId: this.portfolioId
      };
      this.skillService.add(this.skill6) 
      .subscribe({
        next:(data)=>{
         // console.log(data);
        },
        error:(_error)=>{
          // todo manage error  onAddSkill too
          console.log("**error Adding Education**");
          if(_error instanceof HttpErrorResponse ) {
            this.inputError = _error.error.training;
          } 
        }
      });
    }
    this.skill6.description=this.editorData;
    this.skill6.portfolioId=this.portfolioId  
    this.skillService.saveSkill(this.skill6)
    .subscribe({
      next:(data)=>{
        this.skillService.getSkills(this.skill6?.portfolioId) // refresh projects[]
            .subscribe({
              next: (data:Skill[]) => { 
                      this.skills = data;
                      console.log(this.skills);
                      this.skillsChanged.emit(this.skills);
                    },
              error: (_error:Error)=>{console.error("**error Getting skills[]**");} //TODO *******
            });                  
        },
      error:(_error : any)=>{
        console.error("**error updating skill6.description**");
        if(_error instanceof HttpErrorResponse ) {
          this.inputError = _error.error.title;
        } 
      }
    }); 
  }


}
