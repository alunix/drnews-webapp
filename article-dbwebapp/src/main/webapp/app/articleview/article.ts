import {Input, Component} from '@angular/core';

@Component({
    selector: 'news-article',
    template: `
    <div class="news-article">
    <div class="article-header"> 
        <div class="article-symb"><span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span></div>
        <div class="article-title">{{article["title"]}}</div>
    </div>
    <div>
        <div *ngFor="let prop of textProperties">
            <span class="article-prop">{{prop}}:</span><span>{{article[prop]}}</span>
        </div>
        <div>
            <span class="article-prop">articleUrl:</span><a href="{{article.articleUrl}}">go to article</a>
        </div>
        <div>
            <span class="article-prop">imgUrl:</span><a href="{{article.imgUrl}}">view image</a><br>
            <img src="{{article.imgUrl}}"/>
        </div>
        <span class="article-prop"><a (click)="toggleContent()">Expand Article Content</a></span>
        <div *ngIf="showContent">{{article.content}}</div>
    </div>
    </div>
    `,
    styles: [
        `
        .news-article {
            border-width: 1px;
            border-style: solid;
            border-radius: 4px;
            margin: 4px;
        }
        .article-header {
            background-color: red;
            margin: 0;
            padding: 0;
        }
        .article-symb {
            font-size: 18px;
            text-align: center;
            display: table-cell;
            padding: 4px;
            color: white;
        }
        .article-symb span {
            top: 0;
        }
        .article-title {
            font-size: 24px;
            text-align: center;
            display: table-cell;
            width: 100%;
        }
        .article-prop {
            color: grey;
            padding: 4px;
        }
        `
    ]
})
export class Article {
    @Input() article: any;
    //all properties besides title
    textProperties: String[] = ["newsOutlet","section","author", 
                                "place","datePublished","timeExtracted"];
    urlProperties: String[] = ["articleUrl", "imgUrl"]                            
    showContent: boolean = false;                     
           
    constructor(){}
    
    toggleContent() {
        this.showContent = this.showContent ? false : true;
    }
}
