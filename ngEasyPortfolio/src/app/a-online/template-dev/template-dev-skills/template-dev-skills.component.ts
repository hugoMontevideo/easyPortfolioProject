import { AfterViewChecked, Component, ElementRef, Input, Renderer2 } from '@angular/core';
import { Skill } from 'src/app/portfolio/component/skill/skill.interface';
import { environment } from 'src/environments/environment';
import  * as  Editor from 'ckeditor5-custom-build/build/ckeditor';

@Component({
  selector: 'app-template-dev-skills',
  templateUrl: './template-dev-skills.component.html',
  styleUrls: ['./template-dev-skills.component.scss']
})
export class TemplateDevSkillsComponent implements AfterViewChecked  {
  
  @Input() skills:Skill[]= [];
  @Input() portfolioId?:number;

  ENV_PICT:string = `${environment.apiImg}/pictures/`;

  softSkills!: Skill[];
  languages!: Skill[]; // display purpose array
  programsLanguages!: Skill[]; // display purpose array
  drivingLicenceCategories!: Skill[]; // display purpose array
  otherSkills!: Skill[];
  textSkill!: Skill|undefined;

  public Editor1: any = Editor;
  textMessage!: string;
  editorDataSkills: string="";

  constructor( 
    private renderer : Renderer2,
    private elRef1 : ElementRef
  ){}

  ngAfterViewChecked(): void {
    this.softSkills = this.skills.filter(item=>item.categorySkillId==1); 
    this.languages = this.skills.filter(item=>item.categorySkillId==2); 
    this.programsLanguages = this.skills.filter(item=>item.categorySkillId==3); 
    this.drivingLicenceCategories = this.skills.filter(item=>item.categorySkillId==4);
    this.otherSkills = this.skills.filter(item=>item.categorySkillId==5);  
    this.textSkill = this.skills.find(item=>item.categorySkillId==6);
    this.textMessage=(this.textSkill) ?this.textSkill.description 
    :`Je me considère comme une personne très reactive, à l'écoute et capable de traduire les besoins du client ... `;                     
    this.editorDataSkills = this.textMessage

    const cssEditorSkill = this.elRef1.nativeElement.querySelector('#editor1 .ck-content'); // ckeditor  main
    const cssEditorTop = this.elRef1.nativeElement.querySelector('#editor1 .ck-editor__top'); // ckeditor  top
    const cssEditorOther= this.elRef1.nativeElement.querySelector('#editor1 .ck-editor__editable_inline');
    
    this.renderer.setStyle(cssEditorSkill,'background-color', 'transparent');
    this.renderer.setStyle(cssEditorSkill,'border', 'none');
    this.renderer.setStyle(cssEditorOther,'min-height', '2rem')
    this.renderer.setStyle(cssEditorTop,'display', 'none');
  }

  

}
