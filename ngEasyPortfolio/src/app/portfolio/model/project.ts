export class Project {
    id!: number;
    titre!: string;
    description!: string;
    has_skills!: string;
    date!: string;
    p_id!: number;

    constructor(){
        this.id = 0;
        this.titre = '';
        this.description = '';
        this.has_skills = '';
        this.date = '';
        this.p_id = 0;
    }
}