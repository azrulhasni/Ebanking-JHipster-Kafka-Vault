import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { GatewayTempSharedModule } from 'app/shared/shared.module';
import { GatewayTempCoreModule } from 'app/core/core.module';
import { GatewayTempAppRoutingModule } from './app-routing.module';
import { GatewayTempHomeModule } from './home/home.module';
import { GatewayTempEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    GatewayTempSharedModule,
    GatewayTempCoreModule,
    GatewayTempHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    GatewayTempEntityModule,
    GatewayTempAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class GatewayTempAppModule {}
