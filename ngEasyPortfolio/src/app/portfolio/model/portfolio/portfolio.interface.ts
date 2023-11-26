import { Education } from "../../component/education/education.interface";
import { Experience } from "../../component/experience/experience.interface";
import { Project } from "../../component/project/project.interface";
import { Skill } from "../../component/skill/skill.interface";


export interface Portfolio {
    
    id: number,
    title: string,
    description: string,
    name: string,
    firstname: string,
    email:string,
    city: string,
    projects: Project[],
    educations: Education[],
    experiences: Experience[],
    skills: Skill[]

    // u_id!: number;

}