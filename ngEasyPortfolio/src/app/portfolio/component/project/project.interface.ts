import { DocumentProjectModel } from "./document-project-model"

export interface Project {
    id: number,
    title: string,
    description: string,
    date: Date,
    fileName: string,
    documents: DocumentProjectModel[],
    portfolioId: number
}