import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Task} from "../model/task";

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private baseUrl = 'http://localhost:8080/projectmanager/task';

  constructor(private http: HttpClient) {
  }

  addTask(task: Task): Observable<any> {
    return this.http.post(`${this.baseUrl}/add`, task);
  }

  getAllTasks(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/list`);
  }

  getTaskByProject(task: Task): Observable<any> {
    return this.http.get(`${this.baseUrl}/projecttask/${task.projectId}`);
  }


  updateTask(task: Task): Observable<any> {
    return this.http.put(`${this.baseUrl}/update`, task);
  }

  endTask(task: Task): Observable<any> {
    return this.http.put(`${this.baseUrl}/end`, task);
  }
}
