import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MyDataService } from './services/my-service.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule,ReactiveFormsModule  } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { CardComponent } from './card/card.component';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { PaginatorModule } from 'primeng/paginator';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDialogModule } from '@angular/material/dialog';
import { RoundProgressModule } from 'angular-svg-round-progressbar';
import { MatSelectModule } from '@angular/material/select';
import { WishListComponent } from './wish-list/wish-list.component';

@NgModule({
  declarations: [
    AppComponent,
    CardComponent,
    WishListComponent,
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatInputModule,
    MatAutocompleteModule,
    MatTableModule,
    MatIconModule,
    PaginatorModule,
    MatTooltipModule,
    MatDialogModule,
    RoundProgressModule,
    MatSelectModule
  ],
  providers: [
    MyDataService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
