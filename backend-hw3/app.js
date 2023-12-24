import express, { response } from 'express';
import cors from 'cors';
import { MongoClient } from 'mongodb';
import axios from 'axios';
import {OAuthToken} from './ebay_oauth_token.js'
const oa = new OAuthToken('Jianchen-csci571h-PRD-2727b8eb0-66ac97b7','PRD-727b8eb0f256-fdfa-4552-a0d8-aa1e')
const url = `mongodb+srv://kentlew0jianchen:Ljc20010503@cluster0.unbpvic.mongodb.net/?retryWrites=true&w=majority&ssl=true`;
const dbName = 'wishlist'; 
await MongoClient.connect(url, (err, client) => {
  if (err) {
      console.error('fail', err);
      return;
  }
});
var searched = [];
const client = new MongoClient(url);
await client.connect();
const db = client.db(dbName); 
const collection = db.collection("hw3");
const app = express();
const port = process.env.PORT || 8080; 
//const port = 3000;
app.use(cors());
const categorydict = {
  'art':'550',
  'baby':'2984',
  'Books':'267',
  'Clothing, Shoes & Accessories':'11450',
  'Computers/Tablets & Networking':'58058',
  'Health & Beauty':'26395',
  'Music':'11233',
  'Video Games & Consoles':'1249'
}
app.get('/', (req, res) => {
  res.send('Hello from Express!');
});
app.get('/data', async (req, res) => {
    const requestData = req.query.param;
    const url =`http://api.geonames.org/postalCodeSearchJSON?postalcode_startsWith=${requestData}&maxRows=5&username=liujianc&country=US`;
    const response = await axios.get(url);
    //console.log(response.data);
    res.json(response.data.postalCodes);
  });
//ipinfo.io/76.32.120.127?token=5d2fd6027a891c
app.get('/ip', async (req, res) => {
    const url =`http://ipinfo.io/json?token=5d2fd6027a891c`;
    const response = await axios.get(url);
    //console.log(response.data);
    res.json(response.data);
  });
