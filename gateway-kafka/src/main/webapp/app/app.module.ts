import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { GatewayKafkaSharedModule } from 'app/shared/shared.module';
import { GatewayKafkaCoreModule } from 'app/core/core.module';
import { GatewayKafkaAppRoutingModule } from './app-routing.module';
import { GatewayKafkaHomeModule } from './home/home.module';
import { GatewayKafkaEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    GatewayKafkaSharedModule,
    GatewayKafkaCoreModule,
    GatewayKafkaHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    GatewayKafkaEntityModule,
    GatewayKafkaAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class GatewayKafkaAppModule {}
