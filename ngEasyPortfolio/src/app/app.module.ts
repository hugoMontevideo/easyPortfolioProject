import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './core/user/user.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { SharedModule } from './shared/shared.module';
import { PortfolioModule } from './portfolio/portfolio.module';
import { JWTTokenService } from './services/JWTToken.service';
import { JwtInterceptorService } from './services/jwt-interceptor.service';
import { RegisterComponent } from './register/register.component';
import { TemplateDevComponent } from './a-online/template-dev/template-dev.component';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { TemplateDevSkillsComponent } from './a-online/template-dev/template-dev-skills/template-dev-skills.component';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AOnlineRouterModule } from './a-online/a-online.module';
import { OnlineRouterComponent } from './a-online/online-router/online-router.component';
import { TemplateClassicComponent } from "./a-online/template-classic/template-classic.component";
import { TemplateDevFooterComponent } from './a-online/template-dev/template-dev-footer/template-dev-footer.component';
import { LoginResetComponent } from './login-reset/login-reset.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    UserComponent,
    LoginComponent,
    LoginResetComponent,
    RegisterComponent,
    TemplateDevComponent,
    TemplateDevSkillsComponent,
    TemplateDevFooterComponent,
    OnlineRouterComponent  
  ], 
  imports: [
    BrowserModule,
    NgbModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    SharedModule,
    PortfolioModule,
    CKEditorModule,
    BrowserAnimationsModule,
    AOnlineRouterModule,
    ToastrModule.forRoot({
        timeOut: 1500,
        iconClasses: {
            info: "",
            warning: "",
        },
    }),
    TemplateClassicComponent
],
  providers: [
    JWTTokenService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
