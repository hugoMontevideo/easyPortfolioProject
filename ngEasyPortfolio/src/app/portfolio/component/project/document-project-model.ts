export class DocumentProjectModel {

    constructor(
        public id: number,
        public title: string,
        public mime: string,
        public filename: string,
        public projectId: number        
     ){  }
     
}
