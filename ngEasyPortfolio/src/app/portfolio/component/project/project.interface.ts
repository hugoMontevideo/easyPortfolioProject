import { DocumentProject } from "./document-project.interface"

export interface Project {
    id: number,
    title: string,
    description: string,
    languages: string,
    date: Date,
    fileName: string,
    documents: DocumentProject[],
    portfolioId?: number
}