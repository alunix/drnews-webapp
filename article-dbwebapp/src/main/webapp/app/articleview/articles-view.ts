import {Input, Component} from '@angular/core';
import {Article} from './article';

@Component({
    selector: 'articles-view',
    template: `
    <div class="article-container">
        <news-article *ngFor="let data of datalist" [article]="data">
        </news-article>
    </div>
    `,
    styles: [
      `
      .article-container {
          margin: 0 auto;
          border-style: solid;
          border-width: 1px;
          border-color: green;
      }
      `  
    ],
    directives: [Article]
})
export class ArticleView {
    datalist: any[] = [];
    obj = {title: "Violan y despojan de RD$10 mil a una mujer en Constanza",
           author: "Redacción", section: "Panorama", newsOutlet: "ElCaribe",
           articleUrl: "http://www.elcaribe.com.do/2016/05/07/violan-despojan-10-mil-una-mujer-constanza",
           imgUrl: "http://multimedia.mmc.com.do/multimedia/multimedia/2014/09/30/87623e5af7310629014be5a5daf96b7e_300x226.jpg",
           datePublished: "2016-05-07", timeExtracted: "2016-05-08",
           content: "La Policía informó este sábado que apresó a un nacional haitiano acusado de violar sexualmente y despojar de sus pertenencias a una mujer, tras interceptarla en un camino de la comunidad rural Los Bermúdez del municipio de Constanza, provincia La Vega. La institución identificó al detenido como Sandy Rafael (a) Chiquito, de 27 años, mientras que su víctima, de 37, no fue identificada.  El informe indica que la mujer sufrió laceraciones en los glúteos, muslos y otras áreas a causa de la violación, de acuerdo al diagnóstico del médico legista actuante en el caso.  De acuerdo con  la Policía, la mujer declaró que mientras se dirigía a las 8:00 de la mañana hacia una finca donde trabaja su esposo, Sandy Rafael la interceptó violentamente en el paraje Los Bermúdez, donde abusó de ella y la despojó de RD$10,000.00 en efectivo y un celular, que luego fue ocupado por la Policía en poder del extranjero. Luego que la mujer pidiera auxilio, el haitiano fue golpeado por unos trabajadores de una finca de las proximidades, causándole una herida en el cráneo curable entre cuatro y cinco días, salvo complicaciones, según el diagnóstico del médico legista que le atendió."}
    constructor(){
        this.datalist.push(this.obj);
        this.datalist.push(this.obj);
    }
}
