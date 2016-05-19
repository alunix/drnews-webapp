import {Component} from '@angular/core';
import {AppHeader} from './header/app-header'
import {ArticleView} from './articleview/articles-view'
import {Article} from './articleview/article'

@Component({
    selector: 'app',
    template: `
    <appheader></appheader>
    <articles-view></articles-view>
    `,
    directives: [AppHeader,ArticleView]
})
export class AppComponent {
    localType : any = {name: "Delvis"};
 }
