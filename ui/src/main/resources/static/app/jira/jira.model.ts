export class JiraBoard {
  public id: String;
  public self: String;
  public name: String;
  public type: String;

}

export class JiraSprint {
  public id: String;
  public self: String;
  public state: String;
  public name: String;
  public startDate: Date;
  public endDate: Date;
  public completeDate: Date;
  public originBoardId: String;
}


export class JiraSprintSummary {
  public id: String;
  public self: String;
  public totalIssues: Number;
  public totalPoints: Number;
  public totalTimeHours: Number;
  public clientTotalCost: Number;
  public internalTotalCost: Number;
  public margin: Number;
}

export class JiraWorklog {
  public author:String;
  public totalTimeHours:number;
}

export class JiraWorklogSummary {
  public sprintId:String;
  public worklogs:Array<JiraWorklog>;
}