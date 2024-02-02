
import { Portfolio } from "src/app/portfolio/model/portfolio/portfolio.interface";
import { Role } from "./role.interface";

export interface User{
    id: number;
    name: string;
    firstname: string;
    email: string;
    password: string;
    dateInscription: Date;
    dateConnect: Date;
    profileImgPath: string;
    portfolios?: Portfolio[],
    roles: Role[]
    // token!: string;
    // confirmed!: number;
}