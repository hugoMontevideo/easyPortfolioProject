import { Component, ElementRef, Input, Renderer2, SimpleChanges } from '@angular/core';
import { Skill } from '../../component/skill/skill.interface';
import { SkillService } from '../../services/skill.service';
import { environment } from 'src/environments/environment';
import  * as  Editor from 'ckeditor5-custom-build/build/ckeditor';

@Component({
  selector: 'app-board-skill',
  templateUrl: './board-skill.component.html',
  styleUrls: ['./board-skill.component.scss']
})
export class BoardSkillComponent {
  ENV_ICONS: string = `${environment.apiIcons}/`;
  @Input() skills:Skill[]= [];
  @Input() portfolioId?:number;
  softSkills!: Skill[];
  languages!: Skill[]; // display purpose array
  programsLanguages!: Skill[]; // display purpose array
  drivingLicenceCategories!: Skill[]; // display purpose array
  otherSkills!: Skill[];
  textSkill!: Skill|undefined;

  public Editor: any = Editor;
  textMessage!: string;
  editorData: string="";

  
  constructor( private skillService: SkillService,
    private renderer : Renderer2,
    private elRef : ElementRef
  ){}

  ngOnChanges(changes: SimpleChanges){  
    this.skillService.getSkills(this.portfolioId) // refresh projects []
          .subscribe({
              next: (data:Skill[]) => { 
                          this.softSkills = data.filter(item=>item.categorySkillId==1) ;  
                          this.languages = data.filter(item=>item.categorySkillId==2); 
                          this.programsLanguages = data.filter(item=>item.categorySkillId==3); 
                          this.drivingLicenceCategories = data.filter(item=>item.categorySkillId==4);
                          this.otherSkills = data.filter(item=>item.categorySkillId==5); 
                          this.textSkill = data.find(item=>item.categorySkillId==6);
                          this.textMessage=(this.textSkill) ?this.textSkill.description 
                                  :`Je me considère comme une personne très reactive, à l'écoute et capable de traduire les besoins du client ... `;                     
                          this.editorData = this.textMessage
                      },
              error: (err:Error)=>{console.error("**error Getting Skills**");} //TODO *******
            });         
  }

  ngAfterViewChecked(): void {
    const cssEditorBoardSkill = this.elRef.nativeElement.querySelector('.ck-content'); // ckeditor  main
    const cssEditorTopBoardSkill = this.elRef.nativeElement.querySelector('.ck-editor__top'); // ckeditor  top
    if(cssEditorBoardSkill){
      this.renderer.setStyle(cssEditorBoardSkill,'background-color', 'transparent');
      this.renderer.setStyle(cssEditorBoardSkill,'border', 'none');
      this.renderer.setStyle(cssEditorTopBoardSkill,'display', 'none');
    }
  }



}
