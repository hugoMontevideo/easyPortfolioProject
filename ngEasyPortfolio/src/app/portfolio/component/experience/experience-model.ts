export class ExperienceModel {

    constructor(
        public id: number,
        public title: string,
        public company: string,
        public description: string,
        public startDate: Date,
        public endDate:Date,
        public portfolioId: number
     ){  }
     
}