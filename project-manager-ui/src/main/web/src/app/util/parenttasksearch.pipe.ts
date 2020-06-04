import { Pipe, PipeTransform } from '@angular/core';
import {ParentTask} from "../model/parenttask";

@Pipe({
  name: 'parenttasksearch'
})
export class ParenttasksearchPipe implements PipeTransform {

  transform(parentTasks: ParentTask[], filter: number): ParentTask {
    if (parentTasks && filter) {
      let filteredParentTasks: ParentTask[] = parentTasks.filter(parentTask => parentTask.parentId === filter);
      if (filteredParentTasks) {
        return filteredParentTasks[0];
      }
    }
    const defaultParentTask = new ParentTask();
    defaultParentTask.parentTask = "This Task Has NO Parent";
    return defaultParentTask;
  }

}
