import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filtroOferta'
})
export class FiltroOfertaPipe implements PipeTransform {

  /**
   * Filtro para frutas
   * @param frutas [] array con todas las frutas a filtrar 
   * @param isOferta boolean si true mostrar solo frutas en oferta
   * @param searchText string cadena texto a buscar en el nombre de las frutas, 
   *                   caseInsesitive y cualquier coincidencia
   */
  transform( frutas: any[] , isOferta: boolean, searchText?: string): any[] {
    
    console.trace('FiltroOfertaPipe isOferta= %s searchText=%s', isOferta, searchText);

    // conseguimos el mismo array pero sin que apunte a la misma posicion de memoria
    // no usar aResul = frutas;
    let aResul = frutas.map(f=>f);

    // 1º filtrar por Oferta
    if ( isOferta ){   
      aResul = aResul.filter( f => f.oferta );
    }

    //2º filtrar por searchText, si esta definido y diferente vacio
    if ( searchText && searchText !== '' ){
      aResul = aResul.filter( f => f.nombre.includes(searchText) );
    }

    return aResul;

  }

}