app.get('/simplesearch',async (req,res)=>{
  const d = req.query;
  const keyword = encodeURIComponent(d.keyword);
  const category = d.category;
  const isnew = d.isnew;
  const isused = d.isused;
  const isunspecified = d.isunspecified;
  const isfree = d.isfree;
  const islocal = d.islocal;
  var Condition=[];
  if(isnew=='true'){
    Condition.push('New');
  }
  if(isused=='true'){
    Condition.push('Used');
  }
  if(isunspecified=='true'){  
    Condition.push('Unspecified');
  }
  if(category=='All'){
    if(Condition.length==0){
      const url= `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&keywords=${keyword}&paginationInput.entriesPerPage=50&itemFilter(0).name=FreeShippingOnly&itemFilter(0).value=${isfree}&itemFilter(1).name=LocalPickupOnly&itemFilter(1).value=${islocal}&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
      //console.log(url)
      const response = await axios.get(url);
      //console.log(response.data);
      searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
      res.json(response.data);
      return;
    }
    else if(Condition.length==1){
      const url = `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&keywords=${keyword}&paginationInput.entriesPerPage=50&itemFilter(0).name=Condition&itemFilter(0).value(0)=${Condition[0]}&itemFilter(1).name=FreeShippingOnly&itemFilter(1).value=${isfree}&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=${islocal}&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
      const response = await axios.get(url);
      //console.log(response.data);
      searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
      res.json(response.data);
      return;
    }
    else if(Condition.length==2){
      const url = `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&keywords=${keyword}&paginationInput.entriesPerPage=50&itemFilter(0).name=Condition&itemFilter(0).value(0)=${Condition[0]}&itemFilter(0).value(1)=${Condition[1]}&itemFilter(1).name=FreeShippingOnly&itemFilter(1).value=${isfree}&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=${islocal}&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
      const response = await axios.get(url);
      //console.log(response.data);
      searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
      res.json(response.data);
      return;
    }
    else if (Condition.length==3){
      const url = `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&keywords=${keyword}&paginationInput.entriesPerPage=50&itemFilter(0).name=Condition&itemFilter(0).value(0)=${Condition[0]}&itemFilter(0).value(1)=${Condition[1]}&itemFilter(0).value(2)=${Condition[2]}&itemFilter(1).name=FreeShippingOnly&itemFilter(1).value=${isfree}&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=${islocal}&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
      const response = await axios.get(url);
      //console.log(response.data);
      searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
      res.json(response.data);
      return;
    }
  }
  if(Condition.length==0){
    const url= `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&keywords=${keyword}&categoryId=${categorydict[category]}&paginationInput.entriesPerPage=50&itemFilter(0).name=FreeShippingOnly&itemFilter(0).value=${isfree}&itemFilter(1).name=LocalPickupOnly&itemFilter(1).value=${islocal}&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
    console.log(url)
    //https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&keywords=harrypotter&categoryId=267&paginationInput.entriesPerPage=50&itemFilter(0).name=FreeShippingOnly&itemFilter(0).value=false&itemFilter(1).name=LocalPickupOnly&itemFilter(1).value=false&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo

    const response = await axios.get(url);
    //console.log(response.data);
    searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
    res.json(response.data);
    return;
  }
  else if(Condition.length==1){
    const url = `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&keywords=${keyword}&categoryId=${categorydict[category]}&paginationInput.entriesPerPage=50&itemFilter(0).name=Condition&itemFilter(0).value(0)=${Condition[0]}&itemFilter(1).name=FreeShippingOnly&itemFilter(1).value=${isfree}&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=${islocal}&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
    const response = await axios.get(url);
    //console.log(response.data);
    searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
    res.json(response.data);
    return;
  }
  else if(Condition.length==2){
    const url = `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&keywords=${keyword}&categoryId=${categorydict[category]}&paginationInput.entriesPerPage=50&itemFilter(0).name=Condition&itemFilter(0).value(0)=${Condition[0]}&itemFilter(0).value(1)=${Condition[1]}&itemFilter(1).name=FreeShippingOnly&itemFilter(0).value=${isfree}&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=${islocal}&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
    const response = await axios.get(url);
    //console.log(response.data);
    searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
    res.json(response.data);
    return;
  }
  else if (Condition.length==3){
    const url = `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&keywords=${keyword}&categoryId=${categorydict[category]}&paginationInput.entriesPerPage=50&itemFilter(0).name=Condition&itemFilter(0).value(0)=${Condition[0]}&itemFilter(0).value(1)=${Condition[1]}&itemFilter(0).value(2)=${Condition[2]}&itemFilter(1).name=FreeShippingOnly&itemFilter(0).value=${isfree}&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=${islocal}&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
    const response = await axios.get(url);
    //console.log(response.data);
    searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
    res.json(response.data);
    return;
  }
});
app.get('/search',async (req,res)=>{
    //console.log(req.query);
    const d = req.query;
    const keyword = encodeURIComponent(d.keyword);
    
    var category = d.category;
    if(d.category=='All'){
      category='All Categories';
    }
    const isnew = d.isnew;
    const isused = d.isused;
    const isunspecified = d.isunspecified;
    const isfree = d.isfree;
    const islocal = d.islocal;
    const distance = d.distance;
    const location = d.location;
    var Condition=[];
    //console.log(d);
    if(category=='All Categories'){
      if(isnew=='true'){
        Condition.push('New');
      }
      if(isused=='true'){
        Condition.push('Used');
      }
      if(isunspecified=='true'){  
        Condition.push('Unspecified');
      }
      if(Condition.length==0){
        const url= `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&GLOBAL-ID=EBAY-US&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD=TRUE&keywords=${keyword}&paginationInput.entriesPerPage=50&buyerPostalCode=${location}&itemFilter(0).name=MaxDistance&itemFilter(0).value=${distance}&itemFilter(1).name=FreeShippingOnly&itemFilter(1).value=${isfree}&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=${islocal}&itemFilter(3).name=HideDuplicateItems&itemFilter(3).value=true&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
        //console.log(url)
        const response = await axios.get(url);
        //console.log(response.data);
        searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
        res.json(response.data);
        return;
      }
      else if(Condition.length==1){
        const url= `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&GLOBAL-ID=EBAY-US&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD=TRUE&keywords=${keyword}&paginationInput.entriesPerPage=50&buyerPostalCode=${location}&itemFilter(0).name=MaxDistance&itemFilter(0).value=${distance}&itemFilter(1).name=FreeShippingOnly&itemFilter(1).value=${isfree}&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=${islocal}&itemFilter(3).name=HideDuplicateItems&itemFilter(3).value=true&itemFilter(4).name=Condition&itemFilter(4).value(0)=${Condition[0]}&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
        //console.log(url)
        const response = await axios.get(url);
        //console.log(response.data);
        searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
        res.json(response.data);
        return;
      }
      else if(Condition.length==2){
        const url= `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&GLOBAL-ID=EBAY-US&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD=TRUE&keywords=${keyword}&paginationInput.entriesPerPage=50&buyerPostalCode=${location}&itemFilter(0).name=MaxDistance&itemFilter(0).value=${distance}&itemFilter(1).name=FreeShippingOnly&itemFilter(1).value=${isfree}&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=${islocal}&itemFilter(3).name=HideDuplicateItems&itemFilter(3).value=true&itemFilter(4).name=Condition&itemFilter(4).value(0)=${Condition[0]}&itemFilter(4).value(1)=${Condition[1]}&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
        //console.log(url)
        const response = await axios.get(url);
        //console.log(response.data);
        searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
        res.json(response.data);
        return;
      }
      else{
        const url= `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&GLOBAL-ID=EBAY-US&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD=TRUE&keywords=${keyword}&paginationInput.entriesPerPage=50&buyerPostalCode=${location}&itemFilter(0).name=MaxDistance&itemFilter(0).value=${distance}&itemFilter(1).name=FreeShippingOnly&itemFilter(1).value=${isfree}&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=${islocal}&itemFilter(3).name=HideDuplicateItems&itemFilter(3).value=true&itemFilter(4).name=Condition&itemFilter(4).value(0)=${Condition[0]}&itemFilter(4).value(1)=${Condition[1]}&itemFilter(4).value(2)=${Condition[2]}&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
        //console.log(url)
        const response = await axios.get(url);
        res.json(response.data);
        searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
        return;
      }
  
    }
    if(isnew=='true'){
      Condition.push('New');
    }
    if(isused=='true'){
      Condition.push('Used');
    }
    if(isunspecified=='true'){  
      Condition.push('Unspecified');
    }
    if(Condition.length==0){
      const url= `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&GLOBAL-ID=EBAY-US&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD=TRUE&keywords=${keyword}&categoryId=${categorydict[category]}&paginationInput.entriesPerPage=50&buyerPostalCode=${location}&itemFilter(0).name=MaxDistance&itemFilter(0).value=${distance}&itemFilter(1).name=FreeShippingOnly&itemFilter(1).value=${isfree}&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=${islocal}&itemFilter(3).name=HideDuplicateItems&itemFilter(3).value=true&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
      //console.log(url)
      const response = await axios.get(url);
      //console.log(response.data);
      searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
      res.json(response.data);
      return;
    }
    else if(Condition.length==1){
      const url= `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&GLOBAL-ID=EBAY-US&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD=TRUE&keywords=${keyword}&categoryId=${categorydict[category]}&paginationInput.entriesPerPage=50&buyerPostalCode=${location}&itemFilter(0).name=MaxDistance&itemFilter(0).value=${distance}&itemFilter(1).name=FreeShippingOnly&itemFilter(1).value=${isfree}&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=${islocal}&itemFilter(3).name=HideDuplicateItems&itemFilter(3).value=true&itemFilter(4).name=Condition&itemFilter(4).value(0)=${Condition[0]}&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
      const response = await axios.get(url);
      //console.log(response.data);
      searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
      res.json(response.data);
      return;
    }
    else if(Condition.length==2){
      const url= `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&GLOBAL-ID=EBAY-US&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD=TRUE&keywords=${keyword}&categoryId=${categorydict[category]}&paginationInput.entriesPerPage=50&buyerPostalCode=${location}&itemFilter(0).name=MaxDistance&itemFilter(0).value=${distance}&itemFilter(1).name=FreeShippingOnly&itemFilter(1).value=${isfree}&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=${islocal}&itemFilter(3).name=HideDuplicateItems&itemFilter(3).value=true&itemFilter(4).name=Condition&itemFilter(4).value(0)=${Condition[0]}&itemFilter(4).value(1)=${Condition[1]}&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
      const response = await axios.get(url);
      //console.log(response.data);
      searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
      res.json(response.data);
      return;
    }
    else{
      const url= `https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&GLOBAL-ID=EBAY-US&SECURITY-APPNAME=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD=TRUE&keywords=${keyword}&categoryId=${categorydict[category]}&paginationInput.entriesPerPage=50&buyerPostalCode=${location}&itemFilter(0).name=MaxDistance&itemFilter(0).value=${distance}&itemFilter(1).name=FreeShippingOnly&itemFilter(1).value=${isfree}&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=${islocal}&itemFilter(3).name=HideDuplicateItems&itemFilter(3).value=true&itemFilter(4).name=Condition&itemFilter(4).value(0)=${Condition[0]}&itemFilter(4).value(1)=${Condition[1]}&itemFilter(4).value(2)=${Condition[2]}&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo`;
      const response = await axios.get(url);
      res.json(response.data);
      searched = response.data['findItemsAdvancedResponse'][0]['searchResult'][0]['item'];
      return;
    }
});
app.get('/item',async(req,res)=>{
  const itemid = req.query.param;
  //console.log(itemid);
  const url = `https://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=JSON&appid=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&siteid=0&version=967&ItemID=${itemid}&IncludeSelector=Description,Details,ItemSpecifics`;
  const token = await oa.getApplicationToken();
  const config = {
    headers: {
      'X-EBAY-API-IAF-TOKEN': token,
    }
  };
  
  try {
    const response = await axios.get(url, config);
    //console.log(response.data);
    res.json(response.data);
  } catch (error) {
    console.error('error:', error);
  }
  
});
app.get('/similar',async(req,res)=>{
  const extractedText = req.query.param.match(/[A-Za-z\s]+/g).join(' ');
  const words = extractedText.split(' ');
  let wordss = words.filter(function(element) {
      return element !== "";
  });

  console.log(wordss);
  const q = encodeURIComponent(wordss.slice(0, 5).join(' '));
  //const q = encodeURIComponent(req.query.param);
  const url = `https://www.googleapis.com/customsearch/v1?q=${q}&cx=9006377d0d4b14bf4&imgSize=huge&num=10&searchType=image&key=AIzaSyDGj6y7OGL9UjjbQAx0J5YTyIUs8WEs9oc`
  console.log(url);
  try {
    const response = await axios.get(url);
    //console.log(response.data);
    res.json(response.data);
  } catch (error) {
    console.error('error:', error);
  }
});
app.get('/similaritem',async(req,res)=>{
  const q=req.query.param;
  
  const url = `https://svcs.ebay.com/MerchandisingService?OPERATION-NAME=getSimilarItems&SERVICE-NAME=MerchandisingService&SERVICE-VERSION=1.1.0&CONSUMER-ID=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&itemId=${q}&maxResults=20`
  try{
    const response = await axios.get(url);
    //console.log(response.data);
    res.json(response.data);
  }
  catch(error){
    console.error('error:', error);
  }

});
app.get('/saveitem',async(req,res)=>{
  //console.log(req.query.param);
  const num = req.query.param;
  // const url = `https://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=JSON&appid=Jianchen-csci571h-PRD-2727b8eb0-66ac97b7&siteid=0&version=967&ItemID=${itemid}&IncludeSelector=Description,Details,ItemSpecifics`;
  // const token = await oa.getApplicationToken();
  // const config = {
  //   headers: {
  //     'X-EBAY-API-IAF-TOKEN': token,
  //   }
  // };
  // try {
  //   const response = await axios.get(url, config);
  //   console.log(response.data);
    
  //   const insertResult = await collection.insertOne(response.data['Item']);
  //   //console.log("insert", insertResult);

  //   res.json(response.data);
  // } catch (error) {
  //   console.error('error:', error);
  // }
  console.log(searched[num]);
  const insertResult = await collection.insertOne(searched[num]);
  //console.log("insert", insertResult);

  res.json(response.data);


});
app.get('/deleteitem',async(req,res)=>{
  const itemid = req.query.param;
  console.log(itemid);
  const result = await collection.deleteOne({ itemId:{ $all: [itemid] }});
  //console.log("delete", result);
  res.json(result);
}
);
app.get('/wishlist',async(req,res)=>{
  const result = await collection.find({}).toArray();
  const items = result.map(itemid=>itemid.itemId[0]);
  res.json(items);
});
app.get('/getwishlist',async(req,res)=>{
  const result = await collection.find({}).toArray();
  //console.log(result);
  res.json(result);
});
app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});



