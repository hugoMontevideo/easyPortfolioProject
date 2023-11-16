import { Skill } from "../../component/skill/skill.interface";

export interface Portfolio {
    
    id: number,
    title: string,
    description: string,
    name: string,
    firstname: string,
    email:string,
    city: string,
    skills: Skill[]
    // u_id!: number;

}