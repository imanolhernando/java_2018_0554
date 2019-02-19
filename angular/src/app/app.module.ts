import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

//Componentes
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PruebaComponent } from './prueba/prueba.component';
import { JuegoClickComponent } from './juego-click/juego-click.component';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './components/about/about.component';
import { Error404Component } from './components/error404/error404.component';
import { NombreComponent } from './components/nombre/nombre.component';
import { PaginaDirectivaComponent } from './components/pagina-directiva/pagina-directiva.component';
import { FlujoComponent } from './components/flujo/flujo.component';
import { PaginaPipeComponent } from './components/pagina-pipe/pagina-pipe.component';

//Directivas
import { Directiva1Directive } from './directives/directiva1.directive';
import { CountdownDirective } from './directives/countdown.directive';

//Pipes
import { MonedaPipe } from './pipes/moneda.pipe';
import { TrimPipe } from './pipes/trim.pipe';
import { ArrayComponent } from './components/array/array.component';
import { FiltroOfertaPipe } from './pipes/filtro-oferta.pipe';
import { PaginaComparadorComponent } from './components/pagina-comparador/pagina-comparador.component';
import { FrutaComponent } from './components/fruta/fruta.component';
import { TraductorComponent } from './components/traductor/traductor.component';
import { CarritoComponent } from './components/carrito/carrito.component';

@NgModule({
  declarations: [
    AppComponent,
    PruebaComponent,
    JuegoClickComponent,
    HomeComponent,
    AboutComponent,
    Error404Component,
    NombreComponent,
    PaginaDirectivaComponent,
    Directiva1Directive,
    CountdownDirective,
    FlujoComponent,
    PaginaPipeComponent,
    MonedaPipe,
    TrimPipe,
    ArrayComponent,
    FiltroOfertaPipe,
    PaginaComparadorComponent,
    FrutaComponent,
    TraductorComponent,
    CarritoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule //Para poder usar doble binding
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
