import { Component, OnInit,Input,Output ,OnChanges,SimpleChanges,EventEmitter} from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent {
  @Input() cardData: any[] = []; 
  public selectedButton: string = 'product';
  totalPages: number = 10;
  currentPage: number = 1;
  currentId: string = '';
  currentShipping: any[] = [];
  currentSeller: any[] = [];
  currentName: string = '';
  currentNo: number = -1;
  issearchTableVisible: boolean = true;
  isdetailVisible: boolean = false;
  showcarousel:boolean = false;
  isImageVisible: boolean = false;
  currentData: any[] = [];
  isShippingVisible: boolean = false;
  finisedSearch: boolean = false;
  isSellerVisible: boolean = false;
  isSimilarVisible: boolean = false;
  showMoreSimilar: boolean = false;
  rowClickStatus: { [key: number]: boolean } = {};
  facebookhref: string = '';
  imageData: any[] = [];
  productSource: { [key: string]: any } = {};
  wishes: any[] = [];
  wishlist: any[] = [];
  similarData: any[] = [];
  csimilarData: any[] = [];
  selectedSortBy: string = 'default';
  selectedSortOrder: string = 'ascending';
  dataSource = new MatTableDataSource(this.cardData);
  displayedColumns: string[] = ['number', 'Image', 'Title', 'Price', 'Shipping', 'Zip', 'wishlist'];
  a: string = '';
  b: string = '';
  c: number = 0;
  ngOnChanges(changes: SimpleChanges) {
      
      if (changes['cardData'] && changes['cardData'].currentValue) {
        this.currentData = this.cardData.slice((this.currentPage - 1) * 10, this.currentPage * 10);
        this.dataSource.data = this.currentData;
        this.totalPages = Math.ceil(this.cardData.length / 10);
      }
      this.http.get(`http://hw5712.wn.r.appspot.com/wishlist`)
      .subscribe(response => {
        this.wishlist = Object.values(response);
      });  
  }
  get pages(): number[] {
    const pageArray = [];
    for (let i = 1; i <= this.totalPages; i++) {
      pageArray.push(i);
    }
    return pageArray;
  }

  constructor(private http: HttpClient) { }
  backToSearch(){
    this.issearchTableVisible = true;
    this.isdetailVisible = false;
    this.selectedButton = 'product';
    this.isImageVisible = false;
    this.isShippingVisible = false;
    this.isSellerVisible = false;
  }


  showMore(){
    this.showMoreSimilar = !this.showMoreSimilar;
  }
  selectTab(tab: string) {
    this.selectedButton = tab;
    if(tab == 'photos'){
      const url = `http://localhost:3000/similar?param=${this.currentName}`;
      console.log(url);
      this.http.get(url)
      .subscribe(response => {
        this.imageData = Object.values(response)[5];
        console.log(response);
        this.isImageVisible = true;
        this.isdetailVisible = false;
        this.isShippingVisible = false;
        this.isSellerVisible = false;
        this.isSimilarVisible = false;
      });
    }
    else if(tab=='shipping'){
      console.log(this.productSource);
      this.isShippingVisible = true;
      this.isdetailVisible = false;
      this.isImageVisible = false;
      this.isSellerVisible = false;
      this.isSimilarVisible = false;
    }
    else if(tab=='product'){
      this.isdetailVisible = true;
      this.isImageVisible = false;
      this.isShippingVisible = false;
      this.isSellerVisible = false;
      this.isSimilarVisible = false;
    }
    else if(tab=='seller'){
      this.isSellerVisible = true;
      this.isdetailVisible = false;
      this.isImageVisible = false;
      this.isShippingVisible = false;
      this.isSimilarVisible = false;
    }
    else if(tab=='similar'){
      const url = `http://hw5712.wn.r.appspot.com/similaritem?param=${this.currentId}`;
      this.http.get(url)
      .subscribe(response => {
        this.similarData = Object.values(response)[0]['itemRecommendations']['item'];
        this.csimilarData = this.similarData.slice();
        //console.log(this.similarData);
        this.isSimilarVisible = true;
        this.isdetailVisible = false;
        this.isImageVisible = false;
        this.isShippingVisible = false;
        this.isSellerVisible = false;
      });
    }
  }
  showPic(){
    this.showcarousel = true;
  }
  onCloseClick(){
    this.showcarousel = false;
  }

  deleteWishList(id: number,id2: string){
    const url = `http://hw5712.wn.r.appspot.com/deleteitem?param=${id2}`;
    this.http.get(url)
    .subscribe(response => {
      console.log(response)}
      );
    this.wishlist = this.wishlist.filter(item => item !== id2);

    console.log(this.wishlist);
  }
  addToWishList(id: number,id2: string){
    console.log(id2);
    const url = `http://localhost:3000/saveitem?param=${id}`;
    this.http.get(url)
    .subscribe(response => {
      console.log(response)}
      );
      this.wishlist.push(id2);
  }
  searchItem(id: string,title: string, number: number){
    this.a=id;
    this.b=title;
    this.c=number;
    const url = `https://hw5712.wn.r.appspot.com/item?param=${id}`;
    this.currentId = id;
    this.currentShipping = this.cardData[number]['shippingInfo'];
    this.currentSeller = this.cardData[number]['sellerInfo'];
    //console.log(this.currentSeller);
    this.rowClickStatus[number] = !this.rowClickStatus[number];

    //console.log(this.currentShipping);
    this.currentName = title;
    //console.log(this.currentName);
    this.http.get(url)
    .subscribe(response => {
      this.issearchTableVisible = false;
      this.isdetailVisible = true;
      this.finisedSearch = true;
      this.isImageVisible = false;
      this.isShippingVisible = false;
      this.isSellerVisible = false;
      this.isSimilarVisible = false;
      this.facebookhref = `https://www.facebook.com/sharer/sharer.php?u=${Object.values(response)[4]['ViewItemURLForNaturalSearch']}&quote=Buy ${Object.values(response)[4]['Title']} at $${Object.values(response)[4]['CurrentPrice']['Value']} from link below`;
      this.productSource = Object.values(response)[4];
      console.log(this.productSource);
    });
  }

  onSortByChange(){
    if(this.selectedSortOrder=='ascending'){
      if(this.selectedSortBy == 'product-name'){
        this.csimilarData.sort((a, b) => (a.title > b.title) ? 1 : -1);
      }
      else if(this.selectedSortBy == 'days-left'){
        this.csimilarData.sort((a, b) => (parseInt(a.timeLeft.slice(a.timeLeft.indexOf("P") + 1, a.timeLeft.indexOf("D"))) > parseInt(b.timeLeft.slice(b.timeLeft.indexOf("P") + 1, b.timeLeft.indexOf("D")))) ? 1 : -1);
      }
      else if(this.selectedSortBy == 'price'){
        this.csimilarData.sort((a, b) => (a.buyItNowPrice.__value__ > b.buyItNowPrice.__value__) ? 1 : -1);
      }
      else if(this.selectedSortBy == 'shipping-cost'){
        this.csimilarData.sort((a, b) => (a.shippingCost.__value__ > b.shippingCost.__value__) ? 1 : -1);
      }
    }
    else{
      if(this.selectedSortBy == 'product-name'){
        this.csimilarData.sort((a, b) => (a.title < b.title) ? 1 : -1);
      }
      else if(this.selectedSortBy == 'days-left'){
        this.csimilarData.sort((a, b) => (parseInt(a.timeLeft.slice(a.timeLeft.indexOf("P") + 1, a.timeLeft.indexOf("D"))) < parseInt(b.timeLeft.slice(b.timeLeft.indexOf("P") + 1, b.timeLeft.indexOf("D")))) ? 1 : -1);
      }
      else if(this.selectedSortBy == 'price'){
        this.csimilarData.sort((a, b) => (a.buyItNowPrice.__value__ < b.buyItNowPrice.__value__) ? 1 : -1);
      }
      else if(this.selectedSortBy == 'shipping-cost'){
        this.csimilarData.sort((a, b) => (a.shippingCost.__value__ < b.shippingCost.__value__) ? 1 : -1);
      }
    }
    if(this.selectedSortBy == 'default'){
      this.csimilarData = this.similarData;
    }
  }
  onSortOrderByChange(){
    if(this.selectedSortOrder=='ascending'){
      if(this.selectedSortBy == 'product-name'){
        this.csimilarData.sort((a, b) => (a.title > b.title) ? 1 : -1);
      }
      else if(this.selectedSortBy == 'days-left'){
        console.log(this.csimilarData[0].timeLeft.slice(this.csimilarData[0].timeLeft.indexOf("P") + 1, this.csimilarData[0].timeLeft.indexOf("D")))
        this.csimilarData.sort((a, b) => (parseInt(a.timeLeft.slice(a.timeLeft.indexOf("P") + 1, a.timeLeft.indexOf("D"))) > parseInt(b.timeLeft.slice(b.timeLeft.indexOf("P") + 1, b.timeLeft.indexOf("D")))) ? 1 : -1);
      }
      else if(this.selectedSortBy == 'price'){
        this.csimilarData.sort((a, b) => (a.buyItNowPrice.__value__ > b.buyItNowPrice.__value__) ? 1 : -1);
      }
      else if(this.selectedSortBy == 'shipping-cost'){
        this.csimilarData.sort((a, b) => (a.shippingCost.__value__ > b.shippingCost.__value__) ? 1 : -1);
      }
    }
    else{
      if(this.selectedSortBy == 'product-name'){
        this.csimilarData.sort((a, b) => (a.title < b.title) ? 1 : -1);
      }
      else if(this.selectedSortBy == 'days-left'){
        this.csimilarData.sort((a, b) => (parseInt(a.timeLeft.slice(a.timeLeft.indexOf("P") + 1, a.timeLeft.indexOf("D"))) < parseInt(b.timeLeft.slice(b.timeLeft.indexOf("P") + 1, b.timeLeft.indexOf("D")))) ? 1 : -1);
      }
      else if(this.selectedSortBy == 'price'){
        this.csimilarData.sort((a, b) => (a.buyItNowPrice.__value__ < b.buyItNowPrice.__value__) ? 1 : -1);
      }
      else if(this.selectedSortBy == 'shipping-cost'){
        this.csimilarData.sort((a, b) => (a.shippingCost.__value__ < b.shippingCost.__value__) ? 1 : -1);
      }
    }
    if(this.selectedSortBy == 'default'){
      this.csimilarData = this.similarData;
    }
  }
  goToPage(n: number): void {
    this.currentPage = n;
    this.currentData = this.cardData.slice((this.currentPage - 1) * 10, this.currentPage * 10);
    this.dataSource.data = this.currentData;
  }


}
