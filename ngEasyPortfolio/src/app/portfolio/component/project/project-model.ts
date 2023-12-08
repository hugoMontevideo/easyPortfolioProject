import { DocumentProjectModel } from "./document-project-model";

export class ProjectModel {

    constructor(
        public id: number,
        public title: string,
        public description: string,
        public date: Date,
        public fileName: string,
        public documents: DocumentProjectModel [],
        public portfolioId: number        
     ){  }
     
}
