import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FormControl } from '@angular/forms';
import { startWith, map } from 'rxjs/operators';
// interface PostalCode {
//   adminCode2: string;
//   adminCode1: string;
//   adminName2: string;
//   lng: number;
//   countryCode: string;
//   postalCode: string;
//   adminName1: string;
//   'ISO3166-2': string;
//   placeName: string;
//   lat: number;
// }

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  myControl = new FormControl();
  options: string[] = [];
  p_card: any[] = [];
  filteredOptions: Observable<string[]>;
  searched: boolean = false;
  constructor(private http: HttpClient) { 
      this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value))
    );

  }
  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }

  title = 'ebay-hw3';
  showError: boolean = false; 
  myText: string = '';
  keyword: string = '';
  category: string = '';
  isnew: boolean = false;
  isused: boolean = false;
  isunspecified: boolean = false;
  isfree: boolean = false;
  islocal: boolean = false;
  distance: string = '10';
  iscurrent: string = '';
  iszip: string = '';
  selected: string = 'results';
  wishlist:boolean = false;
  wishdata: any[] = [];
  clicked: boolean = false;
  ziperror: boolean = false;
  onTextChange(){
    //console.log(this.myText);
    const requestData = this.myText;
    const url = `http://hw5712.wn.r.appspot.com/data?param=${requestData}`;
    this.http.get(url)
    .subscribe(response => {
      //console.log(response);
      this.options = [];
      const o = Object.values(response);
      for(const p in o){
        //console.log(o[p]['postalCode']);
        this.options.push(o[p]['postalCode']);
      }
    });

  }
  onClearClick(){
    this.keyword = '';
    this.category = '';
    this.isnew = false;
    this.isused = false;
    this.isunspecified = false;
    this.isfree = false;
    this.islocal = false;
    this.distance = '10';
    this.iscurrent = '';
    this.iszip = '';
    this.myText = '';
    this.showError = false;
    this.ziperror = false;
    this.searched = false;
    this.wishdata = [];
    this.p_card = [];
    this.clicked = false;
  }
  onSearchClick(){
    
    if (this.keyword.trim() === '') {
      this.showError = true;
    } else {
      this.showError = false;
    }
    
    if(this.iszip=="option2" && this.myText==''){
      this.ziperror = true;
    }
    else{
      this.ziperror = false;
    }
    if(this.showError==true||this.ziperror==true){
      return;
    }
    const keyword = this.keyword;
    const category = this.category;
    const isnew = this.isnew;
    const isused = this.isused;
    const isunspecified = this.isunspecified;
    const isfree = this.isfree;
    const islocal = this.islocal;
    //const iscurrent = this.iscurrent;
    const distance = this.distance;
    const iszip = this.iszip;
    const myText = this.myText;
    this.clicked = true;
    var location = '';
    var url='';
    if(iszip == 'option2' && this.myText != ''){
      url = `http://localhost:3000/search?keyword=${keyword}&category=${category}&isnew=${isnew}&isused=${isused}&isunspecified=${isunspecified}&isfree=${isfree}&islocal=${islocal}&location=${myText}&distance=${distance}`;
      this.http.get(url)
      .subscribe(response => {
        this.searched = true;
        this.clicked = false;
        if (Object.values(response)[0][0]['searchResult'][0]['@count']!='0'){
          this.p_card = (Object.values(response)[0][0]['searchResult'][0]['item']);
          console.log(this.p_card);
        }
        else{
          this.p_card = [];
        }
        //console.log(Object.values(response));
      });
    }
    else{
      const url2 = `http://ipinfo.io/json?token=5d2fd6027a891c`;
      this.http.get(url2)
      .subscribe(response => {
        location = Object.values(response)[7];
        url = `http://localhost:3000/search?keyword=${keyword}&category=${category}&isnew=${isnew}&isused=${isused}&isunspecified=${isunspecified}&isfree=${isfree}&islocal=${islocal}&location=${location}&distance=${distance}`;
        this.http.get(url)
        .subscribe(response => {
          this.searched = true;
          if (Object.values(response)[0][0]['searchResult'][0]['@count']!='0'){
            this.p_card = (Object.values(response)[0][0]['searchResult'][0]['item']);
            console.log(this.p_card);
          }
          else{
            this.p_card = [];
          }
        });
      });
    }
  }
  showing(c: string){
    
    this.selected = c;
    if(this.selected=='wishlist'){
      this.wishlist = true;
      const url = `http://hw5712.wn.r.appspot.com/getwishlist`;
      this.http.get(url)
      .subscribe(response => {
        this.wishdata = Object.values(response);
      });
    }
    else{
      this.wishlist = false;
    }
  }

}