import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MyDataService {
  private apiUrl = 'https://hw5712.wn.r.appspot.com'; 
  constructor(private http: HttpClient) {}

  fetchData() {
    return this.http.get(`${this.apiUrl}/data`);
  }

  postData(data: any) {
    return this.http.post(`${this.apiUrl}/data`, data);
  }
}
