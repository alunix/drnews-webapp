import {Component} from '@angular/core';

@Component({
    selector: 'appheader',
    template: `
    <div id="title" >{{title}}</div>
    `,
    styles: [
        `
        #title {
            color: red;
            text-align: center;
            border-style: solid;
            padding: 5px;
            font-size: 30px;
        }
        `
    ]
})
export class AppHeader { 
    title = "Database Article Viewer";
    
    constructor(){
        console.log("Appheader Constructor");
    }
}