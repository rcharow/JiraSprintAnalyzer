export class JiraBoard {
  public id: string;
  public self: string;
  public name: string;
  public type: string;

}

export class JiraSprint {
  public id: string;
  public self: string;
  public state: string;
  public name: string;
  public startDate: Date;
  public endDate: Date;
  public completeDate: Date;
  public originBoardId: string;
}


export class JiraSprintSummary {
  public id: string;
  public self: string;
  public name: string;
  public completeDate: Date;
  public totalIssues: Number;
  public totalPoints: Number;
  public totalTimeHours: Number;
  public clientTotalCost: Number;
  public internalTotalCost: Number;
  public margin: Number;
  public worklogSummary: JiraWorklogSummary;
}

export class JiraWorklog {
  public author:string;
  public totalTimeHours:number;
}

export class JiraWorklogSummary {
  public sprintId:string;
  public worklogs:Array<JiraWorklog>;
}

export class JiraPointAnalysis {
  public pointValue:number;
  public avgTimeSpentSeconds:number;
  public avgDollarsSpent:number;
}

export class JiraSprintPointAnalysis {
  public sprintId:string;
  public sprintName:string;
  public startDate:Date;
  public endDate:Date;
  public pointAnalysis:Array<JiraPointAnalysis>;
}