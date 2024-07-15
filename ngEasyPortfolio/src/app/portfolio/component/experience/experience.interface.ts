export interface Experience {
    id: number,
    title: string,
    company: string,
    description: string,
    city: string,
    startDate: Date,
    endDate:Date,
    portfolioId?:number
}