export interface Project {
    id: number,
    title: string,
    description: string,
    date: Date,
    fileName: string,
    file: File | null,
    portfolioId: number
}