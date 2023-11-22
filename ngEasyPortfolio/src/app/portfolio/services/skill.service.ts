import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { Skill } from "../component/skill/skill.interface";

@Injectable()

export class SkillService {

    ENV_DEV:string = environment.apiUrl;
    table:string = "skills";

    constructor ( private http: HttpClient ) {};

    add( skillEdit: Skill ): Observable<any> {        
        return this.http.post(`${this.ENV_DEV}/${this.table}`, skillEdit ,{responseType: "json"} );
    }









}