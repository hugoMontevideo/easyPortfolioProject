import { EducationModel } from "../../component/education/education-model";
import { ExperienceModel } from "../../component/experience/experience-model";
import { ProjectModel } from "../../component/project/project-model";
import { SkillModel } from "../../component/skill/skill-model";

export interface Portfolio {  
    id: number,
    title: string,
    description: string,
    name: string,
    firstname: string,
    email:string,
    city: string,
    projects: ProjectModel[],
    educations: EducationModel[],
    experiences: ExperienceModel[],
    skills: SkillModel[]

    // u_id!: number;

}