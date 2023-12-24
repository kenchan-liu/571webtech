import { Component, OnInit,Input,Output ,OnChanges,SimpleChanges,EventEmitter} from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-wish-list',
  templateUrl: './wish-list.component.html',
  styleUrls: ['./wish-list.component.css']
})
export class WishListComponent {
  @Input() wishdata: any[] = []; 
  public selectedButton: string = 'product';

  currentId: string = '';
  currentShipping: any[] = [];
  currentSeller: any[] = [];
  currentName: string = '';
  currentNo: number = -1;
  issearchTableVisible: boolean = true;
  isdetailVisible: boolean = false;
  showcarousel:boolean = false;
  isImageVisible: boolean = false;
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
  similarData: any[] = [];
  csimilarData: any[] = [];
  selectedSortBy: string = 'default';
  selectedSortOrder: string = 'ascending';
  dataSource = new MatTableDataSource(this.wishdata);
  displayedColumns: string[] = ['number', 'Image', 'Title', 'Price', 'Shipping', 'Zip', 'wishlist'];
  ngOnChanges(changes: SimpleChanges) {
    
    if (changes['wishdata'] && changes['wishdata'].currentValue) {
      const url = `http://hw5712.wn.r.appspot.com/wishlist`;
      this.http.get(url)
      .subscribe(response => {
        this.wishes = Object.values(response);
        console.log(this.wishes);
      });
      console.log(this.wishdata);
      this.dataSource.data = this.wishdata;
    }
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
      const url = `http://hw5712.wn.r.appspot.com/similar?param=${this.currentName}`;
      console.log(url);
      this.http.get(url)
      .subscribe(response => {
        this.imageData = Object.values(response)[5];
        console.log(Object.values(response));
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
      this.wishes = this.wishes.filter(item => item !== id2);
      const url2 = `http://hw5712.wn.r.appspot.com/getwishlist`;
      this.http.get(url2)
      .subscribe(response => {
        this.wishdata = Object.values(response);
        this.dataSource = new MatTableDataSource(this.wishdata);
      });
  }
  searchItem(id: string,title: string, number: number){
    const url = `http://hw5712.wn.r.appspot.com/item?param=${id}`;
    this.currentId = id;
    this.currentShipping = this.wishdata[number]['shippingInfo'];
    this.currentSeller = this.wishdata[number]['sellerInfo'];
    this.rowClickStatus[number] = !this.rowClickStatus[number];

    //console.log(this.currentShipping);
    this.currentName = title;
    console.log(this.currentName);
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
    });
  }
  showWishList(){
    console.log(this.wishdata);
    console.log(this.dataSource.data);
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


}
