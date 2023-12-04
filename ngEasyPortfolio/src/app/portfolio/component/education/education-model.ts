export class EducationModel {

    constructor(
        public id: number,
        public training: string,
        public school: string,
        public degree: string,
        public startDate: Date,
        public endDate: Date,
        public description: string,
        public portfolioId: number
     ){  }
     
}