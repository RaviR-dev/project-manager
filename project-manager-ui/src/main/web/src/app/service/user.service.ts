import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/index";
import {User} from "../model/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/projectmanager/user';

  constructor(private http: HttpClient) {
  }

  addUser(user: User): Observable<any> {
    return this.http.post(`${this.baseUrl}/add`, user);
  }

  getAllUsers(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/list`);
  }

  updateUser(user: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/update`, user);
  }

  deleteUser(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/delete`, user );
  }
}
