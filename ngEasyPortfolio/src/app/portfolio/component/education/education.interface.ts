export interface Education {
    id: number,
    training: string,
    school: string,
    degree: string,
    startDate: Date,
    endDate: Date,
    description: string,
    portfolioId?: number
}